import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ArticleService } from '../services/articles.service';
import { FormControl, FormGroup } from '@angular/forms';
import { ApplicationError } from '../models/applicationerror';
import { ValidateService } from '../services/validate.service';
import countries from '../../assets/json/countries.json';
import { Countries } from '../models/countries';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent {

  private types:Array<string> = ['news','business','sport','technology','travel'];
  public message:string = "";
  contact:FormGroup;
  public countries:any = countries;

  constructor(private articlesService: ArticleService,private cookieService:CookieService,private validateService:ValidateService, private userService:UserService) {
    this.contact = new FormGroup({
      firstName: new FormControl(),
      lastName: new FormControl(),
      email: new FormControl(),
      country: new FormControl(),
      message: new FormControl(),
    })
  }

  public goToSection(section:string) {
      var element = document.getElementById(section);
      element?.scrollIntoView();
  }

  public getArticles(type:string) : Promise<any> {
    return new Promise((resolve,reject) => {
        this.init(type).then((articles) => {
          resolve(articles);
       }).catch((err) => {reject(err)});
    })

  }

  public sendMessage() {
    var formData: any = new FormData();

    try {
      formData.append('firstName', this.contact.get('firstName'));
      formData.append('lastName', this.contact.get('lastName'));
      formData.append('email', this.contact.get('email'));
      formData.append('country', this.contact.get('country'));
      formData.append('message', this.contact.get('message'));

      if(!this.validateService.validateDetails("firstName",this.contact.controls['firstName'].value)) throw new ApplicationError("First name required and must be length 2 to 15.");
      if(!this.validateService.validateDetails("lastName",this.contact.controls['lastName'].value)) throw new ApplicationError("Last name required and must be length 2 to 15.");
      if(!this.validateService.validateDetails("email",this.contact.controls['email'].value)) throw new ApplicationError("Email required, must be a valid address.");
      if(!this.validateService.validateDetails("country",this.contact.controls['country'].value.code)) throw new ApplicationError("Country required, must select a country.");
      if(!this.validateService.validateDetails("message",this.contact.controls['message'].value)) throw new ApplicationError("Message required.");

      this.send(formData);
    }catch(error) {
      console.log(error)
    }
  }
  private init(type:any) : Promise<any> {
    return new Promise((resolve,reject) => {
      const csrf:any = this.cookieService.get('X-CSRF-TOKEN');
            this.articlesService.getContextArticles(type,csrf).then((articles:any) => {
              localStorage.setItem(type,JSON.stringify(articles));
              resolve(JSON.parse(localStorage.getItem(type)!))
            }).catch((err) => reject(err));
    })

  }

  private send(details:any) {
    this.userService.contactMeRequest(details).then(() => {
      this.message = "Message sent";
    })
  }
}
