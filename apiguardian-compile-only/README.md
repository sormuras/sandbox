# API Guardian Usages

## classpath

```text
++ javac -cp lib/apiguardian-api-1.1.2.jar App.java
++ java App
BEGIN with .
END.

++ java -cp '.:lib/*' App
BEGIN with .:lib/apiguardian-api-1.1.2.jar
@org.apiguardian.api.API(consumers={"*"}, since="", status=STABLE)
END.
```

## modulepath

```text
++ javac -d classes --module-path lib --module-source-path . --module app
++ java --module-path classes --module app/app.Main
BEGIN with classes
END.

++ java --module-path classes:lib --add-modules org.apiguardian.api --module app/app.Main
BEGIN with classes:lib
@org.apiguardian.api.API(consumers={"*"}, since="", status=STABLE)
END.
```
