export interface Instance {
  id: string;
  uri: string;
  status: InstanceStatus;
  registeredAt: string;
  leader: boolean;
}

enum InstanceStatus {
  UP,
  OUT_OF_SERVICE
}
