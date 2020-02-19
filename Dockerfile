FROM openjdk:11
COPY ./target/demo-0.0.1-SNAPSHOT.jar bar
CMD ["java", "-jar", "bar"]