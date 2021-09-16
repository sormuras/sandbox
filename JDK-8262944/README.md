# JDK-8262944

Improve exception message when automatic module lists provider class not in JAR file

> **Description**
> If a JAR is deployed on the module path to be an automatic module, and it contains a `META-INF/services`
> configuration file that lists the name of a class that is not in the JAR file, then error message is
> `"Provider class XXXX not in module"`.
> This could be improved to include the file path or name of the JAR file.

<https://bugs.openjdk.java.net/browse/JDK-8262944>

## Status Quo

```text
Exception in thread "main" java.lang.module.FindException: Unable to derive module descriptor for JDK-8262944.jar
        at java.base/jdk.internal.module.ModulePath.readJar(ModulePath.java:648)
        at java.base/jdk.internal.module.ModulePath.readModule(ModulePath.java:331)
        at java.base/jdk.internal.module.ModulePath.scan(ModulePath.java:237)
        at java.base/jdk.internal.module.ModulePath.scanNextEntry(ModulePath.java:190)
        at java.base/jdk.internal.module.ModulePath.findAll(ModulePath.java:166)
        at test.main(test.java:9)
Caused by: java.lang.module.InvalidModuleDescriptorException: Provider class does.not.Exist not in module
        at java.base/jdk.internal.module.ModulePath.deriveModuleDescriptor(ModulePath.java:555)
        at java.base/jdk.internal.module.ModulePath.readJar(ModulePath.java:644)
        at java.base/jdk.internal.module.ModulePath.readModule(ModulePath.java:331)
        at java.base/jdk.internal.module.ModulePath.scan(ModulePath.java:237)
        at java.base/jdk.internal.module.ModulePath.scanNextEntry(ModulePath.java:190)
        at java.base/jdk.internal.module.ModulePath.findAll(ModulePath.java:166)
        at test.main(test.java:9)
```

Note of previous art: the error message composed for `FindException` does end with the JAR file.

## Improved error message

`Provider class does.not.Exist not in module created for JDK-8262944.jar`

- Insert `automatic` somehow into the message?

  `Provider class does.not.Exist not in module automatically created for JDK-8262944.jar`

- Use absolute path of the JAR file? Or its URI?

  `Provider class does.not.Exist not in module created for D:\dev\github\sormuras\sandbox\JDK-8262944\JDK-8262944.jar`

- Insert name of the provider configuration file that contains the non-existing entry?

  `Provider class does.not.Exist not in module created for JDK-8262944.jar's META-INF/services/java.lang.Runnable configuration`
