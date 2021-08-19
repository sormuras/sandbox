set -o xtrace

javac -d classes --module-path lib --module-source-path . --module app
java --module-path classes --module app/app.Main
java --module-path classes:lib --add-modules org.apiguardian.api --module app/app.Main
