import de.sormuras.brahms.resource.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.GenericContainer;

@ExtendWith(ResourceManager.class)
class GenericContainerTests {
    @Test
    void testGlobal(@GlobalResource(Redis6379.class) GenericContainer container) {}
    @Test
    void testClass(@ClassResource(Redis6379.class) GenericContainer container) {}
    @Test
    void testMethod(@MethodResource(Redis6379.class) GenericContainer container) {}
}
