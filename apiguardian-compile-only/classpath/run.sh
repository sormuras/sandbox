set -o xtrace

javac -cp lib/* App.java
java App
java -cp .:lib/* App
