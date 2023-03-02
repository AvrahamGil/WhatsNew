import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ArticleService } from '../articles.service';
import { LoginService } from '../login.service';
import { WelcomeComponent } from '../welcome/welcome.component';

@Component({
  selector: 'app-business',
  templateUrl: './business.component.html',
  styleUrls: ['../app.component.scss']
})
export class BusinessComponent {

  public businessAr:any  = [];
  public isLoging:boolean = false;
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

      const cleanTechnica:number = 0
      const zacks:number = cleanTechnica + this.businessAr[11].length
      const marketWatch:number = zacks + this.businessAr[7].length
      const businessInsider:number = marketWatch + this.businessAr[10].length
      const cnbc:number = businessInsider + this.businessAr[0].length
      const ibtimes:number = cnbc + this.businessAr[6].length
      const fool:number = ibtimes + this.businessAr[3].length
      const entrepreneur:number = fool + this.businessAr[8].length;
      const bloomberg:number = entrepreneur + this.businessAr[4].length
      const forbes:number = bloomberg + this.businessAr[2].length
      const financialTimes:number = forbes + this.businessAr[1].length
      const theStreet:number = financialTimes + this.businessAr[12].length

      this.sites = [
        {name:"cleanTechnica",value: cleanTechnica},
        {name:"zacks",value:zacks},
        {name:"marketWatch",value:marketWatch},
        {name:"businessInsider",value:businessInsider},
        {name:"cnbc",value:cnbc},
        {name:"ibtimes",value:ibtimes},
        {name:"fool",value:fool},
        {name:"entrepreneur",value:entrepreneur},
        {name:"bloomberg",value:bloomberg},
        {name:"forbes",value:forbes},
        {name:"financialTimes",value:financialTimes},
        {name:"theStreet",value:theStreet}
      ];

      this.sites.find((site) => {
        if(site.name === type) {
          document.getElementsByName('icone')[index+site.value]?.setAttribute("class","fas fa-heart fa-lg liked");
        }
      });
    });
  }

  private getArticles() {
    this.welcome.getArticles("business").then((articles) => {
        this.businessAr = articles;
    });
  }
}
