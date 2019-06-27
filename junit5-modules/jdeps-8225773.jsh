/open https://github.com/sormuras/bach/raw/master/BUILDING
run("jdeps", "--version")

var version = "5.0-RC2"

get("lib", "org.junit.jupiter", "junit-jupiter", "5." + version)
get("lib", "org.junit.jupiter", "junit-jupiter-api", "5." + version)
get("lib", "org.junit.jupiter", "junit-jupiter-engine", "5." + version)
get("lib", "org.junit.jupiter", "junit-jupiter-migrationsupport", "5." + version)
get("lib", "org.junit.jupiter", "junit-jupiter-params", "5." + version)

get("lib", "org.junit.vintage", "junit-vintage-engine", "5." + version)

get("lib", "org.junit.platform", "junit-platform-commons", "1." + version)
get("lib", "org.junit.platform", "junit-platform-console", "1." + version)
get("lib", "org.junit.platform", "junit-platform-engine", "1." + version)
get("lib", "org.junit.platform", "junit-platform-launcher", "1." + version)
get("lib", "org.junit.platform", "junit-platform-reporting", "1." + version)
get("lib", "org.junit.platform", "junit-platform-runner", "1." + version)
get("lib", "org.junit.platform", "junit-platform-suite-api", "1." + version)
get("lib", "org.junit.platform", "junit-platform-testkit", "1." + version)

get("lib", "org.apiguardian", "apiguardian-api", "1.1.0")
get("lib", "org.assertj", "assertj-core", "3.12.2")
get("lib", "org.opentest4j", "opentest4j", "1.2.0")
get("lib", "junit", "junit", "4.13-beta-3")

var modules = new ArrayList<String>()
modules.add("org.junit.jupiter")
// modules.add("org.junit.jupiter.api") // Contains Kotlin assets...
modules.add("org.junit.jupiter.engine")
modules.add("org.junit.jupiter.migrationsupport")
// modules.add("org.junit.jupiter.params") // Shadows AssertJ Core JAR...
modules.add("org.junit.platform.commons")
// modules.add("org.junit.platform.console") // Shadows PicoCLI JAR...
modules.add("org.junit.platform.engine")
modules.add("org.junit.platform.launcher")
modules.add("org.junit.platform.reporting")
modules.add("org.junit.platform.suite.api")
modules.add("org.junit.platform.testkit")

var args = new Arguments()
args.add("--module-path").add("lib")
args.add("--multi-release").add("9")
args.add("--check")
args.add(String.join(",", modules))
run("jdeps", args.toArray())

/exit
