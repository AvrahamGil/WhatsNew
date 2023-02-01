import { Component } from '@angular/core';
import { ArticleService } from '../articles.service';

@Component({
  selector: 'app-sport',
  templateUrl: './sport.component.html',
  styleUrls: ['../app.component.scss']
})
export class SportComponent {

  public sportAr:any  = [];
  public newYorkTimesAr:any = [];

  constructor(public articlesService: ArticleService) {}

  ngOnInit() {
    this.initData();
  }

  public initData() {
    this.articlesService.getContextArticlesTwo("sport").then((articles:any) => {
      this.sportAr = articles;
    });

    this.articlesService.getNewYorkArticles("sport").then((articles:any) => {
      this.newYorkTimesAr = articles;
    });
}
}
