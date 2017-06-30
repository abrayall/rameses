# I/O Utilities
## javax.io.Streams
The Streams class provides convenience methods for reading, writing and copying streams.

### Read from stream
```java
import static javax.io.Streams.*;
import static javax.lang.System.*;
...

println(read(new StringInputStream("foobar")));  // prints out foobar
```

### Write to stream
```java
import static javax.io.Streams.*;
import static javax.lang.System.*;
...

println(write("foobar", new StringOutputStream("foobar")).toString());  // prints out foobar
```
## javax.io.File
The File class provides convenience methods for creating, deleting, reading, writing, locking and watching files on the filsystem.

## Create a file
```java
import javax.io.File.*;
...

file("test.txt").create();
```

### Delete a file
```java
import javax.io.File.*;
...

file("test.txt").delete();
```

### Reads a file into a string
```java
import javax.io.File.*;
...

String contents = file("test.txt").read();
```

## Writes a string to a file
```java
import javax.io.File.*;
...

file("test.txt").write("hello world");
```

## Writes and reads a string to a file
```java
import javax.io.File.*;
import static javax.lang.System.*;
...

println(file("test.txt").write("hello world").read());  //prints out hello word
```

## Locks a file from being accessed by other processes
```java
import javax.io.File.*;
import static javax.lang.System.*;
...

file("test.txt").create().lock(lock -> println("got lock"), lock -> println("did not get lock"));
```

## Watches a file for changes
```java
import javax.io.File;
import static javax.io.File.*;
import static javax.lang.System.*;
...

File file = file("test.txt").create().write("foo").watch((file, action) -> println(file.name() + " - " + action));
file.write("test");  //prints out test.txt - modify
file.delete(); //prints out test.txt - delete
```
