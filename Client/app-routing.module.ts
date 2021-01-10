import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';


const routes: Routes = [
  {path:"WhatsNew" , component: AppComponent
}];

@NgModule({
  imports: [RouterModule.forRoot(routes ,{initialNavigation: false})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
