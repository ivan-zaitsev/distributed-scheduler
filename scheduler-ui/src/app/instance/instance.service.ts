import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Instance } from './instance';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { AppService } from '../app.service';

@Injectable()
export class InstancesService {

  constructor(private http: HttpClient) {
  }

  findAll(): Observable<Instance[]> {
    return this.http.get<Instance[]>(AppService.basePath + "/api/v1/instances")
      .pipe(retry(1), catchError(error => throwError(() => error)));
  }

}
