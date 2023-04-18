import { Injectable } from '@angular/core';
import { LoginDetails } from '../models/login-details';


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public isLogging:boolean = false;
  public jwt:any;
  public csrf:any;

  constructor(private loginDetails:LoginDetails) {}

  public isUserLoging() : boolean {
    try {
      this.loginDetails.token = JSON.stringify(localStorage.getItem('X-TOKEN'));
      this.loginDetails.csrf = JSON.stringify((localStorage.getItem('X-CSRF-TOKEN')));

      if(this.loginDetails.token == "null" ||this.loginDetails.csrf == "null" ) return false;

      return true;

    }catch(err) {
      throw new Error('Something went wrong...')
    }
  }

}
