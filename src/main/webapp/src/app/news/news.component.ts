import { Component, OnInit} from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ArticleService } from '../services/articles.service';
import { ErrorHandlerService } from '../services/errorhandlerservice.service';
import { LoginService } from '../services/login.service';
import { WelcomeComponent } from '../welcome/welcome.component';

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

  constructor(private welcome: WelcomeComponent,private articleService:ArticleService,private loginService: LoginService,public cookieService:CookieService,private errorHandlerService:ErrorHandlerService) {}

  ngOnInit() {
    this.getArticles();
    this.isLoging = this.loginService.isUserLoging();
  }

  public likedArticle(item:any,type:string,index:number) {
    const csrf = localStorage.getItem('X-CSRF-TOKEN')!
    this.articleService.likeArticle(item.id,csrf).then(() => {
      localStorage.setItem('X-CSRF-TOKEN',this.cookieService.get('X-CSRF-TOKEN'));

      const ynet:number = 0
      const twsj:number = ynet + this.newsAr[9].length
      const bbc:number = twsj + this.newsAr[2].length
      const time:number = bbc + this.newsAr[7].length
      const cnn:number = time + this.newsAr[3].length
      const theGuardian:number = cnn + this.newsAr[5].length;
      const theBlaze:number = theGuardian + this.newsAr[0].length
      const abcNews:number = theBlaze + this.newsAr[6].length
      const jpost:number = abcNews + this.newsAr[10].length
      const nbc:number = jpost + this.newsAr[11].length

      this.sites = [
        {name:"ynet",value: ynet},
        {name:"TWSJ",value:twsj},
        {name:"BBC",value:bbc},
        {name:"time",value:time},
        {name:"cnn",value:cnn},
        {name:"theGuardian",value:theGuardian},
        {name:"The Blaze",value:theBlaze},
        {name:"ABC News",value:abcNews},
        {name:"JPOST",value:jpost},
        {name:"NBC",value:nbc}
      ];

      this.sites.find((site) => {
        if(site.name === type) {
          var element:HTMLElement = document.getElementsByName('icone')[index+site.value];
          if(element.getAttribute("class")?.includes("unliked")) {
            element.setAttribute("class","fas fa-heart fa-lg liked");
          } else {
            element.setAttribute("class","fas fa-heart fa-lg unliked");
          }
        }

      });

    })
  }

  private getArticles() {
    try {
      this.welcome.getArticles("news").then((articles) => {
        var article = articles.find((item:any) => item.liked == true);
        if(article && this.isLoging) throw new Error("Session Expired, Please Sign In Again.")
        this.newsAr = articles;
    }).catch((err) => {
      throw new Error(err);
    });
    }catch(error:any) {
      this.errorHandlerService.handleError(error);
    }
  }


}
