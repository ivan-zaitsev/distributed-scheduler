resource "kubernetes_manifest" "namespace_zookeper" {
  manifest = {
    "apiVersion" = "v1"
    "kind"       = "Namespace"
    "metadata" = {
      "name" = "postgres"
    }
  }
}

resource "kubernetes_manifest" "service_postgres" {
  manifest = {
    "apiVersion" = "v1"
    "kind"       = "Service"
    "metadata" = {
      "labels" = {
        "service" = "postgres"
      }
      "namespace" = "postgres"
      "name"      = "postgres-service"
    }
    "spec" = {
      "ports" = [
        {
          "name"       = "client"
          "port"       = 5432
          "targetPort" = 5432
        },
      ]
      "selector" = {
        "service" = "postgres"
      }
      "type" = "LoadBalancer"
    }
  }

  depends_on = [kubernetes_manifest.namespace_zookeper]
}

resource "kubernetes_manifest" "statefulset_postgres" {
  manifest = {
    "apiVersion" = "apps/v1"
    "kind"       = "StatefulSet"
    "metadata" = {
      "labels" = {
        "service" = "postgres"
      }
      "namespace" = "postgres"
      "name"      = "postgres-statefulset"
    }
    "spec" = {
      "replicas" = 1
      "selector" = {
        "matchLabels" = {
          "service" = "postgres"
        }
      }
      "template" = {
        "metadata" = {
          "labels" = {
            "service" = "postgres"
          }
        }
        "spec" = {
          "containers" = [
            {
              "env" = [
                {
                  "name"  = "POSTGRES_USER"
                  "value" = "postgres"
                },
                {
                  "name"  = "POSTGRES_PASSWORD"
                  "value" = "postgres"
                },
                {
                  "name"  = "POSTGRES_DB"
                  "value" = "distributed_scheduler"
                },
              ]
              "image" = "postgres:15.2"
              "name"  = "postgres"
              "ports" = [
                {
                  "containerPort" = 5432
                },
              ]
              "volumeMounts" = [
                {
                  "mountPath" = "/var/lib/postgresql/data"
                  "name"      = "postgres-data"
                },
              ]
            },
          ]
        }
      }
      "volumeClaimTemplates" = [
        {
          "metadata" = {
            "name" = "postgres-data"
          }
          "spec" = {
            "accessModes" = [
              "ReadWriteOnce",
            ]
            "resources" = {
              "requests" = {
                "storage" = "1Gi"
              }
            }
          }
        },
      ]
    }
  }

  depends_on = [kubernetes_manifest.namespace_zookeper]
}
