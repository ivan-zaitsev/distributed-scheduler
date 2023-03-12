# distributed-scheduler

## Components

Core (required):
```
scheduler-core: Java 17, Maven, Spring 3
```

Providers database (required):
```
scheduler-discovery-provider-database-postresql: PostgreSQL
```

Providers discovery (required):
```
scheduler-discovery-provider-discovery-zookeeper: Spring Cloud Zookeeper
```

Providers leader (optional):
```
scheduler-discovery-provider-leader-zookeeper: Spring Cloud Zookeeper
```

UI (optional):
```
scheduler-ui: Angular 15
```

Examples (optional):
```
infrastructure: Terraform, Kubernetes
scheduler-rest-api-gateway-zookeeper: Spring Cloud Gateway, Zookeeper
scheduler-partitioning-worker-zookeper: Zookeeper Discovery Provider
scheduler-leadership-worker-zookeper: Zookeeper Leader Provider
```
