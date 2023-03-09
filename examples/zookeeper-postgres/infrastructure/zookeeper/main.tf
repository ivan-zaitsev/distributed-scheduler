resource "kubernetes_manifest" "namespace_zookeper" {
  manifest = {
    "apiVersion" = "v1"
    "kind"       = "Namespace"
    "metadata" = {
      "name" = "zookeeper"
    }
  }
}

resource "kubernetes_manifest" "service_zookeeper" {
  manifest = {
    "apiVersion" = "v1"
    "kind"       = "Service"
    "metadata" = {
      "labels" = {
        "service" = "zookeeper"
      }
      "namespace" = "zookeeper"
      "name"      = "zookeeper-service"
    }
    "spec" = {
      "ports" = [
        {
          "name"       = "client"
          "port"       = 2181
          "targetPort" = 2181
        },
      ]
      "selector" = {
        "service" = "zookeeper"
      }
      "type" = "LoadBalancer"
    }
  }

  depends_on = [kubernetes_manifest.namespace_zookeper]
}

resource "kubernetes_manifest" "statefulset_zookeeper" {
  manifest = {
    "apiVersion" = "apps/v1"
    "kind"       = "StatefulSet"
    "metadata" = {
      "labels" = {
        "service" = "zookeeper"
      }
      "namespace" = "zookeeper"
      "name"      = "zookeeper-statefulset"
    }
    "spec" = {
      "replicas" = 1
      "selector" = {
        "matchLabels" = {
          "service" = "zookeeper"
        }
      }
      "template" = {
        "metadata" = {
          "labels" = {
            "service" = "zookeeper"
          }
        }
        "spec" = {
          "containers" = [
            {
              "env" = [
                {
                  "name"  = "ZOOKEEPER_CLIENT_PORT"
                  "value" = "2181"
                },
                {
                  "name"  = "ZOOKEEPER_DATA_DIR"
                  "value" = "/var/lib/zookeeper/data"
                },
                {
                  "name"  = "ZOOKEEPER_LOG_DIR"
                  "value" = "/var/lib/zookeeper/log"
                },
              ]
              "image" = "confluentinc/cp-zookeeper:7.3.0"
              "name"  = "zookeeper"
              "ports" = [
                {
                  "containerPort" = 2181
                },
              ]
              "volumeMounts" = [
                {
                  "mountPath" = "/var/lib/zookeeper/data"
                  "name"      = "zookeeper-data"
                },
                {
                  "mountPath" = "/var/lib/zookeeper/log"
                  "name"      = "zookeeper-log"
                },
              ]
            },
          ]
        }
      }
      "volumeClaimTemplates" = [
        {
          "metadata" = {
            "name" = "zookeeper-data"
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
        {
          "metadata" = {
            "name" = "zookeeper-log"
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
