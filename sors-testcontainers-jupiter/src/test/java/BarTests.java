import de.sormuras.brahms.resource.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.GenericContainer;

@ExtendWith(ResourceManager.class)
class BarTests {
  @Test
  void bar(@Resource(Redis6379.class) GenericContainer container) {}
}
