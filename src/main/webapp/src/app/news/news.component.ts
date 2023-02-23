import { Component, OnInit} from '@angular/core';
import { WelcomeComponent } from '../welcome/welcome.component';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['../app.component.scss']
})
export class NewsComponent implements OnInit{

  public newsAr:any = [];

  public newYorkTimesAr:any = [];

  constructor(public welcome: WelcomeComponent) {}

  ngOnInit() {
    this.getArticles();
  }

  private getArticles() {
  this.welcome.getArticles("news").then((articles) => {
      this.newsAr = articles;
  });

  this.welcome.getArticles("newsNewYork").then((articles) => {
      this.newYorkTimesAr = articles;
  })
  }
}
