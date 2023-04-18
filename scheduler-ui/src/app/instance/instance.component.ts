import { Component, OnInit } from '@angular/core';
import { Instance } from './instance';
import { InstanceService } from './instance.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-instance',
  templateUrl: './instance.component.html'
})
export class InstanceComponent implements OnInit {

  instances: Instance[] = [];

  constructor(private instanceService: InstanceService) {
  }

  ngOnInit(): void {
    const result: Observable<Instance[]> = this.instanceService.findAll();

    result.subscribe({
      next: (data: Instance[]) => this.instances = data,
      error: (error: any) => console.log(error)
    });
  }

}
