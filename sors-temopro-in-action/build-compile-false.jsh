//
// jshell build-compile-false.jsh
//

/open https://github.com/sormuras/bach/raw/master/src/bach/Bach.java

var ok = 0

var dependencies = Paths.get("dependencies")
var mains = List.of(Paths.get("modules"))
var tests = List.of(Paths.get("modules-test"))
var generated = Paths.get("modules-test-generated")
var target = Paths.get("target")

var bach = new Bach()
bach.util.removeTree(target)
bach.util.removeTree(generated)

bach.util.download(URI.create("http://central.maven.org/maven2/org/junit/platform/junit-platform-commons/1.2.0/junit-platform-commons-1.2.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/junit/platform/junit-platform-console/1.2.0/junit-platform-console-1.2.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/junit/platform/junit-platform-engine/1.2.0/junit-platform-engine-1.2.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/junit/platform/junit-platform-launcher/1.2.0/junit-platform-launcher-1.2.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.2.0/junit-jupiter-api-5.2.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/junit/jupiter/junit-jupiter-engine/5.2.0/junit-jupiter-engine-5.2.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/opentest4j/opentest4j/1.1.0/opentest4j-1.1.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/apiguardian/apiguardian-api/1.0.0/apiguardian-api-1.0.0.jar"), dependencies)

bach.util.download(URI.create("http://central.maven.org/maven2/org/ow2/asm/asm/6.2/asm-6.2.jar"), dependencies)
bach.util.download(URI.create("https://jitpack.io/com/github/sormuras/sors-test-module-processor/master-SNAPSHOT/sors-test-module-processor-master-SNAPSHOT.jar"), dependencies)

//
// main
//

var javacMain = new JdkTool.Javac()
javacMain.destination = target.resolve("main")
javacMain.modulePath = List.of(dependencies)
javacMain.moduleSourcePath = mains
bach.run(javacMain)

//
// process-only test
//
Files.createDirectories(generated)
var processOnly = bach.command("javac", "-proc:only", "-verbose", "-Werror")
processOnly.addAll("-s", generated.resolve("foo"))   // compile = false
processOnly.addAll("-d", target.resolve("test/foo")) // compile = true
processOnly.addAll("--module-path", dependencies)
processOnly.addAll("--add-modules", "ALL-MODULE-PATH")
processOnly.addAll("--processor-module-path", dependencies)
processOnly.add("modules-test/foo/foo/package-info.java")
ok = processOnly.get()

//
// compile test module
//
if (ok == 0) {
  var javac = new JdkTool.Javac();
  javac.verbose = true;
  javac.destination = target.resolve("test");
  javac.modulePath = List.of(dependencies);
  javac.moduleSourcePath = List.of(Paths.get("modules-test"), generated);
  javac.patchModule = bach.util.getPatchMap(tests, mains);
  var compiler = javac.toCommand(bach);
  compiler.arguments.removeIf(arg -> arg.endsWith("package-info.java")); // exclude from compilation
  ok = compiler.get();
}

//
// run test module(s)
//
if (ok == 0) {
  var testRun = new JdkTool.Java();
  testRun.modulePath = List.of(target.resolve("test"), dependencies);
  testRun.addModules = List.of("foo");
  testRun.module = "org.junit.platform.console";
  testRun.args = List.of("--scan-modules");
  bach.run(testRun);
}

/exit
