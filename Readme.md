mvn -q exec:java -Dexec:mainClass="swingy.App"
java -jar target/swingy-1.0.jar console|gui

mvn clean package
java -jar target/swingy-1.0.jar console|gui