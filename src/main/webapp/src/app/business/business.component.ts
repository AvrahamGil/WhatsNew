import { Component } from '@angular/core';
import { ArticleService } from '../articles.service';

@Component({
  selector: 'app-business',
  templateUrl: './business.component.html',
  styleUrls: ['../app.component.scss']
})
export class BusinessComponent {

  public businessAr:any  = [];

  constructor(public articlesService: ArticleService) {}

  ngOnInit() {
    this.initData();
  }

  public initData() {
    this.articlesService.getContextArticlesTwo("business").then((articles:any) => {
      this.businessAr = articles;
    });
  }
}
