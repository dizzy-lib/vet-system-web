FROM amazoncorretto:21
COPY /target/vetweb-0.0.1-SNAPSHOT.jar /app/miapp.jar
EXPOSE 8080

CMD ["java", "-jar", "/app/miapp.jar"]
