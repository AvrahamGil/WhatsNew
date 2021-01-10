import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Articles } from './articles';

export enum TravelSites {
	Traveldailynews = "Traveldailynews",
	Travelandtourworld= "Travelandtourworld",
	Skift= "Skift",
	Phocuswire= "Phocuswire",
	Travelpulse= "Travelpulse",
	Ttgmedia= "Ttgmedia",
	Ttnworldwide= "Ttnworldwide",
	Eturbonews= "Eturbonews",
	Visitseattle= "Visitseattle",
	
	TravelWeekly= "TravelWeekly",
	
	Businesstravelnews= "Businesstravelnews",
	LonelyPlanet= "LonelyPlanet"
}

@Injectable()
export class TravelService {
  constructor(private http:HttpClient) { }

  private getRequest;

  public getAllArticles() : Observable<Articles> {
    this.getRequest = "/travel/getNewsArticles";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }

  public getTrRandomArticle() : Observable<Articles> {
    this.getRequest = "/travel/getTravelRandomArticle";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }
}
