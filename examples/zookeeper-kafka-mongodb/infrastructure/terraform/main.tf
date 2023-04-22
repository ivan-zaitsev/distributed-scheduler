terraform {
  required_version = ">= 1.4.4"

  required_providers {
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = ">= 2.19.0"
    }
  }
}

provider "kubernetes" {
  config_path = "~/.kube/config"
}

provider "helm" {
  kubernetes {
    config_path = "~/.kube/config"
  }
}

resource "kubernetes_manifest" "namespace_scheduler" {
  manifest = yamldecode(<<-EOF
    apiVersion: v1
    kind: Namespace
    metadata:
      name: scheduler
    EOF
  )
}
