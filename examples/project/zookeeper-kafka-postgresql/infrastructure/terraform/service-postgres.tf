resource "helm_release" "postgres_deployment" {
  name      = "postgres"
  namespace = "scheduler"

  repository = "https://charts.bitnami.com/bitnami"
  chart      = "postgresql"
  version    = "12.4.1"

  values = [<<-EOF
    global:
      storageClass: nfs-csi
    auth:
      postgresPassword: password
    persistence:
      size: 50Gi
    EOF
  ]
}
