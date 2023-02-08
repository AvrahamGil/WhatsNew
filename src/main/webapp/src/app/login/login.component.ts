import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import { LoginDetails } from '../login-details';

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

  ngOnInit() {
    this.getCSRFToken();
  }
  constructor(private userSerivce:UserService,private fb: FormBuilder, private users:LoginDetails,private router: Router) {
    this.user = new FormGroup({
      email: new FormControl(),
      password: new FormControl(),
      token: new FormControl()
    });

    router = new Router;
  }

  public login() {
    var formData: any = new FormData();

    try {
      formData.append('email', this.user.get('email'));
      formData.append('password', this.user.get('password'));
      formData.append('token', this.user.get('token'));

      this.users.email = this.user.controls['email'].value;
      this.users.password = this.user.controls['password'].value;
      this.users.token = this.field;
      //this.users.token = this.user.controls['token'].value;

      this.sendRequest(this.users).then(async () => {
        this.message = "User login successfully";
        const element:any = document.getElementById("message");
        element.style.color = "#367CD2";

        this.router.navigate(['login'])
      })

    } catch(error:any) {
      this.message = error;
    }
  }


  private sendRequest(details:LoginDetails) {
    return new Promise((resolve,reject) => {
      try{
         this.userSerivce.loginUser(details).then((x) => {
            resolve(x)
         }).catch((error) => {
          this.message = error.error.errorMessage;
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
          console.log("token?" + x);
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
