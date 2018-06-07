package de.sormuras.sandbox.temopro;

import javax.tools.StandardLocation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PACKAGE)
public @interface TestModule {

  String[] value() default {};

  StandardLocation sourceLocation() default StandardLocation.CLASS_OUTPUT;

  String sourceModuleAndPackageName() default "";

  String sourceRelativeName() default "module-info.test";

  StandardLocation targetLocation() default StandardLocation.SOURCE_OUTPUT;

  String targetModuleAndPackageName() default "";

  String targetRelativeName() default "module-info.java";
}
