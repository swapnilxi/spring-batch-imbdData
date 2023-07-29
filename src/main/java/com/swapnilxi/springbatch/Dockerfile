# Use a base image with Java installed
FROM adoptopenjdk:17-jre-hotspot

WORKDIR /app

COPY target/imdbData.jar imdbData.jar

EXPOSE 8081

CMD ["java", "-jar", "imdbData.jar"]
