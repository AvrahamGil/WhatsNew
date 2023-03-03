import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ArticleService } from '../articles.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent {

  private types:Array<string> = ['news','business','sport','technology','travel','newsNewYork','sportNewYork'];

  constructor(private articlesService: ArticleService,public cookieService:CookieService) {}


  public getArticles(type:string) : Promise<any> {
    if(localStorage.getItem(type) !== null) localStorage.removeItem(type);

    return new Promise((resolve,reject) => {
      this.init(type).then((articles) => {
        resolve(articles);
     }).catch((error) => {reject(error)});
    })

  }

  private init(type:any) : Promise<any> {
    return new Promise((resolve,reject) => {
      const csrf:any = this.cookieService.get('X-CSRFTOKEN');

      try {
          if(type  === 'newsNewYork' || type === 'sportNewYork') {
            this.articlesService.getNewYorkArticles(type,csrf).then((articles:any) => {
              localStorage.setItem(type,JSON.stringify(articles));
              resolve(JSON.parse(localStorage.getItem(type)!))
            });
          } else {
            this.articlesService.getContextArticles(type,csrf).then((articles:any) => {
              localStorage.setItem(type,JSON.stringify(articles));
              resolve(JSON.parse(localStorage.getItem(type)!))
            });
          }
      }catch(error) {
        reject('Please login again')
      }
    })

  }
}
