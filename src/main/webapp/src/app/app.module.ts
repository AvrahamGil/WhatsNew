import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
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
import { Users } from './models/users';
import { LoginDetails } from './models/login-details';
import { NgxCaptchaModule } from 'ngx-captcha';
import { RecaptchaFormsModule, RecaptchaModule, RecaptchaSettings, RECAPTCHA_SETTINGS } from 'ng-recaptcha';
<<<<<<< HEAD
=======
import { env } from '../app/login/login.component';
>>>>>>> 43fc34ca728f401a87b5d465bd19f15c579b2da4
import { HeaderComponent } from './header/header.component';
import { UserComponent } from './user/user.component';
import { CookieService } from 'ngx-cookie-service';
import { ArticleService } from './services/articles.service';
import { HttpErrorInterceptor } from './services/httperrorinterceptor.service';
import { ErrorHandlerService } from './services/errorhandlerservice.service';
import { ValidateService } from './services/validate.service';
import { FooterComponent } from './footer/footer.component';

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
    UserComponent,
    FooterComponent
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
    {provide: ArticleService},{provide:Users},{provide:LoginComponent},{provide:LoginDetails},{provide:WelcomeComponent},{provide:ValidateService},{ provide: RECAPTCHA_SETTINGS,
      useValue: {
<<<<<<< HEAD

=======
        siteKey: env.siteKey,
>>>>>>> 43fc34ca728f401a87b5d465bd19f15c579b2da4
      } as RecaptchaSettings,},{ provide: HTTP_INTERCEPTORS,
        useClass: HttpErrorInterceptor,
        multi: true},
      {provide: ErrorHandler, useClass: ErrorHandlerService}
  ],
  bootstrap: [AppComponent]
})


export class AppModule { }


