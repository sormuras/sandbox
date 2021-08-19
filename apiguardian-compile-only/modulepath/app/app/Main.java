package app;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

@API(status = Status.STABLE)
class Main {
  public static void main(String... args) {
    System.out.printf("BEGIN with %s%n", System.getProperty("jdk.module.path"));
    for(var annotation : Main.class.getAnnotations()) System.out.println(annotation);
    System.out.printf("END.%n%n");
  }
}
