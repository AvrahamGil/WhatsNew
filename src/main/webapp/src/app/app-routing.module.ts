import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BusinessComponent } from './business/business.component';
import { LoginComponent } from './login/login.component';
import { NewsComponent } from './news/news.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { RegisterComponent } from './register/register.component';
import { SportComponent } from './sport/sport.component';
import { TechnologyComponent } from './technology/technology.component';
import { TravelComponent } from './travel/travel.component';
import { UserComponent } from './user/user.component';
import { WelcomeComponent } from './welcome/welcome.component';

const routes: Routes = [
  {path:"" , component: WelcomeComponent},
  {path:"articles/news" , component: NewsComponent},
  {path:"articles/business" , component: BusinessComponent},
  {path:"articles/sport" , component: SportComponent},
  {path:"articles/technology" , component: TechnologyComponent},
  {path:"articles/travel" , component: TravelComponent},
  {path:"user" , component: UserComponent,children:[
    {path:'login' , component:LoginComponent},
    {path:'register' , component:RegisterComponent}
  ]},
  {path:"**" , component: NotFoundComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes , { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
