# distributed-scheduler

## Components

Core (required):
```
scheduler-core: Java 17, Maven, Spring 3
```

Providers discovery (required):
```
scheduler-discovery-provider-zookeeper: Spring Cloud Zookeeper
```

Providers queue (required):
```
scheduler-queue-provider-kafka: Spring Kafka
```

Providers database (required):
```
scheduler-database-provider-mongodb: Spring Data MongoDB
```

UI (optional):
```
scheduler-ui: Angular 15
```

Examples (optional):
```
infrastructure: Terraform, Kubernetes

zookeeper-kafka-mongodb:
  scheduler-rest-api-gateway: Spring Cloud Gateway, Spring Cloud Zookeeper, Elastic APM
  scheduler-worker: Zookeeper Discovery Provider, Kafka Queue Provider, MongoDB Database Provider, Elastic APM

zookeeper-kafka-postgresql:
  scheduler-rest-api-gateway: Spring Cloud Gateway, Spring Cloud Zookeeper, Elastic APM
  scheduler-worker: Zookeeper Discovery Provider, Kafka Queue Provider, PostgreSQL Database Provider, Elastic APM
```
