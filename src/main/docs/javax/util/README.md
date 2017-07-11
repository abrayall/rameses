# Collection Utilities
## javax.util.List
The List class provides convenience constructors and 'pimped' java.util.List wrapper that makes instantiating and working lists much easier!

### Simple empty list of strings
```java
import static javax.util.List.*;
import static javax.lang.System.*;
...
List<String> list = list();
println(list.size()) // prints out 0
```

### Simple list of string creatation with elements
```java
import static javax.util.List.*;
import static javax.lang.System.*;
...
List<String> list = list("foo", "bar", "test");

println(list.size()); // prints out 3
println(list.get(1)); // prints out "bar"
```

### Simple empty list of ints
```java
import static javax.util.List.*;
import static javax.lang.System.*;
...
List<Integer> list = list();

println(list.size()); // prints out 0
```

### Simple list of ints creatation with elements
```java
import static javax.util.List.*;
import static javax.lang.System.*;
...
List<Integer> list = list(3, 2, 1);

println(list.size()); // prints out 3
println(list.get(1)); // prints out 2
```

### Simple list of mixed types with elements
```java
import static javax.util.List.*;
import static javax.lang.System.*;
...
List<Object> list = list("foo", 2, new Object());

println(list.size()); // prints out 3
println(list.get(1)); // prints out 2
```

### List creatation with forced element type
```java
import static javax.util.List.*;
import static javax.lang.System.*;
...
list(String.class);  // returns List<String>()
```

### Convert java.util.List to javax.util.List
```java
import java.util.ArrayList;
import static javax.util.List.*;
import static javax.lang.System.*;
...
List<String> list = list(new ArrayList<String>(), "test", "Test", "foo");
println(list.size()); // prints out 3
println(list.getClass().getName()); // prints out javax.util.List
```

### List concatenation
```java
import static javax.util.List.*;
import static javax.lang.System.*;
...
List<String> list = list("4", "5", "6").concat(list("7", "8")).append("9", "10")
println(list.size()); // prints out 7
println(list.get(6)); // prints out 10
```

## Syncronized list
```java
import static javax.util.List.*;
import static javax.lang.System.*;
...
List<String> list = list("4", "5", "6").sychronize();
List<Integer> synced = synchronized(list(1, 2, 3));
```

<br>
## javax.util.Map
The Map class provides convenience constructors and 'pimped' java.util.Map wrapper that makes instantiating and working maps much easier!

### Simple empty map
```java
import static javax.util.Map.*;
import static javax.lang.System.*;
...

Map<String, String> map = map();
Map<String, Long> longs = map();

println(map.size()); // prints out 0
println(longs.size()); // prints out 0
```

### Simple map with initial values
```java
import static javax.util.Map.*;
import static javax.lang.System.*;
...

Map<String, String> map = map(entry("Foo", "bar"), entry("Bar", "foo"));
Map<String, Long> longs = map(
  entry("foo", 1),
  entry("bar", 2),
  entry("something", 3)
);

println(map.size()); // prints out 2
println(longs.size()); // prints out 3
println(longs.get("foo")); // prints out 1
println(longs.get("foobar", 10)); // prints out 10
```

### Convert java.util.Map to javax.util.Map
```java

import java.util.LinkedHashMap;
import static javax.util.Map.*;
import static javax.lang.System.*;
...

Map<String, String> map = map(new LinkedHashMap<String, String>,
  entry("foo", "bar"),
  entry("bar", "foo"),
  entry("something", "nothing")
);

println(map.size()); // prints outs 3
println(map.keySet()); // prints out "foo", "bar", "something" -- LinkedHashMap keeps key insertion order
```

### Syncronized map
```java
import static javax.util.Map.*;
import static javax.lang.System.*;
...
Map<String, String> map = map(String.class, String.class).synchronize();
Map<String, String> synced = synchronize(map());
Map<String, String> linked = map(LinkedHashMap<String, String>,
  entry("foo", "bar"),
  entry("bar", "foo")
).synchronize();

println(linked.size()); // prints out 2
```