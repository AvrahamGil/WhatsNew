import { Component, Input } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { LoginDetails } from '../login-details';
import { LoginService } from '../login.service';
import { LoginComponent } from '../login/login.component';
import { UserService } from '../user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  @Input() header = '';
  public user = 'User';
  public isLoging:boolean = false;
  public message:string = "";

  constructor(private loginService: LoginService,private loginDetails:LoginDetails ,private userService:UserService,public cookieService:CookieService) {}

  ngOnInit() {
    this.isLoging = this.loginService.isUserLoging();
    if(this.isLoging) this.user = localStorage.getItem('name')!;
  }

  public logOut() {
    this.loginDetails.csrf = localStorage.getItem('X-CSRF')?.toString()!;
    this.loginDetails.token = localStorage.getItem('X-TOKEN')!;

    localStorage.removeItem('X-CSRF');
    localStorage.removeItem('X-TOKEN');
    localStorage.removeItem('name');

    localStorage.removeItem('news');
    localStorage.removeItem('newsNewYork');
    localStorage.removeItem('business');
    localStorage.removeItem('sport');
    localStorage.removeItem('sportNewYork');
    localStorage.removeItem('technology');
    localStorage.removeItem('travel');

    window.location.href = window.location.protocol + '//' + window.location.host + '';

    this.logoutRequest(this.loginDetails).then((response:any) => {
      localStorage.setItem('X-CSRF',response.body[0].value);
      localStorage.setItem('X-TOKEN',response.body[1].value);

      this.loginService.isLogging = false;
      this.cookieService.deleteAll();
    });
  }

  private logoutRequest(details:LoginDetails) {
    return new Promise((resolve,reject) => {
      try{
         this.userService.logoutUser(details).then((x) => {
            resolve(x)
         }).catch((error) => {
          this.message = error.message;
         })
        }catch(err:any) {
          reject(err);
        }
    })
  }
}
