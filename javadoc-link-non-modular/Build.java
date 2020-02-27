import java.lang.module.ModuleFinder;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.spi.ToolProvider;

class Build {
  public static void main(String... args) throws Exception {
    Files.createDirectories(Path.of("lib"));
    get("junit", "4.13", "junit", "junit");
    get("org.opentest4j", "1.2.0", "org.opentest4j", "opentest4j");
    get("org.assertj.core", "3.15.0", "org.assertj", "assertj-core");
    Files.createDirectories(Path.of("bin/api"));
    run(
        "javadoc",
        "-d",
        "bin/api",
        "-Xdoclint:-missing",
        "--module",
        "foo",
        "--module-source-path",
        "src",
        "--module-path",
        "lib",
        "-link", // ok
        "https://docs.oracle.com/en/java/javase/11/docs/api",
        "-link", // ok
        "https://junit.org/junit4/javadoc/4.13",
        "-link", // not ok
        "https://ota4j-team.github.io/opentest4j/docs/1.2.0/api",
        "-link", // not ok
        "https://javadoc.io/doc/org.assertj/assertj-core/3.15.0");
  }

  static void get(String module, String version, String group, String artifact) throws Exception {
    var local = Path.of("lib", module + "-" + version + ".jar");
    if (Files.exists(local)) return;
    var repo = "https://repo1.maven.org/maven2";
    var file = artifact + "-" + version + ".jar";
    var remote = new URL(String.join("/", repo, group.replace('.', '/'), artifact, version, file));
    System.out.printf("<< %s...%n", remote);
    try (var stream = remote.openStream()) {
      Files.copy(stream, local);
    }
  }

  static void run(String name, String... args) {
    System.out.printf(">> %s %s%n", name, String.join("\n    ", args));
    var tool = ToolProvider.findFirst(name).orElseThrow();
    var code = tool.run(System.out, System.err, args);
    if (code != 0) throw new Error("Non-zero exit code: " + code);
  }
}
