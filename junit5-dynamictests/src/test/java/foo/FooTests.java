package foo;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class FooTests {

  @TestFactory
  Stream<DynamicTest> works() {
    return Stream.of(dynamicTest("ok", () -> {}));
  }

  //  @TestFactory
  //  Stream<DynamicTest> fails() {
  //    return Stream.of(dynamicTest("nok", () -> {}).withTestSource(ClassSource.from(getClass())));
  //  }
}
