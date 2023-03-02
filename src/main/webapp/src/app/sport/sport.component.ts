import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ArticleService } from '../articles.service';
import { LoginService } from '../login.service';
import { WelcomeComponent } from '../welcome/welcome.component';

@Component({
  selector: 'app-sport',
  templateUrl: './sport.component.html',
  styleUrls: ['../app.component.scss']
})
export class SportComponent {

  public sportAr:any  = [];
  public newYorkTimesAr:any = [];
  public sites:Array<{name:string,value:number}> = [];

  public isLoging:boolean = false;

  constructor(private welcome: WelcomeComponent,private articleService:ArticleService,private loginService: LoginService,public cookieService:CookieService) {}

  ngOnInit() {
    this.getArticles();
    this.isLoging = this.loginService.isUserLoging();
  }

  public likedArticle(item:any,type:string,index:number) {
    const csrf = localStorage.getItem('X-CSRF')!
    this.articleService.likeArticle(item,csrf).then(() => {
      localStorage.setItem('X-CSRF',this.cookieService.get('X-CSRFTOKEN'));
      document.getElementById('elementIcon')?.setAttribute("class","fas fa-heart fa-lg liked");


      const nyt:number = 0
      const yardBarker:number = nyt + this.sportAr[3].length
      const sbnation:number = yardBarker + this.sportAr[9].length
      const fansided:number = sbnation + this.sportAr[8].length
      const yahoo:number = fansided + this.sportAr[7].length
      const deadSpin:number = yahoo + this.sportAr[2].length
      const sportsIllustrated:number = deadSpin + this.sportAr[5].length
      const sportingNews:number = sportsIllustrated + this.sportAr[10].length;
      const espn:number = sportingNews + this.sportAr[6].length
      const cbs:number = espn + this.sportAr[4].length
      const mlb:number = cbs + this.sportAr[0].length
      const syracuse:number = mlb + this.sportAr[1].length

      this.sites = [
        {name:"yardBarker",value:yardBarker},
        {name:"sbnation",value:sbnation},
        {name:"fansided",value:fansided},
        {name:"yahoo",value:yahoo},
        {name:"deadSpin",value:deadSpin},
        {name:"sportsIllustrated",value:sportsIllustrated},
        {name:"sportingNews",value:sportingNews},
        {name:"espn",value:espn},
        {name:"cbs",value:cbs},
        {name:"mlb",value:mlb},
        {name:"syracuse",value:syracuse}
      ];

      this.sites.find((site) => {
        if(site.name === type) {
          document.getElementsByName('icone')[index+site.value]?.setAttribute("class","fas fa-heart fa-lg liked");
        }
      });


    });
  }

  public likedNyArticle(item:any,type:string,index:number) {
    const csrf = localStorage.getItem('X-CSRF')!
    this.articleService.nylikeArticle(item,csrf).then(() => {
      localStorage.setItem('X-CSRF',this.cookieService.get('X-CSRFTOKEN'));

      this.sites = [
        {name:"NYT",value:this.sportAr[4].length + this.sportAr[9].length + this.sportAr[2].length}
      ];

      this.sites.find((site) => {
        if(site.name === type) {
          document.getElementsByName('icone')[index+site.value]?.setAttribute("class","fas fa-heart fa-lg liked");
        }
      });
    });
  }

  private getArticles() {
    this.welcome.getArticles("sport").then((articles) => {
        this.sportAr = articles;
    });

    this.welcome.getArticles("sportNewYork").then((articles) => {
        this.newYorkTimesAr = articles;
    })
  }

}
