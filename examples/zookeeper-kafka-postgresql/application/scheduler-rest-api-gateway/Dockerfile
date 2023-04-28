FROM openjdk:17

COPY target/distributed-scheduler-rest-api-gateway.jar distributed-scheduler-rest-api-gateway.jar

ENTRYPOINT ["java", "-jar", "/distributed-scheduler-rest-api-gateway.jar"]

EXPOSE 8000
