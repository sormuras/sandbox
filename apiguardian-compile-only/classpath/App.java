import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

@API(status = Status.STABLE)
class App {
    public static void main(String[] args) {
        System.out.printf("BEGIN with %s%n", System.getProperty("java.class.path"));
        for(var annotation : App.class.getAnnotations()) System.out.println(annotation);
        System.out.printf("END.%n%n");
    }
}
