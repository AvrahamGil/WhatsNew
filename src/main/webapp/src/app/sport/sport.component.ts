import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ArticleService } from '../services/articles.service';
import { ErrorHandlerService } from '../services/errorhandlerservice.service';
import { LoginService } from '../services/login.service';
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

  constructor(private welcome: WelcomeComponent,private articleService:ArticleService,private loginService: LoginService,public cookieService:CookieService,private errorHandlerService:ErrorHandlerService) {}

  ngOnInit() {
    this.getArticles();
    this.isLoging = this.loginService.isUserLoging();
  }

  public likedArticle(item:any,type:string,index:number) {
    const csrf = localStorage.getItem('X-CSRF-TOKEN')!
<<<<<<< HEAD
    this.articleService.likeArticle(item.id,csrf).then((response:any) => {
      localStorage.setItem('X-CSRF-TOKEN',response.headers.get("X-CSRF-TOKEN"));
=======
    this.articleService.likeArticle(item.id,csrf).then(() => {
      localStorage.setItem('X-CSRF-TOKEN',this.cookieService.get('X-CSRF-TOKEN'));
>>>>>>> 43fc34ca728f401a87b5d465bd19f15c579b2da4
      document.getElementById('elementIcon')?.setAttribute("class","fas fa-heart fa-lg liked");


      const yardBarker:number = 0;
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
          var element:HTMLElement = document.getElementsByName('icone')[index+site.value];
          if(element.getAttribute("class")?.includes("unliked")) {
            element.setAttribute("class","fas fa-heart fa-lg liked");
          } else {
            element.setAttribute("class","fas fa-heart fa-lg unliked");
          }
        }
      });


    });
  }

  private getArticles() {
    try {
      this.welcome.getArticles("sport").then((articles) => {
        this.sportAr = articles;
    }).catch((error) => {
      return error;
    });
    }catch(error : any) {
      this.errorHandlerService.handleError(error);
    }

  }

}
