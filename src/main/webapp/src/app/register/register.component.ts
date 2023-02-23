import { Component, NgModule, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import {Users} from'../users';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})


export class RegisterComponent implements OnInit{

   user:FormGroup;
   message:string = "";
   siteKey:any = "";
   speicalChar:string = "~`!#$%^&*()_+-=|\\/}]{[\/:;?/>.<,-";



  constructor(private userSerivce:UserService,public fb: FormBuilder, public users:Users,private router: Router) {
    this.user = new FormGroup({
      fullName: new FormControl(),
      userName: new FormControl(),
      email: new FormControl(),
      password: new FormControl(),
      confirmPassword: new FormControl(),
      country: new FormControl(),
      recaptcha: new FormControl(),
    });

    router = new Router;
  }

  ngOnInit() {
    this.siteKey = "6LdiMn8kAAAAAJfVBuBt98ZbrqCtvziO4GCHOn7c";
  }



  public register() {
    var formData: any = new FormData();

    try {
      formData.append('fullName', this.user.get('fullName'));
      formData.append('userName', this.user.get('userName'));
      formData.append('email', this.user.get('email'));
      formData.append('password', this.user.get('password'));
      formData.append('confirmPassword', this.user.get('confirmPassword'));
      formData.append('country', this.user.get('country'));


      if(!this.validateDetails("fullName",this.user.controls['fullName'].value)) throw new Error("Full name cannot be empty").message;
      if(!this.validateDetails("userName",this.user.controls['userName'].value)) throw new Error("User name cannot be empty").message;
      if(!this.validateDetails("email",this.user.controls['email'].value)) throw new Error("Email address must be valid").message;
      if(!this.validateDetails("password",this.user.controls['password'].value,this.user.controls['confirmPassword'].value)) throw new Error("Passwords must be match").message;
      if(!this.validateDetails("country",this.user.controls['country'].value)) throw new Error("Country feild cannot be empty").message;

      this.users.fullName = this.user.controls['fullName'].value;
      this.users.userName = this.user.controls['userName'].value;
      this.users.email = this.user.controls['email'].value;
      this.users.password = this.user.controls['password'].value;
      this.users.country = this.user.controls['country'].value;

      this.users.captcha = grecaptcha.getResponse();

      this.sendRequest(this.users).then(async () => {
        this.message = "User registered successfully";
        const element:any = document.getElementById("message");
        element.style.color = "#367CD2";

        await this.delay(2000);
        this.router.navigate(['/'])
      })

    } catch(error:any) {
      this.message = error;
    }
  }


  private validateDetails(type:string,value:string, anotherValue?:string) : boolean {
    if(value === null) return false;

    if(!this.validateInput(value)) return false;

    var confirmEmail = type === "email" ? value.includes("@") && value.endsWith(".com") ||  value.endsWith(".co.il") || value.endsWith(".net") || value.endsWith(".gov"): false;

    if(type === "email" && confirmEmail == false) return false;

    var confirmPass = type === "password" ? this.validatePassword(value,anotherValue) : true;

    return confirmPass;
  }

  private validateInput(value:string) : boolean {
    if(value === null || value.length < 3 || value.includes(this.speicalChar)) {
      return false;
    }

    return true;
  }

  private validatePassword(value:string,anotherValue?:string) : boolean{
    if(anotherValue !== null) {
      var sameLength = value.length === anotherValue?.length ? true : false;

      var samePass = sameLength && value === anotherValue ? true : false;

      return samePass;
    }

    return false;
  }

  private sendRequest(user:Users) {
    return new Promise((resolve,reject) => {
      try{
         this.userSerivce.registerUser(user).then((x) => {
            resolve(x)
         }).catch((error) => {
          this.message = error.error.errorMessage;
         })
        }catch(err:any) {
          reject(err);
        }
    })
  }

  private delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
}
}
