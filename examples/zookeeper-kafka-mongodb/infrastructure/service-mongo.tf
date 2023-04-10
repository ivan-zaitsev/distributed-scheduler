resource "helm_release" "mongo_cluster" {
  name = "mongo"

  repository = "https://charts.bitnami.com/bitnami"
  chart      = "mongodb-sharded"

  values = [<<-EOF
    global:
      storageClass: nfs-csi
    configsvr:
      replicaCount: 2
    mongos:
      replicaCount: 2
    shards: 2
    shardsvr:
      dataNode:
        replicaCount: 2
    EOF
  ]
}
