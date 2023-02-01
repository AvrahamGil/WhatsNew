import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BusinessComponent } from './business/business.component';
import { NewsComponent } from './news/news.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { SportComponent } from './sport/sport.component';
import { TechnologyComponent } from './technology/technology.component';
import { TravelComponent } from './travel/travel.component';

const routes: Routes = [
  {path:"news" , component: NewsComponent},
  {path:"business" , component: BusinessComponent},
  {path:"sport" , component: SportComponent},
  {path:"technology" , component: TechnologyComponent},
  {path:"travel" , component: TravelComponent},
  {path:"404" , component: NotFoundComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes , { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
