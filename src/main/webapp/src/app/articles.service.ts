import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Articles } from './articles';

export enum ArticleSites {
    //News sites
    Theguardian = "Theguardian",
    Time = "Time",
    WallStreetJournal ="WallStreetJournal",
    CNN = "CNN",
    Theblaze ="Theblaze",
    Ynetnews = "Ynetnews",
    BBCnews = "BBCnews",
    JerusalemNews = "JerusalemNews",
    NBC = "NBC",
    Covid19 = "Covid19",
    ABCnews = "ABCnews",


    //Business sites
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
    Zacks = "Zacks",

    //Sports sites
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

	NewYorkTimes = "NewYorkTimes",

    //Technology sites
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
	TechRadar = "TechRadar",


    //Travel sites
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
export class ArticleService {

  constructor(private http:HttpClient) { }

  private getRequest:any;

  public getAllArticles() : Observable<Articles> {
    this.getRequest = "/whatsnew/articles/getNewsArticles";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }

  public getNewYorkArticles() : Observable<Articles> {
    this.getRequest = "/whatsnew/articles/getNewYorkTimesNewsArticles";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.get<Articles>(this.getRequest,{headers: header});
  }
}
