Backend quick start
===================

Prerequisites
-------------
1) Install JDK 17 or newer (a JRE is not enough).
2) Make sure `java -version` and `javac -version` are both available.
3) Set JAVA_HOME to the JDK folder and put `$JAVA_HOME/bin` on PATH.

Verify your Java setup
----------------------
Run these commands before Maven:

  java -version
  javac -version
  echo $JAVA_HOME
  mvn -version

`mvn -version` should print a JDK home (not a JRE home).

Run tests
---------
From project root:

  cd backend
  chmod +x mvnw
  ./mvnw test

If you prefer system Maven:

  mvn test

Run app
-------

  ./mvnw spring-boot:run

Then open:
- API: http://localhost:9090/hello
- H2 console: http://localhost:9090/h2-console

Troubleshooting: "No compiler is provided in this environment"
---------------------------------------------------------------
This means Maven is running under a JRE instead of a JDK.

Linux/macOS example:

  export JAVA_HOME=/path/to/jdk-17
  export PATH="$JAVA_HOME/bin:$PATH"

Windows PowerShell example:

  setx JAVA_HOME "C:\\Program Files\\Java\\jdk-17"
  $env:Path = "$env:JAVA_HOME\\bin;" + $env:Path

After updating, restart terminal and re-run:

  mvn -version
  mvn test
