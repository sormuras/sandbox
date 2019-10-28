import java.util.spi.ToolProvider;

class Build {
  public static void main(String[] args) {
    run("javac", "--version");
    run("javac", "-d", "bin/classes", "--module-source-path", "src", "--module", "foo");
    run("jar", "--create", "--file", "bin/foo.jar", "--module-version", "1.0", "-C", "bin/classes/foo", ".");
    run("jdeps", "--module-path", "bin", "--multi-release", "BASE", "--check", "foo");
    run("jar", "--describe-module", "--file", "bin/foo.jar");
  }

  static void run(String name, String... args) {
    System.out.printf(">> %s %s%n", name, String.join(" ", args));
    var tool = ToolProvider.findFirst(name).orElseThrow();
    var code = tool.run(System.out, System.err, args);
    if (code != 0) throw new Error("Non-zero exit code: " + code);
  }
}
