/open https://github.com/sormuras/bach/raw/master/src/bach/Bach.java

var dependencies = Paths.get("dependencies")
var mains = List.of(Paths.get("modules"))
var tests = List.of(Paths.get("modules-test"))
var generated = Paths.get("modules-test-generated")

var bach = new Bach()

bach.util.download(URI.create("http://central.maven.org/maven2/org/junit/platform/junit-platform-commons/1.2.0/junit-platform-commons-1.2.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/junit/platform/junit-platform-console/1.2.0/junit-platform-console-1.2.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/junit/platform/junit-platform-engine/1.2.0/junit-platform-engine-1.2.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/junit/platform/junit-platform-launcher/1.2.0/junit-platform-launcher-1.2.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.2.0/junit-jupiter-api-5.2.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/junit/jupiter/junit-jupiter-engine/5.2.0/junit-jupiter-engine-5.2.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/opentest4j/opentest4j/1.1.0/opentest4j-1.1.0.jar"), dependencies)
bach.util.download(URI.create("http://central.maven.org/maven2/org/apiguardian/apiguardian-api/1.0.0/apiguardian-api-1.0.0.jar"), dependencies)

bach.util.download(URI.create("https://jitpack.io/com/github/sormuras/sors-test-module-processor/master-SNAPSHOT/sors-test-module-processor-master-SNAPSHOT.jar"), dependencies)

Files.createDirectories(generated)

var processOnly = bach.command("javac", "-proc:only", "-verbose", "-Werror")
processOnly.addAll("--module-path", dependencies)
processOnly.addAll("--processor-module-path", dependencies)
processOnly.addAll("--add-modules", "de.sormuras.sors.testmodule")
processOnly.addAll("-s", generated.resolve("foo"))
processOnly.add("modules-test/foo/foo/package-info.java")
processOnly.run()

var javac = new JdkTool.Javac()
javac.verbose = true
javac.destination = Paths.get("target", "test")
javac.modulePath = List.of(dependencies)
javac.moduleSourcePath = List.of(Paths.get("modules-test"), generated)
javac.patchModule = bach.util.getPatchMap(tests, mains)
bach.run(javac)

var testRun = new JdkTool.Java()
testRun.modulePath = List.of(javac.destination, dependencies)
testRun.addModules = List.of("foo")
testRun.module = "org.junit.platform.console"
testRun.args = List.of("--scan-modules")
bach.run(testRun)

/exit
