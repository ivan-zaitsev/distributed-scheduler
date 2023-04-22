resource "helm_release" "zookeeper_deployment" {
  name      = "zookeeper"
  namespace = "scheduler"

  repository = "https://charts.bitnami.com/bitnami"
  chart      = "zookeeper"
  version    = "11.2.1"

  values = [<<-EOF
    global:
      storageClass: nfs-csi
    replicaCount: 3
    persistence:
      size: 100Gi
    EOF
  ]

  depends_on = [kubernetes_manifest.namespace_scheduler]
}
