import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Articles } from '../models/articles';
import sites from '../../assets/json/sites.json';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class ArticleService {

  public articles =  Array(14).fill([[]]);
  public data:any  = [];
  public articlesData:any  = [];

  private types = ["news","business","sport","technology","travel"];

  constructor(private http:HttpClient,public cookieService:CookieService) { }

  private getRequest:any;
  private postRequest:any;

  public getContextArticles(type:string,csrf:string) : Promise<any[]> {
    var array:any = this.articles.map(() => Object.values([]));
    var arrayMap:any = this.articles.map(() => Object.values([]));

    return new Promise((resolve, reject) => {
      try {
        array = this.getAllArticlesData(csrf).subscribe((data:Articles) => {
          let index = this.types.indexOf(type);
          if(index === -1) index = this.types.indexOf("news");
          this.data = Array.from(Object.keys(data),(k) =>data[k as keyof Articles]);

          this.articlesData[index] = this.data[index];

          if(this.articlesData[index] === undefined) {
            document.getElementById('expiredAlert')?.classList.remove('hide');
          }

          this.articlesData[index].map((x:any) => {
            var num = sites[index][type][x.newsType];
            arrayMap[num].push(x);

            if(index == 0 && arrayMap[0] !== undefined) array = arrayMap.map((x:any) => x);
            if(index == 1 && arrayMap[1] !== undefined) array = arrayMap.map((x:any) => x);
            if(index == 2 && arrayMap[2] !== undefined) array = arrayMap.map((x:any) => x);
            if(index == 3 && arrayMap[3] !== undefined) array = arrayMap.map((x:any) => x);
            if(index == 4 && arrayMap[4] !== undefined) array = arrayMap.map((x:any) => x);
          })

          resolve(array);
        })
      } catch(err) {
        this.cookieService.delete("X-CSRF-TOKEN");
        this.cookieService.delete("X-TOKEN");
        reject(err);
      }
    })
  }

   public likeArticle(articleId:string,csrf:string) : Promise<any[]> {
    return new Promise((resolve,reject) => {
      try {
        this.liked(articleId,csrf).subscribe((response:any) => {
          resolve(response);
        })
      }catch(err) {
        reject(err)
      }
    })
   }

   private liked(articleId:string,csrf:string) : Observable<string> {
    this.postRequest = "/articles/liked";

    const header = new HttpHeaders().set('X-CSRF-TOKEN' , csrf)

    return this.http.post<string>(this.postRequest,articleId,{headers:header}).pipe((err) => err);
  }

  private getAllArticlesData(csrf:string) : Observable<Articles> {
    this.getRequest = "/articles/getNewsArticles";

    const header = new HttpHeaders().set('X-CSRF-TOKEN' , csrf).set('Content-Type', 'application/json');

    return this.http.get<Articles>(this.getRequest,{headers: header}).pipe((err) => err);
  }

}
