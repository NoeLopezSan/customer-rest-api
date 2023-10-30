FROM eclipse-temurin:17

LABEL maintainer="Noe Lopez"

WORKDIR /app

COPY target/restdemo1-0.0.1-SNAPSHOT.jar /app/restdemo.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "restdemo.jar"]