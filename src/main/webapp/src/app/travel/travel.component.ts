import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ArticleService } from '../services/articles.service';
import { ErrorHandlerService } from '../services/errorhandlerservice.service';
import { LoginService } from '../services/login.service';
import { WelcomeComponent } from '../welcome/welcome.component';

@Component({
  selector: 'app-travel',
  templateUrl: './travel.component.html',
  styleUrls: ['../app.component.scss']
})
export class TravelComponent {

  public travelAr:any  = [];
  public sites:Array<{name:string,value:number}> = [];
  public isLoging:boolean = false;

  constructor(private welcome: WelcomeComponent,private articleService:ArticleService,private loginService: LoginService,public cookieService:CookieService,private errorHandlerService:ErrorHandlerService) {}

  ngOnInit() {
    this.getArticles();
    this.isLoging = this.loginService.isUserLoging();
  }

  public likedArticle(item:any,type:string,index:number) {
    const csrf = localStorage.getItem('X-CSRF-TOKEN')!

    this.articleService.likeArticle(item.id,csrf).then(() => {
      localStorage.setItem('X-CSRF-TOKEN',this.cookieService.get('X-CSRF-TOKEN'));

      const travelDaily:number = 0
      const travelAndTourWorld:number = travelDaily + this.travelAr[0].length
      const skift:number = travelAndTourWorld + this.travelAr[1].length
      const phocusWire:number = skift + this.travelAr[2].length
      const visitSea:number = phocusWire + this.travelAr[3].length
      const ttnWorldWide:number = visitSea + this.travelAr[8].length
      const travelWeekly:number = ttnWorldWide + this.travelAr[6].length
      const businessTravel:number = travelWeekly + this.travelAr[9].length;
      const travelPulsel:number = businessTravel + this.travelAr[10].length
      const eturboNews:number = travelPulsel + this.travelAr[4].length
      const ttg:number = eturboNews + this.travelAr[7].length
      const lonelyPlanet:number = ttg + this.travelAr[5].length

      this.sites = [
        {name:"travelDaily",value: travelDaily},
        {name:"travelAndTourWorld",value:travelAndTourWorld},
        {name:"skift",value:skift},
        {name:"phocusWire",value:phocusWire},
        {name:"visitSea",value:visitSea},
        {name:"ttnWorldWide",value:ttnWorldWide},
        {name:"travelWeekly",value:travelWeekly},
        {name:"businessTravel",value:businessTravel},
        {name:"travelPulsel",value:travelPulsel},
        {name:"eturboNews",value:eturboNews},
        {name:"ttg",value:ttg},
        {name:"lonelyPlanet",value:lonelyPlanet}
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
      this.welcome.getArticles("travel").then((articles) => {
        this.travelAr = articles;
    });
    }catch(error:any) {
      this.errorHandlerService.handleError(error);
    }
  }
}