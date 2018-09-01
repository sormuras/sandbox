package foo;

import org.junit.jupiter.api.Test;

class PackageFooTests {

  @Test
  void accessPackageFoo() {
    assert PackageFoo.class.getModule() == PackageFooTests.class.getModule();
  }
}
