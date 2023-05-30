import { Component, Input } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { LoginDetails } from '../models/login-details';
import { LoginService } from '../services/login.service';
import { UserService } from '../services/user.service';
import { ErrorHandlerService } from '../services/errorhandlerservice.service';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import userImage from '../../assets/json/image.json';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  @Input() header = '';
  public user = 'Guest';
  fileName = '';
  file!:File;
  public isLoging:boolean = false;
  public message:string = "";
  public paymentLink = '';
  public profileImage:any;

  constructor(private loginService: LoginService,private loginDetails:LoginDetails,private userService:UserService ,private cookieService:CookieService,private errorHandlerService:ErrorHandlerService,private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.isLoging = this.loginService.isUserLoging();

    if(this.isLoging) {
      this.user = localStorage.getItem('name')!;
      var image =  localStorage.getItem('image')!;

      this.getImageUrl(image);

    } else {
      this.user = "Guest";
    }

  }

  public logOut() {
    try {
      this.loginDetails.csrf = localStorage.getItem('X-CSRF-TOKEN')?.toString()!;
      this.loginDetails.token = localStorage.getItem('X-TOKEN')!;

      localStorage.clear();

      window.location.href = window.location.host + window.location.protocol + '/whatsnew';
      location.reload();

      this.logoutRequest(this.loginDetails).then(() => {
        localStorage.removeItem('X-CSRF-TOKEN')
        localStorage.removeItem('X-TOKEN')

        this.cookieService.deleteAll();
        this.loginService.isLogging = false;

      }).catch(() => {throw new Error("Please login again.")});
    } catch(error:any) {
      this.errorHandlerService.handleError(error);
    }

  }


  public editImage() {
    document.getElementById("uploadFile")?.click();
  }

  public onFileSelected(event:any) {
    this.file = event.target.files[0];

    if (this.file) {

        this.fileName = this.file.name;

        const formData = new FormData();

        formData.append("file", this.file);
        formData.append("email", this.loginDetails.email);
        formData.append("X-TOKEN", localStorage.getItem("X-TOKEN")!);

         this.sendFile(formData).then(async (response:any) => {
          if(response !== null) {
             localStorage.removeItem('image');
             localStorage.setItem('image',response.body.image);
             location.reload();
          }
        });
    }
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

  private getImageUrl(image:any) {
    var userPhoto = userImage[0]["image"]["user"];
    var url = !image.match("null") ? 'data:image/png;base64,' + localStorage.getItem('image')! : 'data:image/png;base64,' + userPhoto;

    this.profileImage = this.sanitizer.bypassSecurityTrustUrl(url);

  }

}
