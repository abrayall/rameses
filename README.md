<img src="http://www.clipartlord.com/wp-content/uploads/2013/01/king-tut.png" width="150px" />
# Rameses - Java 8+ Utility Library

Rameses is a Java 8 (and above) utility library intented to make Java code easier, cleaner and less error prone.  It is lightweight, and has no dependencies!  

Add Rameses to your project now, and start taking advantage of all it has to offer.

## Install
Download the Rameses jar file and add it to your project's classpath.

## Documentation
### Lang Utilities
#### javax.lang.Try
The Try class provides convenience methods for executing code and handling errors in a functional way that produces cleaner code than typical try/catch blocks

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
The List class provides convenience constructors and 'pimped' wrapper implementation to make creating and using lists much easier and results in cleaner code.

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
