import { Component, OnInit} from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ArticleService } from '../articles.service';
import { LoginService } from '../login.service';
import { WelcomeComponent } from '../welcome/welcome.component';
import * as $ from 'jquery';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['../app.component.scss']
})

export class NewsComponent implements OnInit{

  public newsAr:any = [];
  public isLoging:boolean = false;
  public newYorkTimesAr:any = [];
  public sites:Array<{name:string,value:number}> = [];

  constructor(private welcome: WelcomeComponent,private articleService:ArticleService,private loginService: LoginService,public cookieService:CookieService) {}

  ngOnInit() {
    this.getArticles();
    this.isLoging = this.loginService.isUserLoging();
  }

  public likedArticle(item:any,type:string,index:number) {
    const csrf = localStorage.getItem('X-CSRF')!
    this.articleService.likeArticle(item,csrf).then(() => {
      localStorage.setItem('X-CSRF',this.cookieService.get('X-CSRFTOKEN'));

      const ynet:number = 0
      const covid19:number = ynet + this.newsAr[4].length
      const twsj:number = covid19 + this.newsAr[9].length
      const nyt:number = twsj + this.newsAr[2].length
      const bbc:number = nyt + this.newYorkTimesAr.length
      const Time:number = bbc + this.newsAr[7].length
      const CNN:number = Time + this.newsAr[1].length
      const theGuardian:number = CNN + this.newsAr[3].length;
      const theBlaze:number = theGuardian + this.newsAr[5].length
      const abcNews:number = theBlaze + this.newsAr[0].length
      const jpost:number = abcNews + this.newsAr[6].length
      const nbc:number = jpost + this.newsAr[10].length

      this.sites = [
        {name:"ynet",value: ynet},
        {name:"COVID-19",value:covid19},
        {name:"TWSJ",value:twsj},
        {name:"NYT",value:nyt},
        {name:"BBC",value:bbc},
        {name:"Time",value:Time},
        {name:"CNN",value:CNN},
        {name:"The Guardian",value:theGuardian},
        {name:"The Blaze",value:theBlaze},
        {name:"ABC News",value:abcNews},
        {name:"JPOST",value:jpost},
        {name:"NBC",value:nbc}
      ];

      this.sites.find((site) => {
        if(site.name === type) {
          document.getElementsByName('icone')[index+site.value]?.setAttribute("class","fas fa-heart fa-lg liked");
        }
      });

    })
  }

  public likedNyArticle(item:any,type:string,index:number) {
    const csrf = localStorage.getItem('X-CSRF')!
    this.articleService.nylikeArticle(item,csrf).then(() => {
      localStorage.setItem('X-CSRF',this.cookieService.get('X-CSRFTOKEN'));

      this.sites = [
        {name:"NYT",value:this.newsAr[4].length + this.newsAr[9].length + this.newsAr[2].length}
      ];

      this.sites.find((site) => {
        if(site.name === type) {
          document.getElementsByName('icone')[index+site.value]?.setAttribute("class","fas fa-heart fa-lg liked");
        }
      });
    })
  }

  private getArticles() {
  this.welcome.getArticles("news").then((articles) => {
      this.newsAr = articles;
  });

  this.welcome.getArticles("newsNewYork").then((articles) => {
      this.newYorkTimesAr = articles;
  })
  }


}
