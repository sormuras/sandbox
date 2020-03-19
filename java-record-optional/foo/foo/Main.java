package foo;

import java.util.Optional;

record Foo(String name) {
  public java.util.Optional<String> nameOptional() {
    return java.util.Optional.ofNullable(name);
  }
}

record Bar(Optional<String> name) {}

public class Main {
  public static void main(String... args) {
    System.out.println(new Foo("foo"));
    System.out.println(new Foo("foo").nameOptional().orElseThrow());
    System.out.println(new Foo(null).nameOptional().orElse("?"));

    System.out.println(new Bar(Optional.of("bar")));
    System.out.println(new Bar(Optional.empty()).name().orElse("?"));
  }
}
