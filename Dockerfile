# Build the executable Spring Boot jar with the bundled Maven wrapper.
FROM eclipse-temurin:21-jdk AS build
WORKDIR /build
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw -B dependency:go-offline
COPY src/ src/
RUN ./mvnw -B -DskipTests clean package

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=build /build/target/hangman-game-1.0-SNAPSHOT.jar app.jar

# OpenShift runs containers with an arbitrary high UID in the root group, so
# the app must not assume a fixed user. Make the jar group-readable and drop root.
RUN chgrp 0 /app/app.jar && chmod g=u /app/app.jar
USER 1001

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
