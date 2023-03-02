import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public isLogging:boolean = false;
  public jwt:any;
  public csrf:any;
  public uuid: any;

  constructor(public cookieService:CookieService) {}

  public isUserLoging() : boolean {
    var details = JSON.stringify(localStorage.getItem('X-TOKEN'))
    if(details.length > 10) {
      this.isLogging = true;
      return true;
    } else {
      this.isLogging = false;
      return false;
    }
  }

}
