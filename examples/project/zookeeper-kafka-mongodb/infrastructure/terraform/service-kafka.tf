resource "helm_release" "kafka_deployment" {
  name      = "kafka"
  namespace = "scheduler"

  repository = "https://charts.bitnami.com/bitnami"
  chart      = "kafka"
  version    = "21.4.4"

  values = [<<-EOF
    global:
      storageClass: nfs-csi
    replicaCount: 3
    persistence:
      size: 50Gi
    kraft:
      enabled: true
    zookeeper:
      enabled: false
    EOF
  ]
}
