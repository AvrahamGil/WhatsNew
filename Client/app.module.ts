import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { NewsRequestService } from './news-request.service';
import { BusinessService } from './business.service';
import { SportService } from './sport.service';
import { TravelService } from './travel.service';
import { TechnologyService } from './technology.service';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [NewsRequestService, BusinessService, SportService,TechnologyService,TravelService],
  bootstrap: [AppComponent]
})
export class AppModule { }
