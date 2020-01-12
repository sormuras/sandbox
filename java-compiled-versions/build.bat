REM javac -d target/classes --release 9 --module-source-path src --module foo
javac -d target/classes --module-source-path src --module foo
jdeps --module-path target/classes --check foo
