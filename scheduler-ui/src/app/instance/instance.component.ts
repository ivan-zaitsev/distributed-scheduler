import { Component, OnInit } from '@angular/core';
import { Instance } from './instance';
import { InstancesService as InstanceService } from './instance.service';

@Component({
  selector: 'app-instances',
  templateUrl: './instance.component.html'
})
export class InstanceComponent implements OnInit {

  instances: Instance[] = [];

  constructor(private instanceService: InstanceService) {
  }

  ngOnInit(): void {
    this.instanceService.findAll().subscribe({
      next: (data: Instance[]) => this.instances = data,
      error: (error: any) => console.log(error)
    });
  }

}
