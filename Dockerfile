FROM openjdk:15
VOLUME /tmp
COPY target/vote-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8383
ENTRYPOINT ["java", "-jar", "/app.jar"]