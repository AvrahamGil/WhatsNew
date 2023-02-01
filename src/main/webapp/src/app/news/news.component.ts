import { Component, OnInit} from '@angular/core';
import { ArticleService } from '../articles.service';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['../app.component.scss']
})
export class NewsComponent implements OnInit{

  public newsAr:any  = [];
  public newYorkTimesAr:any = [];

  constructor(public articlesService: ArticleService) {}

  ngOnInit() {
    this.initData();
  }

  public initData() {
    this.articlesService.getContextArticlesTwo("news").then((articles:any) => {
      this.newsAr = articles;
    });


    this.articlesService.getNewYorkArticles("news").then((articles:any) => {
      this.newYorkTimesAr = articles;
    });

  }

}
