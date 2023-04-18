import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InstanceComponent } from './instance/instance.component';
import { InstanceService } from './instance/instance.service';
import { TaskComponent } from './task/task.component';
import { TaskService } from './task/task.service';
import { AppService } from './app.service';

@NgModule({
  declarations: [
    AppComponent,
    InstanceComponent,
    TaskComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [
    AppService,
    InstanceService,
    TaskService
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {

}
