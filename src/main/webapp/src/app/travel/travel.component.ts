import { Component } from '@angular/core';
import { ArticleService } from '../articles.service';
import { WelcomeComponent } from '../welcome/welcome.component';

@Component({
  selector: 'app-travel',
  templateUrl: './travel.component.html',
  styleUrls: ['../app.component.scss']
})
export class TravelComponent {

  public travelAr:any  = [];

  constructor(public welcome: WelcomeComponent) {}

  ngOnInit() {
    this.getArticles();
  }

  private getArticles() {
    this.welcome.getArticles("travel").then((articles) => {
        this.travelAr = articles;
    });
  }
}