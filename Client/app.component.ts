import { Component } from '@angular/core';
import $ from 'jquery';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Articles } from './articles';
import { Observable } from 'rxjs';
import { NewsRequestService, NewsSites } from './news-request.service';
import { BusinessService, BusinessSites } from './business.service';
import { SportService, SportSites } from './sport.service';
import { TravelService, TravelSites } from './travel.service';
import { TechnologyService, TechnologySites } from './technology.service';
import { KeyValue } from '@angular/common';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent {


  constructor(public http: HttpClient, public newsRequest:NewsRequestService , public businessRequest:BusinessService, public sportRequest:SportService, public technologyService: TechnologyService,public travelSerice: TravelService){ }

  public counter = 0;

  //News
  public wallStreetJournalArticles = [];
  public abcNewsArticles= [];
  public bbcArticles = [];
  public cnnArticles = [];
  public covid19Articles = [];
  public ynetarticles = [];
  public newYorkPoliticsArticles = [];
  public theBlazeArticles = [];
  public theguardianArticles = [];
  public timeNews = [];
  public jerusalemPostArticles = [];
  public nbcArticles = [];


  //Business
  public cnbcBusinessArticles = [];
  public ftBusinessArticles = [];
  public forBesBusinessArticles = [];
  public foolArticles = [];
  public bloombergBusinessArticles = [];
  public cleanTechnicaBusinessArticles = [];
  public ibtimesBusinessArticles = [];
  public marketWatchArticles = [];
  public yahooFinanceArticles = [];
  public entrepreneurArticles = [];
  public businessInsiderArticles = [];
  public zackArticles = [];


  //Sport
  public sportsIllustratedArticles = [];
  public cbsArticles = [];
  public deadSpinArticles = []
  public espnArticles = [];
  public fanSidedArticles = [];
  public mlbArticles = [];
  public sbnationArticles = [];
  public sportingNewsArticels = [];
  public syracuseArticles = [];
  public yahooSportArticles = [];
  public yardBarkerArticles = [];
  public newYorkTimesSportArticles = [];


  //Technology
  public sciencedailyArticles = [];
  public wiredArticles = [];
  public theVergeArticles = [];
  public thenextwebArticles = [];
  public gizmodoArticles = [];
  public engadgetArticles = [];
  public axiosArticles = [];
  public arstechnicaArticles = [];
  public bitcionArticles = [];
  public appleArticles  = [];
  public techCrunchArticles = [];
  public techRadarArticles = [];

  
  //Travel
  public businessTravel = [];
  public eturboNews = [];
  public phocusWire = [];
  public skift = [];
  public travelWeekly = [];
  public travelAndTourWorld = [];
  public travelDailyNews = [];
  public travelPulse = [];
  public ttgMediaNews = [];
  public ttnWorldWideNews = [];
  public Visitseattle = [];
  public lonelyPlanet = [];
   
  //DataArticles
  public articlesData = [];
  public articles = [];

  //RandomArticle
  public newsRandomArticle = [];
  public businessRandomArticle = [];
  public sportRandomArticle = [];
  public technologyRandomArticle = [];
  public travelRandomArticle = [];

  //Articles
  //this.wallStreetJournalArticles
  public newsArticles = [this.wallStreetJournalArticles , this.abcNewsArticles, this.bbcArticles,this.cnnArticles,this.covid19Articles,this.ynetarticles,this.theBlazeArticles,this.theguardianArticles,this.timeNews,this.jerusalemPostArticles,this.nbcArticles];
  public businessArticles = [this.zackArticles, this.cnbcBusinessArticles, this.ftBusinessArticles,this.forBesBusinessArticles,this.foolArticles,this.bloombergBusinessArticles,this.cleanTechnicaBusinessArticles,this.ibtimesBusinessArticles,this.marketWatchArticles,this.yahooFinanceArticles,this.entrepreneurArticles,this.businessInsiderArticles]; 
  public sportArticles = [this.sportsIllustratedArticles, this.cbsArticles, this.deadSpinArticles,this.espnArticles,this.fanSidedArticles,this.mlbArticles,this.sbnationArticles,this.sportingNewsArticels,this.syracuseArticles,this.yahooSportArticles,this.yardBarkerArticles];
  public technologyArticles = [this.sciencedailyArticles, this.wiredArticles, this.theVergeArticles,this.thenextwebArticles,this.gizmodoArticles,this.engadgetArticles,this.axiosArticles,this.arstechnicaArticles];
  public technologyOtherArticles = [this.bitcionArticles, this.appleArticles, this.techCrunchArticles,this.techRadarArticles];
  public travelArticles = [ this.businessTravel,  this.eturboNews,  this.phocusWire, this.skift, this.travelWeekly, this.travelAndTourWorld, this.travelDailyNews,this.travelPulse,this.ttgMediaNews,this.ttnWorldWideNews,this.Visitseattle,this.lonelyPlanet];
  
  //Sites
  public newsSites = [NewsSites.WallStreetJournal, NewsSites.ABCnews,NewsSites.BBCnews, NewsSites.CNN,NewsSites.Covid19,NewsSites.Ynetnews,NewsSites.Theblaze,NewsSites.Theguardian,NewsSites.Time,NewsSites.JerusalemNews,NewsSites.NBC];
  public businessSites = [BusinessSites.Zacks,BusinessSites.CNBC,BusinessSites.FT, BusinessSites.Forbes,BusinessSites.Fool,BusinessSites.Bloomberg,BusinessSites.CleanTechnica,BusinessSites.IBtimes, BusinessSites.MarketWatch,BusinessSites.YahooFinance,BusinessSites.Entrepreneur,BusinessSites.BusinessInsider];
  public sportSites = [SportSites.SportsIllustrated,SportSites.Cbssports, SportSites.DeadSpin,SportSites.ESPN,SportSites.Fansided,SportSites.MLB,SportSites.Sbnation, SportSites.Sportingnews,SportSites.Syracuse,SportSites.Yahoo,SportSites.YardBarker];
  public technologySites = [TechnologySites.Sciencedaily,TechnologySites.Wired, TechnologySites.Theverge,TechnologySites.Thenextweb,TechnologySites.Gizmodo,TechnologySites.Engadget,TechnologySites.Axios, TechnologySites.Arstechnica];
  public technologyOtherSites = [TechnologySites.Bitcoin,TechnologySites.Apple,TechnologySites.Techcrunch,TechnologySites.TechRadar];
  public travelSites = [ TravelSites.Businesstravelnews,TravelSites.Eturbonews, TravelSites.Phocuswire,TravelSites.Skift,TravelSites.TravelWeekly, TravelSites.Travelandtourworld,TravelSites.Traveldailynews, TravelSites.Travelpulse,TravelSites.Ttgmedia,TravelSites.Ttnworldwide,TravelSites.Visitseattle,TravelSites.LonelyPlanet];
 
  ngOnInit() {
  
  this.generateArticles();

  $("#scrollTop").click(function() {
    $("html").scrollTop(0);
    });

    if ($(window).width() > 992) {
      $(window).scroll(function(){  
         if ($(this).scrollTop() > 40) {
            $('#header').addClass("fixed-top");
            $('body').css('padding-top', $('#header').outerHeight() + 'px');
          }else{
            $('#header').removeClass("fixed-top");
            $('body').css('padding-top', '0');
          }   
      });
    } 
  }


  private getContextNewsArticles() {
    this.newsRequest.getAllArticles().subscribe(data => {
      this.articlesData = Array.from(Object.keys(data),k =>data[k]);
      this.articlesData.forEach(item => {
        this.newsSites.forEach(site =>  {
          if(item.newsType === site) {
            var articl
            this.newsArticles[this.counter].push(item);
          } else {
            this.counter++;
          }
        },this.counter = 0)
      })
     });
   }

 
   private getContextBusinessArticles() {
    this.businessRequest.getAllArticles().subscribe(data => {
      this.articlesData = Array.from(Object.keys(data),k =>data[k]);
      this.articlesData.forEach(item => {
        this.businessSites.forEach(site =>  {
          if(item.newsType === site) {
            this.businessArticles[this.counter].push(item);
          } else {
            this.counter++;
          }
        },this.counter = 0)
      });
     });
   }
		

   private getNewYorkTimesNews() {
    this.newsRequest.getNewYorkArticles().subscribe(data => {
      this.articlesData = Array.from(Object.keys(data),k =>data[k]);
      this.articlesData.forEach(item => {
          switch(item.newsType) {
              case NewsSites.NewYorkTimes:
              this.newYorkPoliticsArticles.push(item);
              break;
       
          }
      });
     });
   }

   private getContextSportArticles() {
    this.sportRequest.getAllArticles().subscribe(data => {
      this.articlesData = Array.from(Object.keys(data),k =>data[k]);
      this.articlesData.forEach(item => {
        this.sportSites.forEach(site =>  {
          if(item.newsType === site) {
            this.sportArticles[this.counter].push(item);
          } else {
            this.counter++;
          }
        },this.counter = 0)
      });
     });
   }

  

   private getNewYorkTimesSportsNews() {
    this.sportRequest.getNewYorkArticles().subscribe(data => {
      this.articlesData = Array.from(Object.keys(data),k =>data[k]);
      this.articlesData.forEach(item => {
          switch(item.newsType) {
              case SportSites.NewYorkTimes:
              this.newYorkTimesSportArticles.push(item);
              break;
       
          }
      });
     });
   }

   private getContextTechnologyArticles() {
    this.technologyService.getAllArticles().subscribe(data => {
      this.articlesData = Array.from(Object.keys(data),k =>data[k]);
      this.articlesData.forEach(item => {
        this.technologySites.forEach(site =>  {
          if(item.newsType === site) {
            this.technologyArticles[this.counter].push(item);
          } else {
            this.counter++;
          }
        },this.counter = 0)
      });
     });
   }
   
   private getTechArticles() {
    this.technologyService.getOtherArticles().subscribe(data => {
      this.articlesData = Array.from(Object.keys(data),k =>data[k]);
      this.articlesData.forEach(item => {
        this.technologyOtherSites.forEach(site =>  {
          if(item.newsType === site) {
            this.technologyOtherArticles[this.counter].push(item);
          } else {
            this.counter++;
          }
        },this.counter = 0)
      });
     });
   }


   private getContextTravelArticles() {
    this.travelSerice.getAllArticles().subscribe(data => {
      this.articlesData = Array.from(Object.keys(data),k =>data[k]);
      this.articlesData.forEach(item => {
        this.travelSites.forEach(site =>  {
          if(item.newsType === site) {
            this.travelArticles[this.counter].push(item);
          } else {
            this.counter++;
          }
        },this.counter = 0)
      });
     });
   }

   private getContextRandomNewsArticle() {
    this.newsRequest.getNRandomArticle().subscribe(item => {
        this.newsRandomArticle.push(item);
      }
      );
   }

   private getContextRandomBusinessArticle() {
    this.businessRequest.getBRandomArticle().subscribe(item => {
      this.businessRandomArticle.push(item);
    });
   }

   private getContextRandomSportArticle() {
    this.sportRequest.getSRandomArticle().subscribe(item => {
      this.sportRandomArticle.push(item);
    });
   }

   private getContextRandomTechnologyArticle() {
    this.technologyService.getTeRandomArticle().subscribe(item => {
      this.technologyRandomArticle.push(item);
    });
   }

   private getContextRandomTravelArticle() {
    this.travelSerice.getTrRandomArticle().subscribe(data5 => {
      this.travelRandomArticle.push(data5);
    });
   }

   public scrollTo(el:string) {
    document.getElementById(el).scrollIntoView();
  }

  private generateArticles() {
    this.getContextNewsArticles();
    this.getNewYorkTimesNews();
    
    this.getContextBusinessArticles();


    this.getContextSportArticles();
    this.getNewYorkTimesSportsNews();

    this.getContextTechnologyArticles();
    this.getTechArticles();

    this.getContextTravelArticles();

    this.getContextRandomNewsArticle();
    this.getContextRandomBusinessArticle();
    this.getContextRandomSportArticle();
    this.getContextRandomTechnologyArticle();
    this.getContextRandomTravelArticle();
  }
}
