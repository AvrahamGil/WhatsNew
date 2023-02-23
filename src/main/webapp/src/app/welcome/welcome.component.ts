import { Component } from '@angular/core';
import { ArticleService } from '../articles.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent {

  private types:Array<string> = ['news','business','sport','technology','travel','newsNewYork','sportNewYork'];

  constructor(public articlesService: ArticleService) {}

  ngOnInit() {
    this.init(this.types);
  }

  public getArticles(type:string) : Promise<any> {
    return new Promise((resolve,reject) => {
      this.init(type).then((articles) => {
        resolve(articles);
     }).catch((error) => {reject(error)});
    })

  }

  private init(type:any) : Promise<any> {
    return new Promise((resolve,reject) => {
      try {
          if(type  === 'newsNewYork' || type === 'sportNewYork') {
            this.articlesService.getNewYorkArticles(type).then((articles:any) => {
              localStorage.setItem(type,JSON.stringify(articles));
              resolve(JSON.parse(localStorage.getItem(type)!))
            });
          } else {
            this.articlesService.getContextArticlesTwo(type).then((articles:any) => {
              localStorage.setItem(type,JSON.stringify(articles));
              resolve(JSON.parse(localStorage.getItem(type)!))
            });
          }
      }catch(error) {
        reject('Something went wrong...')
      }
    })

  }
}
