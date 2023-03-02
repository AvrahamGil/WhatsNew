import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ArticleService } from '../articles.service';
import { LoginService } from '../login.service';
import { WelcomeComponent } from '../welcome/welcome.component';

@Component({
  selector: 'app-technology',
  templateUrl: './technology.component.html',
  styleUrls: ['../app.component.scss']
})
export class TechnologyComponent {

  public technologyAr:any  = [];

  public articles =  Array(14).fill([[]]);
  public data:any  = [];
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

      const engadet:number = 0
      const theVerge:number = engadet + this.technologyAr[2].length
      const techCrunc:number = theVerge + this.technologyAr[3].length
      const gizmodo:number = techCrunc + this.technologyAr[8].length
      const thenextweb:number = gizmodo + this.technologyAr[1].length
      const arstechnica:number = thenextweb + this.technologyAr[7].length
      const apple:number = arstechnica + this.technologyAr[6].length
      const bitcoin:number = apple + this.technologyAr[9].length;
      const axios:number = bitcoin + this.technologyAr[11].length
      const scienceDaily:number = axios + this.technologyAr[4].length
      const wire:number = scienceDaily + this.technologyAr[0].length
      const techRadar:number = wire + this.technologyAr[5].length

      this.sites = [
        {name:"engadet",value: engadet},
        {name:"theVerge",value:theVerge},
        {name:"techCrunc",value:techCrunc},
        {name:"gizmodo",value:gizmodo},
        {name:"thenextweb",value:thenextweb},
        {name:"arstechnica",value:arstechnica},
        {name:"apple",value:apple},
        {name:"bitcoin",value:bitcoin},
        {name:"axios",value:axios},
        {name:"scienceDaily",value:scienceDaily},
        {name:"wire",value:wire},
        {name:"techRadar",value:techRadar}
      ];

      this.sites.find((site) => {
        if(site.name === type) {
          document.getElementsByName('icone')[index+site.value]?.setAttribute("class","fas fa-heart fa-lg liked");
        }
      });

    });
  }

  private getArticles() {
    this.welcome.getArticles("technology").then((articles) => {
        this.technologyAr = articles;
    });
  }

}