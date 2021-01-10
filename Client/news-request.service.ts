import { HttpClient , HttpHeaders, HttpParams  } from '@angular/common/http';
import { Articles } from './/Articles';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';


export enum NewsSites {
  Theguardian = "Theguardian",
  Time = "Time",

  WallStreetJournal ="WallStreetJournal",
  CNN = "CNN",

  Theblaze ="Theblaze",
  Ynetnews = "Ynetnews",

  //RequestNews
  BBCnews = "BBCnews",
  JerusalemNews = "JerusalemNews",
  NBC = "NBC",
  Covid19 = "Covid19",

  ABCnews = "ABCnews",

  //other
  NewYorkTimes = "NewYorkTimes"
  
}

@Injectable()
export class NewsRequestService {

  constructor(private http:HttpClient, private router: Router) { }

  private getRequest;

  public getAllArticles() : Observable<Articles> {
    this.getRequest = "/news/getNewsArticles";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }

  public getNewYorkArticles() : Observable<Articles> {
    this.getRequest = "/news/getNewYorkTimesNewsArticles";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }

  public getNRandomArticle() : Observable<Articles> {
    this.getRequest = "/news/getNewsRandomArticle";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }
}

