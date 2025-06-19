FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/notas-0.0.1-SNAPSHOT.jar notas.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "notas.jar"]