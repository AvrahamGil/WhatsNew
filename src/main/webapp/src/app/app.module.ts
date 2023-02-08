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
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Users } from './users';
import { UserComponent } from './user/user.component';
import { LoginDetails } from './login-details';

@NgModule({
  declarations: [
    AppComponent,
    NewsComponent,
    BusinessComponent,
    SportComponent,
    TechnologyComponent,
    TravelComponent,
    NotFoundComponent,
    RegisterComponent,
    LoginComponent,
    WelcomeComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [ArticleService,Users,LoginDetails],
  bootstrap: [AppComponent]
})

export class AppModule { }
