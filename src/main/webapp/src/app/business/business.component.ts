import { Component } from '@angular/core';
import { ArticleService } from '../articles.service';
import { WelcomeComponent } from '../welcome/welcome.component';

@Component({
  selector: 'app-business',
  templateUrl: './business.component.html',
  styleUrls: ['../app.component.scss']
})
export class BusinessComponent {

  public businessAr:any  = [];

  constructor(public welcome: WelcomeComponent) {}

  ngOnInit() {
    this.getArticles();
  }

  private getArticles() {
    this.welcome.getArticles("business").then((articles) => {
        this.businessAr = articles;
    });
  }
}
