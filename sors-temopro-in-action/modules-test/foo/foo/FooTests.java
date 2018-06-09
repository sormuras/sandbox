package foo;

import org.junit.jupiter.api.*;

class FooTests {

  @Test
  void hello() {
    Assertions.assertEquals("foo", new Foo().hello());
  }
}
