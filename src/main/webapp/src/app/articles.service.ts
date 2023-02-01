import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Articles } from './articles';
import sites from '../assets/json/sites.json';

@Injectable()
export class ArticleService {

  public articles =  Array(14).fill([[]]);
  public data:any  = [];
  public articlesData:any  = [];

  private types = ["news","business","sport","technology","travel"];

  constructor(private http:HttpClient) { }

  private getRequest:any;

  public getContextArticlesTwo(type:string) : Promise<any[]> {
    var array:any = this.articles.map(() => Object.values([]));
    var arrayMap:any = this.articles.map(() => Object.values([]));

    return new Promise((resolve, reject) => {
      try {
        array = this.getAllArticlesData().subscribe((data:Articles) => {
          let index = this.types.indexOf(type);
          this.data = Array.from(Object.keys(data),(k) =>data[k as keyof Articles]);

          this.articlesData[index] = this.data[index];

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
        reject(err);
      }
    })
  }

  public getNewYorkArticles(type:string) : Promise<any[]> {
    var array:any = Array(2).fill([[]]);
    var arrayMap:any = Array(2).fill([[]]);

    return new Promise((resolve,reject) => {
      try{
        array = this.getNewYorkData().subscribe((data:Articles) => {
          let types = ["news","sport"];
          let index = types.indexOf(type);

          this.data = Array.from(Object.keys(data),(k) =>data[k as keyof Articles]);

          this.articlesData = this.data;

          if(type === "news") arrayMap[index] = this.articlesData.slice(0,5);
          if(type === "sport") arrayMap[index] = this.articlesData.slice(6);

          if(index == 0 && arrayMap[index] !== undefined) array = arrayMap.map((x:any) => x);
          if(index == 1 && arrayMap[index] !== undefined) array = arrayMap.map((x:any) => x);

          resolve(array[index]);

         });
      }catch(err) {
        reject(err);
      }
    })

   }

  private getAllArticlesData() : Observable<Articles> {
    this.getRequest = "http://localhost:8080/whatsnew/articles/getNewsArticles";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }

  private getNewYorkData() : Observable<Articles> {
    this.getRequest = "http://localhost:8080/whatsnew/articles/getNewYorkTimesNewsArticles";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }


}
