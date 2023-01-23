import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ArticleService } from './articles.service';
import sites from '../assets/json/sites.json';
import { Articles } from './articles';
import $ from 'jquery';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})


export class AppComponent {


  constructor(public http: HttpClient,public articlesService : ArticleService){ }


  public realArticles =  Array(14).fill([[]]);
  public articlesData:any  = [];
  public actualArticles:any  = [];
  public randomArticles:any  = [];

  public newsAr:any  = [];
  public businessAr:any  = [];
  public sportAr:any  = [];
  public technologyAr:any  = [];
  public travelAr:any  = [];
  public newYorkTimesAr:any = [];


  ngOnInit() {


  this.generateArticles();

  $("#scrollTop").click(() =>{
    $("html").scrollTop(0);
    });

    if ($(window).width.length > 992) {
      $(window).scroll(() => {
         if ($(this).scrollTop.length > 40) {
            $('#header').addClass("fixed-top");
            $('body').css('padding-top', $('#header').outerHeight() + 'px');
          }else{
            $('#header').removeClass("fixed-top");
            $('body').css('padding-top', '0');
          }
      });
    }
  }


  private getContextArticles() {
    this.articlesService.getAllArticles().subscribe((data:Articles) => {
      this.articlesData = Array.from(Object.keys(data),(k) =>data[k as keyof Articles]);
      this.actualArticles[0] = this.articlesData[0];
      this.actualArticles[1] = this.articlesData[1];
      this.actualArticles[2] = this.articlesData[2];
      this.actualArticles[3] = this.articlesData[3];
      this.actualArticles[4] = this.articlesData[4];

      this.newsAr = this.realArticles.map((x) => Object.values([]));
      this.businessAr = this.realArticles.map((x) => Object.values([]));
      this.sportAr = this.realArticles.map((x) => Object.values([]));
      this.technologyAr = this.realArticles.map((x) => Object.values([]));
      this.travelAr = this.realArticles.map((x) => Object.values([]));


      this.actualArticles[0].forEach((x:any) => {
        var index = sites[0].news[x.newsType];
        this.newsAr[index].push(x);
      })

      this.actualArticles[1].forEach((x:any) => {
        var index = sites[1].business[x.newsType];
        this.businessAr[index].push(x);
      })

      this.actualArticles[2].forEach((x:any) => {
        var index = sites[2].sport[x.newsType];
        this.sportAr[index].push(x);
      })

      this.actualArticles[3].forEach((x:any) => {
        var index = sites[3].technology[x.newsType];
        this.technologyAr[index].push(x);
      })

      this.actualArticles[4].forEach((x:any) => {
        var index = sites[4].travel[x.newsType];
        this.travelAr[index].push(x);
      })
     },err => {
      console.log(err)
     });
   }

   private getNewYorkArticles() {
    this.articlesService.getNewYorkArticles().subscribe(data => {
      this.articlesData = Array.from(Object.keys(data),k =>data[k as keyof Articles]);

      this.newYorkTimesAr[0] = this.articlesData.slice(0,5);
      this.newYorkTimesAr[1] = this.articlesData.slice(6);

     },err => {
      console.log(err)
     });
   }

   public scrollTo(el:string) {
    document.getElementById(el)?.scrollIntoView();
  }

  private generateArticles() {
    this.getContextArticles();
    this.getNewYorkArticles();

  }
}
