import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Articles } from './Articles';

export enum SportSites {
  MLB = "MLB",
	Syracuse ="Syracuse",
	DeadSpin ="DeadSpin",
	YardBarker ="YardBarker",
	Cbssports = "Cbssports",
	SportsIllustrated = "SportsIllustrated",
	ESPN = "ESPN",
	Yahoo = "Yahoo",
	Fansided = "Fansided",
	Sbnation ="Sbnation",
	Sportingnews= "Sportingnews",

	NewYorkTimes = "NewYorkTimes"
}
@Injectable()
export class SportService {

  constructor(private http:HttpClient) { }

  private getRequest;

  public getAllArticles() : Observable<Articles> {
    this.getRequest = "/sport/getNewsArticles";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }


  public getNewYorkArticles() : Observable<Articles> {
    this.getRequest = "/sport/getNewYorkTimesNewsArticles";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }

  public getSRandomArticle() : Observable<Articles> {
    this.getRequest = "/sport/getSportRandomArticle";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }
}
