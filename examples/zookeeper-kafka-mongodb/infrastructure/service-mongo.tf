resource "helm_release" "mongo_cluster" {
  name      = "mongo"
  namespace = "scheduler"

  repository = "https://charts.bitnami.com/bitnami"
  chart      = "mongodb-sharded"
  version    = "6.3.3"

  values = [<<-EOF
    service:
      type: LoadBalancer
    global:
      storageClass: nfs-csi
    auth:
      rootUser: root
      rootPassword: password
    configsvr:
      replicaCount: 3
    mongos:
      replicaCount: 3
    shards: 3
    shardsvr:
      dataNode:
        replicaCount: 3
    EOF
  ]

  depends_on = [kubernetes_manifest.namespace_scheduler]
}
