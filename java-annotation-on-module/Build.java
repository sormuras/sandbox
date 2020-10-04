class Build {
  public static void main(String... args) throws Exception {
    run("javac", "-d", "classes", "--module-source-path", ".", "--module", "test");
  }

  static void run(String name, String... args) {
    System.out.printf(">> %s %s%n", name, String.join(" ", args));
    var tool = java.util.spi.ToolProvider.findFirst(name).orElseThrow();
    var code = tool.run(System.out, System.err, args);
    if (code != 0) throw new Error("Non-zero exit code: " + code);
  }
}
