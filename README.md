<img src="http://www.clipartlord.com/wp-content/uploads/2013/01/king-tut.png" width="150px" />
# Rameses - Java 8+ Utility Library

Rameses is a Java 8 (and above) utility library intended to make Java code easier, cleaner and less error prone.  It is lightweight, and has no dependencies!  

Add Rameses to your project now, and start taking advantage of all it has to offer.

## Install
Download the Rameses jar file and add it to your project's classpath.

## Documentation
### Lang Utilities
#### javax.lang.Try
The Try class provides convenience methods for executing code and handling errors in a functional way that produces cleaner code than typical try/catch blocks.

##### Try with null returned on error
```java
import javax.lang.Try
...
int value = Try.attempt(() -> {
  return 1 + 1;
});

System.out.println(value); // prints out 2
```
```java
import javax.lang.Try
...
int value = Try.attempt(() -> {
  return 1 / 0;
});

System.out.println(value); // prints out null
```

##### Try / catch with static default value on error
```java
import javax.lang.Try
...
int value = Try.attempt(() -> {
  return 1 / 0;
}, 4);

System.out.println(value); // prints out 4
```

##### Try / catch with function to call on error
```java
import javax.lang.Try
...

int value = Try.attempt(() -> {
  return 1 / 0;
}, () -> 24);

System.out.println(value); // prints out 24
```

<br><br>
### Collection Utilities
#### javax.util.List
The List class provides convenience constructors and 'pimped' java.util.List wrapper that makes instantiating and working lists much easier!

##### Simple empty list of strings
```java
static import java.util.List.*;
...
List<String> list = list();
System.out.println(list.size()) // prints out 0
```

##### Simple list of string creatation with elements
```java
static import java.util.List.*;
...
List<String> list = list("foo", "bar", "test");

System.out.println(list.size()); // prints out 3
System.out.println(list.get(1)); // prints out "bar"
```

##### Simple empty list of ints
```java
static import java.util.List.*;
...
List<Integer> list = list();

System.out.println(list.size()); // prints out 0
```

##### Simple list of ints creatation with elements
```java
static import java.util.List.*;
...
List<Integer> list = list(3, 2, 1);

System.out.println(list.size()); // prints out 3
System.out.println(list.get(1)); // prints out 2
```

##### Simple list of mixed types with elements
```java
static import java.util.List.*;
...
List<Object> list = list("foo", 2, new Object());

System.out.println(list.size()); // prints out 3
System.out.println(list.get(1)); // prints out 2
```

##### List creatation with forced element type
```java
static import java.util.List.*;
...
list(String.class);  // returns List<String>()
```

##### Convert java.util.List to javax.util.List
```java
import java.util.ArrayList;
static import java.util.List.*;
...
List<String> list = list(new ArrayList<String>(), "test", "Test", "foo");
System.out.println(list.size()); // prints out 3
System.out.println(list.getClass().getName()); // prints out javax.util.List
```

##### List concatenation
```java
static import java.util.List.*;
...
List<String> list = list("4", "5", "6").concat(list("7", "8")).append("9", "10")
System.out.println(list.size()); // prints out 7
System.out.println(list.get(6)); // prints out 10
```

#### Syncronized list
```java
static import java.util.List.*;
...
List<String> list = list("4", "5", "6").sychronize();
List<Integer> synced = synchronized(list(1, 2, 3));
```

<br>
#### javax.util.Map
The Map class provides convenience constructors and 'pimped' java.util.Map wrapper that makes instantiating and working maps much easier!

##### Simple empty map
```java
import static javax.util.Map.*;
...

Map<String, String> map = map();
Map<String, Long> longs = map();

System.out.println(map.size()); // prints out 0
System.out.println(longs.size()); // prints out 0
```

##### Simple map with initial values
```java
import static javax.util.Map.*;
...

Map<String, String> map = map(entry("Foo", "bar"), entry("Bar", "foo"));
Map<String, Long> longs = map(
  entry("foo", 1),
  entry("bar", 2),
  entry("something", 3)
);

System.out.println(map.size()); // prints out 2
System.out.println(longs.size()); // prints out 3
System.out.println(longs.get("foo")); // prints out 1
System.out.println(longs.get("foobar", 10)); // prints out 10
```

##### Convert java.util.Map to javax.util.Map
```java

import java.util.LinkedHashMap;
import static javax.util.Map.*;
...

Map<String, String> map = map(new LinkedHashMap<String, String>,
  entry("foo", "bar"),
  entry("bar", "foo"),
  entry("something", "nothing")
);

System.out.println(map.size()); // prints outs 3
System.out.println(map.keySet()); // prints out "foo", "bar", "something" -- LinkedHashMap keeps key insertion order
```

##### Syncronized map
```java
import static javax.util.Map.*;
...
Map<String, String> map = map(String.class, String.class).synchronize();
Map<String, String> synced = synchronize(map());
Map<String, String> linked = map(LinkedHashMap<String, String>,
  entry("foo", "bar"),
  entry("bar", "foo")
).synchronize();

System.out.println(linked.size()); // prints out 2
```

<br><br>
### I/O Utilities
#### javax.io.Streams
The Streams class provides convenience methods for reading, writing and copying streams.

##### Read from stream
```java
import static javax.io.Streams.*;
...

System.out.println(read(new StringInputStream("foobar")));  // prints out foobar
```

##### Write to stream
```java
import static javax.io.Streams.*;
...

System.out.println(write("foobar", new StringOutputStream("foobar")).toString());  // prints out foobar
```
#### javax.io.File
The File class provides convenience methods for creating, deleting, reading, writing, locking and watching files on the filsystem.

###### Create a file
```java
import javax.io.File.*;
...

file("test.txt").create();
```

##### Delete a file
```java
import javax.io.File.*;
...

file("test.txt").delete();
```

##### Reads a file into a string
```java
import javax.io.File.*;
...

String contents = file("test.txt").read();
```

#### Writes a string to a file
```java
import javax.io.File.*;
...

file("test.txt").write("hello world");
```

#### Writes and reads a string to a file
```java
import javax.io.File.*;
...

System.out.println(file("test.txt").write("hello world").read());  //prints out hello word
```

#### Locks a file from being accessed by other processes
```java
import javax.io.File.*;
...

file("test.txt").create().lock(lock -> System.out.println("got lock"), lock -> System.out.println("did not get lock"));
```

#### Watches a file for changes
```java
import javax.io.File.*;
...

File file = file("test.txt").create().write("foo").watch((file, action) -> System.out.println(file.name() + " - " + action));
file.write("test");  //prints out test.txt - modify
file.delete(); //prints out test.txt - delete
```
