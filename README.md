<img src="http://www.clipartlord.com/wp-content/uploads/2013/01/king-tut.png" width="150px" />
# Rameses - Java 8+ Utility Library

Rameses is a Java 8 (and above) utility library intented to make Java code easier, cleaner and less error prone.  It is lightweight, and has no dependencies!  

Add Rameses to your project now, and start taking advantage of all it has to offer.

### Install
Download the Rameses jar file and add it to your project's classpath.

### Documentation
#### javax.lang.Try
The Try class provides convenience methods for executing code and handling errors in a functional way that produces cleaner code than typical try/catch blocks

##### Try with null returned on error
```java
import javax.lang.Try

int value = Try.attempt(() -> {
  return 1 + 1;
});

System.out.println(value); // prints out 2
```
```java
import javax.lang.Try

int value = Try.attempt(() -> {
  return 1 / 0;
});

System.out.println(value); // prints out null
```

##### Try / catch with static default value on error
```java
import javax.lang.Try

int value = Try.attempt(() -> {
  return 1 / 0;
}, 4);

System.out.println(value); // prints out 4
```

##### Try / catch with function to call on error
int value = Try.attempt(() -> {
  return 1 / 0;
}, () -> 24);

System.out.println(value); // prints out 24
```
