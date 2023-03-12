export interface Instance {
  id: string;
  uri: string;
  registeredAt: string;
  status: InstanceStatus;
  mode: InstanceMode;
  partitioning: InstancePartitioning;
  leadership: InstanceLeadership;
}

export enum InstanceStatus {
  UP,
  OUT_OF_SERVICE
}

export enum InstanceMode {
  PARTITIONING,
  LEADERSHIP
}

interface InstancePartitioning {
  partitions: string;
}

interface InstanceLeadership {
  leader: boolean;
}
