import de.sormuras.brahms.resource.ResourceSupplier;
import org.testcontainers.containers.GenericContainer;

public class Redis6379 implements ResourceSupplier<GenericContainer> {

    private final GenericContainer redis;

    public Redis6379() {
        this.redis= new GenericContainer("redis:3.0.2").withExposedPorts(6379);
        redis.start();
    }

    @Override
    public void close() {
        redis.stop();
    }

    @Override
    public GenericContainer get() {
        return redis;
    }

}
