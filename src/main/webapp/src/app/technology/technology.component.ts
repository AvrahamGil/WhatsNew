import { Component } from '@angular/core';
import { ArticleService } from '../articles.service';
import { WelcomeComponent } from '../welcome/welcome.component';

@Component({
  selector: 'app-technology',
  templateUrl: './technology.component.html',
  styleUrls: ['../app.component.scss']
})
export class TechnologyComponent {

  public technologyAr:any  = [];

  public articles =  Array(14).fill([[]]);
  public data:any  = [];

  constructor(public welcome: WelcomeComponent) {}

  ngOnInit() {
    this.getArticles();
  }

  private getArticles() {
    this.welcome.getArticles("technology").then((articles) => {
        this.technologyAr = articles;
    });
  }

}