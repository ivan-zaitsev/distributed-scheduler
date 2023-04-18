import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Instance } from './instance';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { AppService } from '../app.service';

@Injectable()
export class InstanceService {

  constructor(private http: HttpClient) {
  }

  findAll(): Observable<Instance[]> {
    const url: string = AppService.basePath + "/api/v1/instances";

    return this.http.get<Instance[]>(url)
      .pipe(retry(1), catchError(error => throwError(() => error)));
  }

}
