import java.lang.module.*;
import java.nio.file.Path;
import java.util.spi.ToolProvider;
import java.util.stream.Stream;

class test {
  public static void main(String... args) {
    var jar = Path.of("JDK-8262944.jar");
    run("jar", "--create", "--file", jar, "-C", ".", ".");
    run("jar", "--describe-module", "--file", jar);

    var expectedMessage = "Provider class does.not.Exist not in module created for " + jar.toAbsolutePath();

    try {
      ModuleFinder.of(jar).findAll();
    } catch (FindException exception) {
      if (exception.getCause() instanceof InvalidModuleDescriptorException imde) {
        var actualMessage = imde.getMessage();
        if (actualMessage.equals(expectedMessage)) {
          return;
        }
        throw new AssertionError(
            """
            Unexpected detail message stored in exception object.

            Expected: %s
              Actual: %s
            """
                .formatted(expectedMessage, actualMessage));
      }
      throw exception;
    }
  }

  static void run(String name, Object... arguments) {
    var args = Stream.of(arguments).map(Object::toString).toList();
    System.out.println(">> " + name + " " + String.join(" ", args));
    var tool = ToolProvider.findFirst(name).orElseThrow();
    var code = tool.run(System.out, System.err, args.toArray(String[]::new));
    if (code != 0) throw new Error("Non-zero exit code: " + code);
  }
}
