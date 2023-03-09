# Description

```
Start minikube:

minikube start --memory=8192 --cpus=4 --disk-size=40g --vm-driver=virtualbox --no-vtx-check
minikube docker-env
```

```
Enable ingress, tunnel:

minikube addons enable ingress
minikube tunnel
```

```
Apply terraform changes:

terraform apply
```

```
Get kube components:

kubectl get all --all-namespaces
```
