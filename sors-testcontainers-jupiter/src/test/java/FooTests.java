import de.sormuras.brahms.resource.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.GenericContainer;

@ExtendWith(ResourceManager.class)
class FooTests {
  @Test
  void foo(@Resource(Redis6379.class) GenericContainer container) {}
}
