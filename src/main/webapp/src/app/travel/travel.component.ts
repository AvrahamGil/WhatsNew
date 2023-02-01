import { Component } from '@angular/core';
import { ArticleService } from '../articles.service';

@Component({
  selector: 'app-travel',
  templateUrl: './travel.component.html',
  styleUrls: ['../app.component.scss']
})
export class TravelComponent {

  public travelAr:any  = [];

  constructor(public articlesService: ArticleService) {}

  ngOnInit() {
    this.initData();
  }

  public initData() {
    this.articlesService.getContextArticlesTwo("travel").then((articles:any) => {
      this.travelAr = articles;
    });
  }
}
