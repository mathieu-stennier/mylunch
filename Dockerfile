FROM openjdk:8-jdk-alpine
COPY /target/mylunch*.jar mylunch.jar
ENTRYPOINT ["java","-jar","/mylunch.jar"]
