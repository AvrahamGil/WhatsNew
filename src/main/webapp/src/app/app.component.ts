import { Component } from '@angular/core';
import * as $ from 'jquery';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent {

  header = "header";

  constructor(){ }


  ngOnInit() {
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
    };

    const input = document.getElementById('login') as HTMLElement
    input?.setAttribute('disabled', '');
  }

  public scrollTo(el:string) {
    document.getElementById(el)?.scrollIntoView();
  }
}
