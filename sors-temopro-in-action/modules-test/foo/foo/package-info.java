@de.sormuras.sors.testmodule.TestModule(
    // target mode
    compile = false,

    // compile = false
    mainModuleDescriptorSource = "modules/foo", // module-info.java
    value = {"  requires org.junit.jupiter.api;"}, //

    // compile = true
    mainModuleDescriptorBinary = "target/main/foo", // module-info.class
    testRequires = {"org.junit.jupiter.api"} //
    )
package foo;
