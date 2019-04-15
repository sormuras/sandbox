//usr/bin/env jshell --show-version --execution local "$0" "$@"; exit $?

/open https://raw.githubusercontent.com/junit-team/junit5-samples/master/junit5-modular-world/BUILDING

run("javac", "--version")

//
// Resolve external libraries
//

get("build/modules/lib", "org.apiguardian", "apiguardian-api", "1.0.0")
get("build/modules/lib", "org.opentest4j", "opentest4j", "1.1.1")

get("build/modules/lib", URI.create("https://javadoc.jitpack.io/com/github/junit-team/junit5/junit-jupiter-api/modules-SNAPSHOT/junit-jupiter-api-modules-SNAPSHOT.jar"))
get("build/modules/lib", URI.create("https://javadoc.jitpack.io/com/github/junit-team/junit5/junit-jupiter-engine/modules-SNAPSHOT/junit-jupiter-engine-modules-SNAPSHOT.jar"))

get("build/modules/lib", URI.create("https://javadoc.jitpack.io/com/github/junit-team/junit5/junit-platform-commons/modules-SNAPSHOT/junit-platform-commons-modules-SNAPSHOT.jar"))
get("build/modules/lib", URI.create("https://javadoc.jitpack.io/com/github/junit-team/junit5/junit-platform-engine/modules-SNAPSHOT/junit-platform-engine-modules-SNAPSHOT.jar"))
get("build/modules/lib", URI.create("https://javadoc.jitpack.io/com/github/junit-team/junit5/junit-platform-launcher/modules-SNAPSHOT/junit-platform-launcher-modules-SNAPSHOT.jar"))

//
// Compile module descriptors
//

var args = new Arguments()
args.add("-verbose")
args.add("-d").add("build/classes")
args.add("--add-reads").add("org.junit.platform.launcher=org.junit.platform.engine")
args.add("--module-path").add("build/modules/lib")
args.addAllFiles("src/main/java", ".java")
run("javac", args.toArray())

/exit
