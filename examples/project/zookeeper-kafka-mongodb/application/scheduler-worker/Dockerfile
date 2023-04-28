FROM openjdk:17

COPY target/distributed-scheduler-worker.jar distributed-scheduler-worker.jar

ENTRYPOINT ["java", "-jar", "/distributed-scheduler-worker.jar"]

EXPOSE 8080
