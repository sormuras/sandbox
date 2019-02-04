rd /s /q bin
cls
javac -d bin/api src/Api.java
javac -d bin/checker -cp bin/api src/Checker.java
javac -d bin/test -cp bin/api --processor-path bin/api;bin/checker -processor Checker -J-Dde.sormuras.verbose=true src/Test.java
java -cp bin/test src/Main.java