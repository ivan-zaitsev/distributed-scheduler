import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InstanceComponent } from './instance/instance.component';

const routes: Routes = [
  { path: 'instances', component: InstanceComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
