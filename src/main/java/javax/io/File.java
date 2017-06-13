package javax.io;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
	
	public String path() {
		return this.file.getAbsolutePath();
	}
	
	public boolean exists() {
		return this.exists();
	}
	
	public String type() {
		return this.file.isDirectory() ? "directory" : "file";
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
	
	
	public File copy(String source) throws Exception {
		return this.copy(new java.io.File(source));
	}
	
	public File copy(java.io.File source) throws Exception {
		return this.copy(new File(file));
	}
	
	public File copy(File source) throws Exception {
		Files.copy(source.toPath(), this.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
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
	
	public File synchronize(String target) throws Exception {
		return this.synchronize(new java.io.File(target));
	}
	
	public File synchronize(java.io.File target) throws Exception {
		return this.synchronize(new File(target));
	}

	public File synchronize(File target) throws Exception {
		this.synchonizer(target).synchronize();
		return this;
	}

	public FileSynchronizer synchonizer(String target) {
		return this.synchonizer(new java.io.File(target));
	}
	
	public FileSynchronizer synchonizer(java.io.File target) {
		return this.synchonizer(new File(target));
	}
	
	public FileSynchronizer synchonizer(File target) {
		return new FileSynchronizer(this, target);
	}
	
	public String toString() {
		return this.path();
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
		private Boolean running = false;
		
		public FileWatcher(File file, BiConsumer<File, String> callback) {
			this.file = file;
			this.callback = callback;
		}
		
		public FileWatcher watch() throws Exception {
			this.service = this.file.toPath().getFileSystem().newWatchService();
			this.file.parent().toPath().register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
			this.start();
			return this;
		}
		
		public void run() {
			this.running = true;
			while(this.running) {
				Try.attempt(() -> {
					WatchKey key = service.take();
					for (WatchEvent<?> event : key.pollEvents()) {
						if (Path.class.isInstance(event.context())) {
							Path context = (Path) event.context();
							if (context.getFileName().toString().equals(this.file.name()) || this.file.file.isDirectory() == true)
								Try.attempt(() -> callback.accept(this.file.file.isDirectory() == true ? new File(context.getFileName().toString()) : this.file, event.kind().toString().toLowerCase().replace("entry_", "")));
						}
					}
					
					key.reset();
				});
			}
		}
		
		public void halt() throws Exception {
			this.service.close();
			this.running = false;
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
	
	public class FileSynchronizer {
		
		private File source;
		private File target;
		private FileWatcher watcher;
		
		public FileSynchronizer(File source, File target) {
			this.source = source;
			this.target = target;
		}
		
		public FileSynchronizer synchronize() throws Exception {
			this.watcher = this.source.watcher((source, operation) -> {
				File target = new File(source.path().replace(this.source.toPath().normalize().toFile().getAbsolutePath(), this.target.toPath().normalize().toFile().getAbsolutePath()));
				System.out.println(operation + " " + target);
				if (operation.equals("create") || operation.equals("modify"))
					Try.attempt(() -> target.copy(source));
				else if (operation.equals("delete"))
					Try.attempt(() -> target.delete());
					
			}).watch();
			return this;
		}
		
		public FileSynchronizer halt() throws Exception {
			this.watcher.halt();
			return this;
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
	
	public static void main(String[] arguments) throws Exception {
		new File(".").synchronize("/tmp");
	}
}
