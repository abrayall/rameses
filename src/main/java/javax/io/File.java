package javax.io;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.lang.Try;

public class File {
	
	protected java.io.File file;
	
	public File(String path) {
		this(new java.io.File(path));
	}
	
	public File(java.io.File directory, String name) {
		this(new java.io.File(directory, name));
	}
	
	public File(java.io.File file) {
		this.file = file;
	}
	
	public String name() {
		return this.file.getName();
	}
	
	public boolean exists() {
		return this.exists();
	}
	
	public File create() throws Exception {
		this.mkdirs();
		this.file.createNewFile();
		return this;
	}
	
	public File delete() throws Exception {
		if (this.file.delete() == false)
			throw new Exception("Failed to delete file");
		
		return this;
	}
	
	public File mkdirs() throws Exception {
		if (this.file.getParentFile() != null) 
			this.file.getParentFile().mkdirs();
		
		return this;
	}
	
	public File parent() throws Exception {
		return file(file.getAbsoluteFile().getParent());
	}
	
	public InputStream inputStream() throws Exception {
		return new FileInputStream(file);
	}
	
	public String read() throws Exception {
		return Streams.read(this.inputStream());
	}
	
	public OutputStream outputStream() throws Exception {
		return new FileOutputStream(this.file);
	}
	
	public File write(String contents) throws Exception {
		return this.write(new ByteArrayInputStream(contents.getBytes()));
	}
	
	public File write(InputStream contents) throws Exception {
		Streams.write(contents, this.outputStream()).close();
		return this;
	}
	
	public FileLock lock() {
		return new FileLock(this);
	}
	
	public File lock(Consumer<FileLock> success, Consumer<FileLock> failure) {
		this.lock().lock(success, failure);
		return this;
	}
	
	public File watch(BiConsumer<File, String> callback) throws Exception {
		this.watcher(callback).watch();
		return this;	
	}
	
	public FileWatcher watcher(BiConsumer<File, String> callback) {
		return new FileWatcher(this, callback);
	}
	
	public java.io.File toFile() {
		return this.file;
	}
	
	public java.nio.file.Path toPath() {
		return this.file.toPath();
	}
	
	public class FileWatcher extends Thread {

		private File file;
		private BiConsumer<File, String> callback;
		private WatchService service;
		
		public FileWatcher(File file, BiConsumer<File, String> callback) {
			this.file = file;
			this.callback = callback;
		}
		
		public FileWatcher watch() throws Exception {
			this.service = this.file.toPath().getFileSystem().newWatchService();
			this.file.parent().toPath().register(service, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
			this.start();
			return this;
		}
		
		public void run() {
			while(true) {
				Try.attempt(() -> {
					WatchKey key = service.take();
					for (WatchEvent<?> event : key.pollEvents()) {
						if (Path.class.isInstance(event.context())) {
							Path context = (Path) event.context();
							if (context.getFileName().toString().equals(this.file.name()))
								Try.attempt(() -> callback.accept(this.file, event.kind().toString().toLowerCase().replace("entry_", "")));
						}
					}
					
					key.reset();
				});
			}
		}
	}
	
	public class FileLock {
		
		private File file;
		private java.nio.channels.FileLock lock; 
		
		public FileLock(File file) {
			this.file = file;
		}
		
		public File getFile() {
			return this.file;
		}
		
		public boolean isLocked() {
			return this.lock != null && this.lock.isValid();
		}
		
		public void release() throws Exception {
			this.lock.release();
		}
		
		public void release(Consumer<FileLock> success, Consumer<FileLock> failure) {
			Try.attempt(() -> {
				this.lock.release();
				success.accept(this);
			}, () -> failure.accept(this));
		}
		
		public void lock(Consumer<FileLock> success, Consumer<FileLock> failure) {
			Try.attempt(() -> {
			  this.lock = FileChannel.open(file.toPath(), StandardOpenOption.WRITE).tryLock();
			  success.accept(this);
			}, () -> failure.accept(this));
		}
	}
	
	public static File file(String path) {
		return new File(path);
	}
	
	public File file(java.io.File directory, String name) {
		return new File(directory, name);
	}
	
	public File file(java.io.File file) {
		return new File(file);
	}
}
