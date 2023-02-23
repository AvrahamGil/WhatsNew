import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import { LoginDetails } from '../login-details';
import { LoginService } from '../login.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent {

  user:FormGroup;


  message:string = "";
  field:string = "";
  speicalChar:string = "~`!#$%^&*()_+-=|\\/}]{[\/:;?/>.<,-";
  siteKey:any = "";
  authStatus:boolean = false;
  validateSuccess:boolean = false;


  ngOnInit() {
    this.getCSRFToken();
  }

  constructor(private userSerivce:UserService,private fb: FormBuilder, private users:LoginDetails,private isLogigng:LoginService,private router: Router) {
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

      this.validateSuccess = !!this.users.email && !!this.users.password;

      if( !this.validateSuccess) throw new Error("One or more details are incorrect");

      this.users.captcha = grecaptcha.getResponse();

      this.users.token = this.field;

      this.loginRequest(this.users).then((response:any) => {
          this.message = "User login successfully";
          this.isLogigng.isLogging = true;
          this.isLogigng.jwt = response.body[1].value;
          localStorage.setItem('loging',this.isLogigng.jwt);

          const element:any = document.getElementById("message");
          element.style.color = "#367CD2";

          this.router.navigate([''])
      }).catch((error) => {this.message = error;})


    } catch(error:any) {
      this.message = error;
    }
  }

  public logout() {
    try {
      this.logoutRequest(this.users).then(async () => {
          this.isLogigng.isLogging = false;
          localStorage.setItem('loging',this.isLogigng.isLogging.toString());
          const element:any = document.getElementById("message");
          element.style.color = "#367CD2";

          this.router.navigate([''])
      }).catch((error) => {this.message = error;})


    } catch(error:any) {
      this.message = error;
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
          console.log("First response?" + response);
            resolve(response)
         }).catch((error) => {
          this.message = error.message;
         })
        }catch(err:any) {
          reject(err);
        }
    })
  }

  private logoutRequest(details:LoginDetails) {
    return new Promise((resolve,reject) => {
      try{
         this.userSerivce.logoutUser(details).then((x) => {
            resolve(x)
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
  siteKey: '6LdiMn8kAAAAAJfVBuBt98ZbrqCtvziO4GCHOn7c',
}
