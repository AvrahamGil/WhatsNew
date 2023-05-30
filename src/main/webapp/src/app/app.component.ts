import { Component, ElementRef, ViewChild } from '@angular/core';
import { LoginService } from './services/login.service';
import { WelcomeComponent } from './welcome/welcome.component';
import * as bootstrap from 'bootstrap';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent {

  header = "header";
  footer="footer";
  private types:Array<string> = ['news','business','sport','technology','travel'];
  isLoging:boolean = false;


  public newsAr:any = [];

  @ViewChild('helloModal') helloEl?: ElementRef;
  modal?: bootstrap.Modal;
  ngAfterViewInit() {
    this.modal = new bootstrap.Modal(this.helloEl?.nativeElement, {});
  }

  trigger() {
    this.modal?.toggle();
  }

  constructor(private loginService: LoginService,private welcome:WelcomeComponent){ }

  ngOnInit() {
    this.isLoging = this.loginService.isUserLoging();
    this.getArticles();
  }

  private getArticles() {
    this.types.forEach((types) => {

    })
    this.welcome.getArticles("news").then((articles) => {
        this.newsAr = articles;
    });
    }
}
