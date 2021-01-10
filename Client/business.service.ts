import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Articles } from './Articles';


export enum BusinessSites {
  CNBC = "CNBC",
  FT = "FT",

  Forbes ="Forbes",
  Fool = "Fool",

  Bloomberg ="Bloomberg",
  CleanTechnica = "CleanTechnica",
  IBtimes = "IBtimes",
  MarketWatch = "MarketWatch",
  Entrepreneur = "Entrepreneur",
  YahooFinance = "YahooFinance",
  BusinessInsider = "BusinessInsider",
  Zacks = "Zacks"
  
}


export class BusinessService {

  constructor(private http:HttpClient, private router: Router) { }

  private getRequest;
  private params: HttpParams;


  public getAllArticles() : Observable<Articles> {
    this.getRequest = "/business/getNewsArticles";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }


  public getBRandomArticle() : Observable<Articles> {
    this.getRequest = "/business/getBusinessRandomArticle";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }
}
