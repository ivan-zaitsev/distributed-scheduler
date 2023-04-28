# zookeeper-kafka-postgresql

## Install applications

### Package applications
```
mvn -f ../ clean install

mvn -f scheduler-rest-api-gateway/ clean package
mvn -f scheduler-worker/ clean package
```

### Build docker images
```
docker build -t distributed-scheduler-rest-api-gateway:1.0.0 scheduler-rest-api-gateway/
docker tag distributed-scheduler-rest-api-gateway:1.0.0 <REGISTRY_URL>/distributed-scheduler-rest-api-gateway:1.0.0
docker push <REGISTRY_URL>/distributed-scheduler-rest-api-gateway:1.0.0

docker build -t distributed-scheduler-worker:1.0.0 scheduler-worker/
docker tag distributed-scheduler-worker:1.0.0 <REGISTRY_URL>/distributed-scheduler-worker:1.0.0
docker push <REGISTRY_URL>/distributed-scheduler-worker:1.0.0
```
