# Lang Utilities
### javax.lang.System
The System class provides convenience methods for printing out values to the console and getting the current system time

#### Printing out values
```java
import static javax.lang.System.*;
...

println("Hello World");  // prints out Hello World
println("Hello", "World", 10, true, 1 + 1);   // prints out Hello World 10 true 2
```

#### Getting system time
```java
import static javax.lang.System.*;
...

println(now());  // prints out current time (1492121352021)
```

<br>

### javax.lang.Try
The Try class provides convenience methods for executing code and handling errors in a functional way that produces cleaner code than typical try/catch blocks.

#### Try with null returned on error
```java
import javax.lang.Try
import static javax.lang.System.*;
...
int value = Try.attempt(() -> {
  return 1 + 1;
});

println(value); // prints out 2
```
```java
import javax.lang.Try
import static javax.lang.System.*;
...
int value = Try.attempt(() -> {
  return 1 / 0;
});

println(value); // prints out null
```

#### Try / catch with static default value on error
```java
import javax.lang.Try
import static javax.lang.System.*;
...
int value = Try.attempt(() -> {
  return 1 / 0;
}, 4);

println(value); // prints out 4
```

#### Try / catch with function to call on error
```java
import javax.lang.Try
import static javax.lang.System.*;
...

int value = Try.attempt(() -> {
  return 1 / 0;
}, () -> 24);

println(value); // prints out 24
```

<br>

### javax.lang.Runtime
The Runtime class provides methods for executing command line executables and other commands.
#### Running command line executable
```java
import javax.io.Streams;
import static javax.lang.Runtime.*;
import static javax.lang.System.*;
...

println(Streams.read(execute("echo 4").getInputStream())) // prints out 4
```