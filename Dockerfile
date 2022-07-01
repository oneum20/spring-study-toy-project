FROM openjdk:11

# TODO: DB ARG


WORKDIR /app
COPY target/study-toy-0.0.1-SNAPSHOT.jar app.jar

RUN chown -R 1000:1000 /app

EXPOSE 8080

# TODO: profile
# TODO: log dir

USER 1000

ENTRYPOINT ["java", "-jar", "app.jar"]
