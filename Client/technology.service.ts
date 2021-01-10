import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Articles } from './articles';

export enum TechnologySites {

  Sciencedaily = "Sciencedaily",
	Gizmodo = "Gizmodo",
	Engadget = "Engadget",
	Theverge = "Theverge",
	Axios = "Axios",
	Wired = "Wired",
	Arstechnica = "Arstechnica",
	Thenextweb = "Thenextweb",
	
	Bitcoin = "Bitcoin",
	Techcrunch = "Techcrunch",
	Apple = "Apple",
	TechRadar = "TechRadar"

}
@Injectable()
export class TechnologyService {

  constructor(private http:HttpClient) { }

  private getRequest;

  public getAllArticles() : Observable<Articles> {
    this.getRequest = "/technology/getNewsArticles";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }


  public getOtherArticles() : Observable<Articles> {
    this.getRequest = "/technology/getOtherNewsArticles";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }

  public getTeRandomArticle() : Observable<Articles> {
    this.getRequest = "/technology/getTechnologyRandomArticle";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }
}
