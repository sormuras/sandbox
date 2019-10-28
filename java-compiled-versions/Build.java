import java.lang.module.ModuleFinder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.spi.ToolProvider;

class Build {
  public static void main(String... args) throws Exception {
    run("javac", "--version");

    // Always recompile the module-info.class file...
    Files.deleteIfExists(Path.of("bin/classes/foo/module-info.class"));

    if (args.length == 0)
      run("javac", "-d", "bin/classes", "--module-source-path", "src", "--module", "foo");
    else
      run("javac", "-d", "bin/classes", "--module-source-path", "src", "--module", "foo", "--release", args[0]);

    // Creating and using a modular JAR doesn't change the outcome
    // run("jar", "--create", "--file", "bin/foo.jar", "--module-version", "1.0", "-C", "bin/classes/foo", ".");
    // run("jar", "--describe-module", "--file", "bin/foo.jar");

    run("jdeps", "--module-path", "bin/classes", "--check", "foo");

    System.out.println(ModuleFinder.of(Path.of("bin/classes")).find("foo").orElseThrow().descriptor());

    // "javap" does not emit the version of "java.base"
    // run("javap", "bin/classes/foo/module-info.class");
  }

  static void run(String name, String... args) {
    System.out.printf(">> %s %s%n", name, String.join(" ", args));
    var tool = ToolProvider.findFirst(name).orElseThrow();
    var code = tool.run(System.out, System.err, args);
    if (code != 0) throw new Error("Non-zero exit code: " + code);
  }
}
