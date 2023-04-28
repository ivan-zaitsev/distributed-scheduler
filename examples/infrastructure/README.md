# infrastructure

## Install kubernetes

### Install nfs storage driver
```
curl -skSL https://raw.githubusercontent.com/kubernetes-csi/csi-driver-nfs/v4.2.0/deploy/install-driver.sh | bash -s v4.2.0 --
kubectl delete CSIDriver nfs.csi.k8s.io
kubectl apply -f kubernetes/csi-storage.yaml
```

### Install bare metal load-balancer
To access kubernetes resources externally outside cluster which is located on premises one option is to use metallb.
```
kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.13.9/config/manifests/metallb-native.yaml
kubectl apply -f kubernetes/metallb-ip-pool.yaml
```

### Install wireguard
To access kubernetes resources externally outside cluster one option is to tunnel specific traffic on local machine through VPN server which is located on kubernetes cluster.
```
kubectl apply -f kubernetes/wireguard.yaml
kubectl exec -it statefulset.apps/wireguard -- cat /config/peer1/peer1.conf
```

### Install docker registry
```
kubectl apply -f kubernetes/docker-registry.yaml
```

## Install terraform

### Install kubernetes resources
```
terraform -chdir=terraform apply -target null_resource.elastic_operator_manifest
terraform -chdir=terraform apply
```

### Retrieve elastic password
```
kubectl get secret elasticsearch-es-elastic-user -n scheduler -o jsonpath='{.data.elastic}' | base64 -d
```
