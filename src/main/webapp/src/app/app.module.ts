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
import { LoginDetails } from './login-details';
import { NgxCaptchaModule } from 'ngx-captcha';
import { RecaptchaFormsModule, RecaptchaModule, RecaptchaSettings, RECAPTCHA_SETTINGS } from 'ng-recaptcha';
import { env } from '../app/login/login.component';
import { HeaderComponent } from './header/header.component';
import { LoginService } from './login.service';
import { UserComponent } from './user/user.component';
import { CookieService } from 'ngx-cookie-service';

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
    HeaderComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxCaptchaModule,
    RecaptchaModule,
    RecaptchaFormsModule,
  ],
  providers: [
    {provide: ArticleService},{provide:Users},{provide:LoginService},{provide:LoginDetails},{provide:WelcomeComponent},{ provide: RECAPTCHA_SETTINGS,
      useValue: {
        siteKey: env.siteKey,
      } as RecaptchaSettings,}
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
