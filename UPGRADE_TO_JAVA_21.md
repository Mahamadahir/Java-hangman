Upgrade to Java 21 (LTS)

This project has been updated to target Java 21 via the Maven configuration. Follow the steps below to update your local environment and build the project.

1) Install JDK 21
   - On Windows, download and install an OpenJDK 21 build (Adoptium Temurin, Eclipse Temurin, or your preferred distribution):
     https://adoptium.net/releases.html?variant=openjdk21

2) Point your JAVA_HOME to the JDK 21 installation and update PATH
   - In PowerShell (temporary for the session):
     $env:JAVA_HOME = 'C:\Program Files\Eclipse Adoptium\jdk-21'
     $env:PATH = "$env:JAVA_HOME\\bin;" + $env:PATH

   - Or set system environment variables via Windows Settings.

3) Verify Java and Maven
   - java -version
   - mvn -v

4) Build
   - mvn -U clean package

Notes
- The project previously targeted Java 11; updating to Java 21 may require code changes if the project used removed APIs or relied on internal JDK internals.
- If you have CI, update its JDK to Java 21.
- If you want automated migration suggestions, consider running OpenRewrite recipes for JDK migration.
