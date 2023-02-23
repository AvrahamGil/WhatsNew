import { Component } from '@angular/core';
import { ArticleService } from '../articles.service';
import { WelcomeComponent } from '../welcome/welcome.component';

@Component({
  selector: 'app-sport',
  templateUrl: './sport.component.html',
  styleUrls: ['../app.component.scss']
})
export class SportComponent {

  public sportAr:any  = [];
  public newYorkTimesAr:any = [];

  constructor(public welcome: WelcomeComponent) {}

  ngOnInit() {
    this.getArticles();
  }

  private getArticles() {
    this.welcome.getArticles("sport").then((articles) => {
        this.sportAr = articles;
    });

    this.welcome.getArticles("sportNewYork").then((articles) => {
        this.newYorkTimesAr = articles;
    })
  }

}
