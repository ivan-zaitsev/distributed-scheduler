resource "helm_release" "mongo_deployment" {
  name      = "mongo"
  namespace = "scheduler"

  repository = "https://charts.bitnami.com/bitnami"
  chart      = "mongodb-sharded"
  version    = "6.3.3"

  values = [<<-EOF
    global:
      storageClass: nfs-csi
    auth:
      rootUser: root
      rootPassword: password
    configsvr:
      replicaCount: 2
      persistence:
        size: 50Gi
    mongos:
      replicaCount: 2
    shards: 3
    shardsvr:
      persistence:
        size: 50Gi
      dataNode:
        replicaCount: 2
    EOF
  ]
}

resource "kubernetes_manifest" "mongo_service" {
  manifest = yamldecode(<<-EOF
    apiVersion: v1
    kind: Service
    metadata:
      name: mongo-mongos-sharded-headless
      namespace: scheduler
    spec:
      type: ClusterIP
      clusterIP: None
      selector:
        app.kubernetes.io/component: mongos
      ports:
        - name: mongos
          port: 27017
          targetPort: 27017
    EOF
  )
}
