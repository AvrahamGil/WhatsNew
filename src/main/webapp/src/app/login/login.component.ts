import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { LoginDetails } from '../models/login-details';
import { LoginService } from '../services/login.service';
import { CookieService } from 'ngx-cookie-service';
import { ErrorHandlerService } from '../services/errorhandlerservice.service';
import { ApplicationError } from '../models/applicationerror';
import { HttpResponse } from '@angular/common/http';
import { ValidateService } from '../services/validate.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent {

  user:FormGroup;
  isLoging:boolean = false;
  message:string = "";
  field:string = "";
  speicalChar:string = "~`!#$%^&*()_+-=|\\/}]{[\/:;?/>.<,-";
  siteKey:any = "";
  authStatus:boolean = false;
  errorMessage = "One or more details are incorrect";

  profileImage:any;

  ngOnInit() {
    this.getCSRFToken();
    this.isLoging = this.isLogigng.isUserLoging();
  }

  constructor(private userSerivce:UserService,private fb: FormBuilder, private users:LoginDetails,private isLogigng:LoginService,private router: Router,private cookieService:CookieService,private errorHandlerService:ErrorHandlerService,private validateService:ValidateService) {
    this.user = this.fb.group({
      email: new FormControl(),
      password: new FormControl(),
      token: new FormControl(),
      recaptcha: new FormControl(),
    })

    router = new Router;
  }

  public login() {
    var formData: any = new FormData();
    try {
      formData.append('email', this.user.get('email'));
      formData.append('password', this.user.get('password'));
      formData.append('token', this.user.get('token'));

      this.user.controls['email'].addValidators(Validators.email);
      this.user.controls['password'].addValidators(Validators.minLength(2));

      this.users.email = this.user.controls['email'].value;
      this.users.password = this.user.controls['password'].value;

      var isFilled = !!this.users.email && !!this.users.password;
      if(!isFilled) throw new ApplicationError("Email and password are required");

      if(!this.validateService.validateDetails("email",this.user.controls['email'].value)) throw new ApplicationError("One or more details are incorrect")
      if(!this.validateService.validateDetails("password",this.user.controls['password'].value)) throw new ApplicationError("One or more details are incorrect")

      this.users.captcha = grecaptcha.getResponse();
      if(this.users.captcha.length == 0) throw new ApplicationError("Captcha must be signed");

      this.users.csrf = this.field;

      this.loginRequest(this.users).then((response:any) => {
          this.message = "User login successfully";

          this.isLogigng.isLogging = true;
          this.isLogigng.jwt = response.headers.get("X-TOKEN");
          this.isLogigng.csrf = response.headers.get("X-CSRF-TOKEN")

          localStorage.removeItem('news');
          localStorage.removeItem('newsNewYork');
          localStorage.removeItem('business');
          localStorage.removeItem('sport');
          localStorage.removeItem('sportNewYork');
          localStorage.removeItem('technology');
          localStorage.removeItem('travel');

          localStorage.setItem('X-CSRF-TOKEN',this.isLogigng.csrf);
          localStorage.setItem('X-TOKEN',this.isLogigng.jwt);
          localStorage.setItem('name',response.body.fullName);
          localStorage.setItem('image',response.body.image);

          const element:any = document.getElementById("message");
          element.style.color = "#367CD2";
          this.router.navigate(['']).then(() =>{
            window.location.href =   window.location.host + window.location.protocol + '/whatsnew';
            location.reload();
          });
      }).catch(() => {this.cookieService.deleteAll(); this.message = this.errorMessage; })

    } catch(error:any) {
      if(error instanceof ApplicationError) {
        this.message = error.message;
      } else {
        this.message = "One or more details are incorrect";
      }
      this.errorHandlerService.handleError(error);
    }
  }

  public changeStatus() {
    return new Promise((resolve) => {
      resolve(this.authStatus == true);
    })
  }

  public expired() {
    this.authStatus == false;
}

  private loginRequest(details:LoginDetails)  {
    return new Promise((resolve,reject) => {
      try{
         this.userSerivce.loginUser(details).then((response) => {
            resolve(response)
         }).catch((error) => {
          this.message = error.message;
         })
        }catch(err:any) {
          reject(err);
        }
    })
  }

  private getCSRFToken() {
    return new Promise((resolve,reject) => {
      try{
         this.userSerivce.getToken().then((x:any) => {
          this.field = x;
            resolve(x)
         }).catch((error) => {
          this.field = error;
         })
        }catch(err:any) {
          reject(err);
        }
    })
  }
}

export const env = {
  siteKey: 'key',
}
