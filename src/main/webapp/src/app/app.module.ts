import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { ArticleService } from './articles.service';
import { NewsComponent } from './news/news.component';
import { BusinessComponent } from './business/business.component';
import { SportComponent } from './sport/sport.component';
import { TechnologyComponent } from './technology/technology.component';
import { TravelComponent } from './travel/travel.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { UserComponent } from './user/user.component';

@NgModule({
  declarations: [
    AppComponent,
    NewsComponent,
    BusinessComponent,
    SportComponent,
    TechnologyComponent,
    TravelComponent,
    NotFoundComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [ArticleService],
  bootstrap: [AppComponent]
})

export class AppModule { }
