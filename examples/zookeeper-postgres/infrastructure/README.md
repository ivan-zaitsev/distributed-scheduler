# Description

Start minikube:
```
minikube start --memory=8192 --cpus=4 --disk-size=40g --vm-driver=virtualbox --no-vtx-check
minikube docker-env
```

Enable ingress, tunnel:

```
minikube addons enable ingress
minikube tunnel
```

Apply terraform changes:

```
terraform apply
```

Get all kube components:
```
kubectl get all --all-namespaces
```

Forward kube service ports to localhost:
```
kubectl port-forward service/postgres-service 5432:5432 --namespace postgres
kubectl port-forward service/zookeeper-service 2181:2181 --namespace zookeeper
```
