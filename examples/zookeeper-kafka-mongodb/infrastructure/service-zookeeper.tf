resource "kubernetes_manifest" "service_zookeeper" {
  manifest = yamldecode(<<-EOF
    apiVersion: v1
    kind: Service
    metadata:
      labels:
        service: zookeeper
      name: zookeeper-service
      namespace: default
    spec:
      ports:
        - name: client
          port: 2181
          targetPort: 2181
      selector:
        service: zookeeper
    EOF
  )
}

resource "kubernetes_manifest" "statefulset_zookeeper" {
  manifest = yamldecode(<<-EOF
    apiVersion: apps/v1
    kind: StatefulSet
    metadata:
      labels:
        service: zookeeper
      name: zookeeper-statefulset
      namespace: default
    spec:
      replicas: 1
      selector:
        matchLabels:
          service: zookeeper
      template:
        metadata:
          labels:
            service: zookeeper
        spec:
          containers:
            - image: confluentinc/cp-zookeeper:7.3.0
              name: zookeeper
              ports:
                - containerPort: 2181
              env:
                - name: ZOOKEEPER_CLIENT_PORT
                  value: "2181"
                - name: ZOOKEEPER_DATA_DIR
                  value: "/var/lib/zookeeper/data"
              volumeMounts:
                - mountPath: /var/lib/zookeeper/data
                  name: zookeeper-data
      volumeClaimTemplates:
        - metadata:
            name: zookeeper-data
          spec:
            accessModes:
              - ReadWriteOnce
            storageClassName: nfs-csi
            resources:
              requests:
                storage: 10Gi
    EOF
  )
}
