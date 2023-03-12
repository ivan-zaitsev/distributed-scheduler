import { Component, OnInit } from '@angular/core';
import { Instance, InstanceMode } from './instance';
import { InstancesService as InstanceService } from './instance.service';

@Component({
  selector: 'app-instances',
  templateUrl: './instance.component.html'
})
export class InstanceComponent implements OnInit {

  instancesPartitioning: Instance[] = [];
  instancesLeadership: Instance[] = [];

  constructor(private instanceService: InstanceService) {
  }

  ngOnInit(): void {
    this.instanceService.findAll(InstanceMode.PARTITIONING).subscribe({
      next: (data: Instance[]) => this.instancesPartitioning = data,
      error: (error: any) => console.log(error)
    });

    this.instanceService.findAll(InstanceMode.LEADERSHIP).subscribe({
      next: (data: Instance[]) => this.instancesLeadership = data,
      error: (error: any) => console.log(error)
    });
  }

}
