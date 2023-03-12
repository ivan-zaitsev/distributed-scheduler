import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InstanceComponent } from './instance/instance.component';
import { InstancesService } from './instance/instance.service';
import { AppService } from './app.service';

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
    AppService,
    InstancesService
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {

}
