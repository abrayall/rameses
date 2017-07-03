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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.Try;
import javax.util.List;

public class File {
	
	protected java.io.File file;
	
	public File(String path) {
		this(new java.io.File(path));
	}
	
	public File(File directory, String name) {
		this(directory.file, name);
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
		return this.file.toPath().normalize().toAbsolutePath().toString();
	}
	
	public boolean exists() {
		return this.file.exists();
	}
	
	public String type() {
		return this.file.isDirectory() ? "directory" : "file";
	}
	
	public boolean isDirectory() {
		return this.file.isDirectory();
	}
	
	public long size() {
		return this.file.length();
	}
	
	public long modified() {
		return this.file.lastModified();
	}
	
	public File create() throws Exception {
		this.mkdirs();
		this.file.createNewFile();
		return this;
	}
	
	public File delete() throws Exception {
		if (this.file.exists() == true) {
			if (this.file.isDirectory()) {
				for (File child : this.list())
					child.delete();
			}
			
			Files.delete(this.file.toPath());
		}
		
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
	
	public List<File> list() throws Exception {
		return this.list(file -> true);
	}
	
	public List<File> list(Function<File, Boolean> filter) throws Exception {
		if (this.file.exists() == false || this.file.isDirectory() == false) return List.list();
		return List.list(Arrays.stream(this.file.listFiles()).map(file -> {
			return new File(file);	
		}).filter(file -> filter.apply(file)).collect(Collectors.toList()));
	}
	
	public Stream<File> search() throws Exception {
		return this.search(file -> true);
	}
	
	public Stream<File> search(String pattern) throws Exception {
		return this.search(file -> file.path().matches(pattern));
	}
	
	public Stream<File> search(Function<File, Boolean> filter) throws Exception {
		return this.list().parallelStream().flatMap(path -> {
			File file = new File(path.toFile());
			if (file.isDirectory()) 
				return Try.attempt(() -> file.search(filter));
			
			return filter.apply(file) == true ? Stream.of(file) : Stream.empty();	
		});
		//return Files.find(this.file.toPath(), Integer.MAX_VALUE, (path, attributes) -> {
		//	return filter.apply(new File(path.toFile()));
		//}, FileVisitOption.FOLLOW_LINKS).map(path -> new File(path.toFile()));
	}
	
	public long latest() throws Exception {
		return this.search().mapToLong(file -> file.modified()).max().orElse(this.modified());
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
		this.synchronizer(target).synchronize();
		return this;
	}

	public FileSynchronizer synchonizer(String target) {
		return this.synchronizer(new java.io.File(target));
	}
	
	public FileSynchronizer synchronizer(java.io.File target) {
		return this.synchronizer(new File(target));
	}
	
	public FileSynchronizer synchronizer(File target) {
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
			this.register(file);
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
							if (context.getFileName().toString().equals(this.file.name()) || this.file.isDirectory() == true)
								Try.attempt(() -> callback.accept(this.file.isDirectory() == true ? new File(context.getFileName().toString()) : this.file, event.kind().toString().toLowerCase().replace("entry_", "")));
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
		
		protected void register(File file) throws Exception {
			File context = file.isDirectory() ? file : file.parent();
			context.toPath().register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
			if (file.isDirectory())
				file.list(child -> child.isDirectory()).forEach(directory -> Try.attempt(() -> register(directory)));			
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
		private List<BiConsumer<File, String>> listeners = List.list(new ArrayList<BiConsumer<File, String>>());
		
		public FileSynchronizer(File source, File target) {
			this.source = source;
			this.target = target;
		}
		
		public FileSynchronizer synchronize() throws Exception {
			return this.synchronize(true);
		}
		
		public FileSynchronizer synchronize(boolean continous) throws Exception {
			this.synchronize(this.source, this.target);
			if (continous)
				this.watch();
			
			return this;
		}
		
		public FileSynchronizer watch() throws Exception {
			this.watcher = this.watcher().watch();
			return this;
		}
		
		public FileWatcher watcher() throws Exception {
			return this.source.watcher((file, operation) -> {
				handle(source, resolve(source, this.source, this.target), operation.equals("create") ? "add" : operation);				
			});
		}
		
		public FileSynchronizer listen(BiConsumer<File, String> listener) {
			this.listeners.add(listener);
			return this;
		}
		
		public FileSynchronizer halt() throws Exception {
			if (this.watcher != null)
				this.watcher.halt();
			
			return this;
		}
		
		protected FileSynchronizer synchronize(File source, File target) throws Exception {
			source.list().forEach(file -> Try.attempt(() -> handle(file, resolve(file, this.source, this.target))));
			target.list().forEach(file -> Try.attempt(() -> handle(resolve(file, this.target, this.source), file)));
			return this;
		}
		
		protected void handle(File source, File target) throws Exception {
			if (source.file.isDirectory() && target.file.isDirectory())
				synchronize(source, target);
			if (source.exists() && target.exists() == false)
				handle(source, target, "add");
			else if (source.exists() == false && target.exists())
				handle(source, target, "delete");
			else if (source.exists() && target.exists() && (source.size() != target.size() || source.modified() != target.modified()))
				handle(source, target, "modify");
		}
		
		protected void handle(File source, File target, String operation) {
			if (operation.equals("add") || operation.equals("modify"))
				Try.attempt(() -> target.copy(source));
			else if (operation.equals("delete"))
				Try.attempt(() -> target.delete());
			
			this.notify(source, operation);
		}
		
		protected File resolve(File file, File source, File target) {
			return new File(normalize(file).replace(normalize(source), normalize(target)));
		}
		
		protected String normalize(File file) {
			return file.toPath().normalize().toFile().getAbsolutePath();
		}
		
		protected void notify(File file, String operation) {
			for (BiConsumer<File, String> listener : this.listeners)
				Try.attempt(() -> listener.accept(file, operation));
		}
	}
	
	public static File file(String path) {
		return new File(path);
	}
	
	public static File file(java.io.File directory, String name) {
		return new File(directory, name);
	}
	
	public static File file(File directory, String name) {
		return new File(directory, name);
	}
	
	public static File file(java.io.File file) {
		return new File(file);
	}
}
