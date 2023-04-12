resource "helm_release" "kafka_cluster" {
  name      = "kafka"
  namespace = "scheduler"

  repository = "https://charts.bitnami.com/bitnami"
  chart      = "kafka"
  version    = "21.4.4"

  values = [<<-EOF
    externalAccess:
      enabled: true
      service:
        type: LoadBalancer
      autoDiscovery:
        enabled: true
    rbac:
      create: true
    global:
      storageClass: nfs-csi
    replicaCount: 3
    kraft:
      enabled: true
    zookeeper:
      enabled: false
    EOF
  ]

  depends_on = [kubernetes_manifest.namespace_scheduler]
}
