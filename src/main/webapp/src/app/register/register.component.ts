import { Component, NgModule, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import {Users} from'../models/users';
import countries from '../../assets/json/countries.json';
import { Countries } from '../models/countries';
import { ErrorHandlerService } from '../services/errorhandlerservice.service';
import { ApplicationError } from '../models/applicationerror';
import { ValidateService } from '../services/validate.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})


export class RegisterComponent implements OnInit{

   user:FormGroup;
<<<<<<< HEAD
=======
   siteKey:any = "";
>>>>>>> 43fc34ca728f401a87b5d465bd19f15c579b2da4
   fileName = '';
   file!:File;

   speicalChar:string = "~`!#$%^&*()_+-=|\\/}]{[\/:;?/>.<,-";

   message:string = "";
   registerSuccess="User registered successfully";
   registerFailed = "One or more details are incorrect";
   errorMessage = "One or more details are incorrect";

   public countries:any = countries;

  constructor(private userService:UserService,public fb: FormBuilder, private users:Users,private router: Router,private errorHandlerService:ErrorHandlerService,private validateService:ValidateService) {
    this.user = new FormGroup({
      fullName: new FormControl(),
      userName: new FormControl(),
      email: new FormControl(),
      password: new FormControl(),
      confirmPassword: new FormControl(),
      country: new FormControl(),
      file: new FormControl(),
      recaptcha: new FormControl(),
    });

    router = new Router;
  }

  ngOnInit() {
<<<<<<< HEAD

  }

=======
    this.siteKey = "6LdiMn8kAAAAAJfVBuBt98ZbrqCtvziO4GCHOn7c";
  }



>>>>>>> 43fc34ca728f401a87b5d465bd19f15c579b2da4
  public register() {
    var formData: any = new FormData();

    try {
      formData.append('fullName', this.user.get('fullName'));
      formData.append('userName', this.user.get('userName'));
      formData.append('password', this.user.get('password'));
      formData.append('confirmPassword', this.user.get('confirmPassword'));
      formData.append('country', this.user.get('country'));

      if(!this.validateService.validateDetails("fullName",this.user.controls['fullName'].value)) throw new ApplicationError("Full name required and must be length 2 to 15.");
      if(!this.validateService.validateDetails("userName",this.user.controls['userName'].value)) throw new ApplicationError("User name required and must be length 2 to 15.");
      if(!this.validateService.validateDetails("email",this.user.controls['email'].value)) throw new ApplicationError("Email required, must be a valid address.");
      if(!this.validateService.validateDetails("password",this.user.controls['password'].value,this.user.controls['confirmPassword'].value)) throw new ApplicationError("Passwords required and must be length 2 to 15.");
      if(!this.validateService.validateDetails("country",this.user.controls['country'].value.code)) throw new ApplicationError("Country required, must select a country.");

      this.users.fullName = this.user.controls['fullName'].value;
      this.users.userName = this.user.controls['userName'].value;
      this.users.email = this.user.controls['email'].value;
      this.users.password = this.user.controls['password'].value;
      this.users.country = this.user.controls['country'].value.code;

      formData.append('email', this.user.controls['email'].value);

<<<<<<< HEAD
=======
      this.users.captcha = grecaptcha.getResponse();

      if(this.users.captcha.length == 0) throw new ApplicationError("Captcha is required.");

>>>>>>> 43fc34ca728f401a87b5d465bd19f15c579b2da4
      if(this.file !== undefined) this.users.withImage = true;

      this.sendRequest(this.users).then(async (response:any) => {
        localStorage.setItem('X-TOKEN',response.headers.get("X-TOKEN"));

        if(this.users.withImage) {
          formData.append("file", this.file);
          formData.append("X-TOKEN", localStorage.getItem("X-TOKEN"));

          await this.sendFile(formData).then((response:any) => {
            if(response.body === true) this.users.withImage = true;
          });
        }

        this.message = this.registerSuccess;

        const element:any = document.getElementById("message");
        element.style.color = "#367CD2";

        this.router.navigate(['']).then(() =>{
          window.location.href =   window.location.host + window.location.protocol + '/whatsnew';
          location.reload();
        });

        setTimeout(() => {
          if((!this.message.match(this.registerSuccess))) {
            this.message = this.registerFailed;
          }
        }, 1000);

      }).catch((err) => {this.message = this.errorMessage; throw new Error(err)})

    } catch(error:any) {
      if(error instanceof ApplicationError) {
        this.message = error.message;
      } else {
        this.message = this.registerFailed;
      }
      this.errorHandlerService.handleError(error);

    }
  }

  public onFileSelected(event:any) {

    this.file = event.target.files[0];

    if (this.file) {

        this.fileName = this.file.name;

        const formData = new FormData();

        formData.append("file", this.file);
    }
}

  private sendFile(file:any) {
    return new Promise((resolve,reject) => {
      try{
         this.userService.upload(file).then((x) => {
            resolve(x)
         }).catch((error) => {
          this.message = error.errorMessage;
         })
        }catch(err:any) {
          reject(err);
        }
    })
  }


  private sendRequest(user:Users) {
    return new Promise((resolve,reject) => {
      try{
         this.userService.registerUser(user).then((x) => {
            resolve(x)
         }).catch((error) => {
          this.message = error.errorMessage;
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
