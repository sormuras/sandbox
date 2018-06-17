package foo;

import org.junit.jupiter.api.*;

class FooTests {

  @Test
  void hello() {
    Assertions.assertEquals("foo", new Foo().hello());
  }

  @Test
  void module() {
    Assertions.assertEquals("foo", FooTests.class.getModule().getName());
  }
}
