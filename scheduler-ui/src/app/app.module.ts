import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InstanceComponent } from './instance/instance.component';
import { InstancesService } from './instance/instance.service';

@NgModule({
  declarations: [
    AppComponent,
    InstanceComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [
    InstancesService
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {

  static basePath: string = "http://localhost:8000/worker-node";

}
