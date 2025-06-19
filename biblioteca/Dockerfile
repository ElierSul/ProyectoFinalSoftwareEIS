FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/biblioteca-0.0.1-SNAPSHOT.jar biblioteca.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "biblioteca.jar"]