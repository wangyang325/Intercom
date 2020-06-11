FROM openjdk:latest
VOLUME /tmp
ADD target/assignment-1.0.jar assignment.jar
ENTRYPOINT ["java", "-jar", "assignment.jar"]
