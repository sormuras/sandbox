# java-annotation-on-module

A `module` declaration is not allowed to be a target of an annotation that lacks an `@Target` meta-annotation.

```text
./test/module-info.java:1: error: annotation type not applicable to this kind of declaration
@test.A
^
1 error
```

> If an `@Target` meta-annotation **is not present** on an annotation type `T`,
> then an annotation of type `T` may be written as a modifier for any declaration
> except a type parameter declaration.

Copied from [Target](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/lang/annotation/Target.html)'s API documentation.
See also: https://docs.oracle.com/javase/specs/jls/se15/html/jls-9.html#jls-9.6.4.1

- Log: https://github.com/sormuras/sandbox/runs/1204815696#step:5:10
- Motivation: https://github.com/junit-team/junit5/issues/2391
