export interface Instance {
  id: string;
  uri: string;
  registeredAt: string;
  updatedAt: string;
  status: InstanceStatus;
  partitions: number[];
}

export enum InstanceStatus {
  UP,
  OUT_OF_SERVICE
}
