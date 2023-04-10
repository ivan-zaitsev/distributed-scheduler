resource "kubernetes_manifest" "service_kafka" {
  manifest = yamldecode(<<-EOF
    apiVersion: v1
    kind: Service
    metadata:
      labels:
        service: kafka
      name: kafka-service
      namespace: default
    spec:
      ports:
        - name: broker
          port: 9092
          targetPort: 9092
        - name: controller
          port: 9093
          targetPort: 9093
      selector:
        service: kafka
    EOF
  )
}

resource "kubernetes_manifest" "statefulset_kafka" {
  manifest = yamldecode(<<-EOF
    apiVersion: apps/v1
    kind: StatefulSet
    metadata:
      labels:
        service: kafka
      name: kafka-statefulset
      namespace: default
    spec:
      replicas: 1
      selector:
        matchLabels:
          service: kafka
      template:
        metadata:
          labels:
            service: kafka
        spec:
          containers:
            - image: bitnami/kafka:3.2.3
              name: kafka
              ports:
                - containerPort: 9092
                - containerPort: 9093
              env:
                - name: KAFKA_BROKER_ID
                  value: "1"
                - name: KAFKA_ENABLE_KRAFT
                  value: "yes"
                - name: KAFKA_CFG_PROCESS_ROLES
                  value: "broker,controller"
                - name: KAFKA_CFG_CONTROLLER_LISTENER_NAMES
                  value: "CONTROLLER"
                - name: ALLOW_PLAINTEXT_LISTENER
                  value: "yes"
                - name: KAFKA_CFG_LISTENERS
                  value: "PLAINTEXT://:9092,CONTROLLER://:9093"
                - name: KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP
                  value: "CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT"
                - name: KAFKA_CFG_ADVERTISED_LISTENERS
                  value: "PLAINTEXT://127.0.0.1:9092"
                - name: KAFKA_CFG_CONTROLLER_QUORUM_VOTERS
                  value: "1@127.0.0.1:9093"
              volumeMounts:
                - mountPath: /var/lib/kafka/data
                  name: kafka-data
      volumeClaimTemplates:
        - metadata:
            name: kafka-data
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
