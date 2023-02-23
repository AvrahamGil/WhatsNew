import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public isLogging:boolean = false;
  public jwt:any;
  public csrf:any;

  constructor() {}

  ngOnInit() {
    this.isUserLoging();
  }

  private isUserLoging() {
    var details = JSON.stringify(localStorage.getItem('loging'))
    if(details.match('true')) {
      this.isLogging = true;
    } else {
      this.isLogging = false;
    }

  }
}
