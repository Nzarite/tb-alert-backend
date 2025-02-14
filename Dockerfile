FROM amazoncorretto:21-alpine
VOLUME /tmp
COPY target/tb-alert-backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/app.jar"]
