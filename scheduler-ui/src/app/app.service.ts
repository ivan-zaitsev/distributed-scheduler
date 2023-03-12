import { Injectable } from '@angular/core';
import { InstanceMode } from './instance/instance';

@Injectable()
export class AppService {

  static basePathPartitioning: string = "http://localhost:8000/worker-node-partitioning";
  static basePathLeadership: string = "http://localhost:8000/worker-node-leadership";

  static getBasePath(mode: InstanceMode): string {
    if (InstanceMode.PARTITIONING == mode) {
      return AppService.basePathPartitioning;
    }
    if (InstanceMode.LEADERSHIP == mode) {
      return AppService.basePathLeadership;
    }

    throw new Error('Failed to find base path for = ' + mode);
  }

}
