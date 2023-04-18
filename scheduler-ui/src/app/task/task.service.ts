import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PagedList, Task, TaskStatus } from './task';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { AppService } from '../app.service';

@Injectable()
export class TaskService {

  constructor(private http: HttpClient) {
  }

  findAll(statuses: TaskStatus[], pageSize: number, pageCursor?: string): Observable<PagedList<Task>> {
    const url: string = AppService.basePath + "/api/v1/tasks";

    let params = new HttpParams();
    params = params.append("statuses", statuses.join(','));
    params = params.append("pageSize", pageSize);
    if (pageCursor != null) {
      params = params.append("pageCursor", pageCursor);
    }

    return this.http.get<PagedList<Task>>(url, { params })
      .pipe(retry(1), catchError(error => throwError(() => error)));
  }

}
