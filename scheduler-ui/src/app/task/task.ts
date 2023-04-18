export interface PagedList<T> {

  content: T[];
  nextCursor?: string;

}

export interface Task {

  id: string;
  status: TaskStatus;
  executeAt: string;
  name: string;

}

export enum TaskStatus {

  SCHEDULED,
  SUBMITTED,
  PROCESSING,
  SUCCEEDED,
  FAILED

}

export namespace TaskStatus {

  export function values(): TaskStatus[] {
    return Object.keys(TaskStatus)
      .filter(status => !Number.isNaN(Number(status)))
      .map(status => TaskStatus[status as unknown as TaskStatus])
      .map(status => status as unknown as TaskStatus);
  }

}
