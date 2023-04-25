resource "null_resource" "elastic_operator_manifest" {
  provisioner "local-exec" {
    command = <<-EOF
      kubectl apply -f https://download.elastic.co/downloads/eck/2.7.0/crds.yaml
      kubectl apply -f https://download.elastic.co/downloads/eck/2.7.0/operator.yaml
      EOF
  }

  provisioner "local-exec" {
    when    = destroy
    command = <<-EOF
      kubectl delete -f https://download.elastic.co/downloads/eck/2.7.0/operator.yaml
      kubectl delete -f https://download.elastic.co/downloads/eck/2.7.0/crds.yaml
      EOF
  }

  depends_on = [kubernetes_manifest.namespace_scheduler]
}

resource "kubernetes_manifest" "elasticsearch_deployment" {
  manifest = yamldecode(<<-EOF
    apiVersion: elasticsearch.k8s.elastic.co/v1
    kind: Elasticsearch
    metadata:
      name: elasticsearch
      namespace: scheduler
    spec:
      version: 8.7.0
      nodeSets:
        - name: default
          count: 3
          config:
            node.store.allow_mmap: false
          volumeClaimTemplates:
            - metadata:
                name: elasticsearch-data
              spec:
                storageClassName: nfs-csi
                accessModes:
                  - ReadWriteOnce
                resources:
                  requests:
                    storage: 50Gi
    EOF
  )

  depends_on = [null_resource.elastic_operator_manifest]
}

resource "kubernetes_manifest" "kibana_deployment" {
  manifest = yamldecode(<<-EOF
    apiVersion: kibana.k8s.elastic.co/v1
    kind: Kibana
    metadata:
      name: kibana
      namespace: scheduler
    spec:
      version: 8.7.0
      count: 1
      elasticsearchRef:
        name: elasticsearch
      http:
        service:
          spec:
            type: LoadBalancer
    EOF
  )

  depends_on = [null_resource.elastic_operator_manifest]
}

resource "kubernetes_manifest" "apm_server_deployment" {
  manifest = yamldecode(<<-EOF
    apiVersion: apm.k8s.elastic.co/v1
    kind: ApmServer
    metadata:
      name: apm-server
      namespace: scheduler
    spec:
      version: 8.7.0
      http:
        tls:
          selfSignedCertificate:
            disabled: true
      count: 3
      elasticsearchRef:
        name: elasticsearch
      kibanaRef:
        name: kibana
    EOF
  )

  depends_on = [null_resource.elastic_operator_manifest]
}

resource "kubernetes_manifest" "apm_server_service" {
  manifest = yamldecode(<<-EOF
    apiVersion: v1
    kind: Service
    metadata:
      name: apm-server-apm-http-headless
      namespace: scheduler
    spec:
      type: ClusterIP
      clusterIP: None
      selector:
        apm.k8s.elastic.co/name: apm-server
      ports:
        - name: http
          port: 8200
          targetPort: 8200
    EOF
  )

  depends_on = [kubernetes_manifest.namespace_scheduler]
}
