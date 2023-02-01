import { Component } from '@angular/core';
import { ArticleService } from '../articles.service';

@Component({
  selector: 'app-technology',
  templateUrl: './technology.component.html',
  styleUrls: ['../app.component.scss']
})
export class TechnologyComponent {

  public technologyAr:any  = [];

  public articles =  Array(14).fill([[]]);
  public data:any  = [];

  constructor(public articlesService: ArticleService) {}

  ngOnInit() {
    this.initData();
  }

  public initData() {
    this.articlesService.getContextArticlesTwo("technology").then((articles:any) => {
      this.technologyAr = articles;
    });
  }
}
