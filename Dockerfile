FROM amazoncorretto:21-alpine-jdk

COPY target/admin-0.0.1-SNAPSHOT.jar ortegadmin.jar

ENTRYPOINT["java", "-jar", "/ortegadmin.jar"]