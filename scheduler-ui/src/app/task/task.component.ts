import { Component, OnInit } from '@angular/core';
import { TaskService } from './task.service';
import { PagedList, Task, TaskStatus } from './task';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html'
})
export class TaskComponent implements OnInit {

  pageSize: number = 50;
  nextPageCursor?: string;

  tasks: Task[] = [];

  constructor(private taskService: TaskService) {
  }

  ngOnInit(): void {
    this.render(this.pageSize);
  }

  onLoadMore(): void {
    this.render(this.pageSize, this.nextPageCursor);
  }

  render(pageSize: number, pageCursor?: string): void {
    const result: Observable<PagedList<Task>> = this.taskService.findAll(TaskStatus.values(), pageSize, pageCursor);

    result.subscribe({
      next: (data: PagedList<Task>) => {
        this.tasks.push(...data.content);
        this.nextPageCursor = data.nextCursor;
      },
      error: (error: any) => console.log(error)
    });
  }

}
