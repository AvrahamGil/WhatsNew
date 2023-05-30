import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { LoginDetails } from '../models/login-details';
import { Users } from '../models/users';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public data:any  = [];

  constructor(private http:HttpClient) { }

  private postRequest:any;
  private getRequest:any;

  public registerUser(user:Users) : Promise<any[]> {
    return new Promise((resolve, reject) => {
      try {
        this.register(user).subscribe((data:any) =>  resolve(data));

      } catch(err:any) {
        reject(err);
      }
    })
  }

  public loginUser(details:LoginDetails) : Promise<any[]> {

    return new Promise((resolve,reject) => {
      try{
        this.login(details).subscribe((response:any) => {resolve(response)},(error) => {return "One or more details are incorrect"});

      }catch(err:any) {
        reject(err);
      }
    })

   }


   public logoutUser(details:LoginDetails) : Promise<any[]> {
    return new Promise((resolve,reject) => {
      try{
        this.logout(details).subscribe((data:any) => resolve(data),(error) => {return "One or more details are incorrect"});

      }catch(err:any) {
        reject(err);
      }
    })

   }

   public upload(file:any) : Promise<any[]> {
    return new Promise((resolve,reject) => {
      try{
        this.uploadFile(file).subscribe((data:any) => resolve(data),(error) => {return "One or more details are incorrect"});

      }catch(err:any) {
        reject(err);
      }
    })

   }

   public contactMeRequest(file:any) : Promise<any[]> {
    return new Promise((resolve,reject) => {
      try{
        this.contactRequest(file).subscribe((data:any) => resolve(data),(error) => {return "One or more details are incorrect"});

      }catch(err:any) {
        reject(err);
      }
    })

   }

   public getToken() : Promise<any[]> {

    return new Promise((resolve,reject) => {
      try{
        this.getCSRFToken().subscribe((data:any) => resolve(data),(error) => {reject(error)});

      }catch(err) {
        throw new Error("One or more details are incorrect");
      }
    })
   }

  private register(user:Users) : Observable<HttpResponse<ArrayBuffer>> {
    this.postRequest = "/welcome/register";
    var header = new HttpHeaders().set('RECAPTCHA', user.captcha).set('Content-Type','application/json');

    return this.http.post<ArrayBuffer>(this.postRequest,user,{headers:header,observe:'response'}).pipe((err) => err);
  }

  private logout(user:LoginDetails) : Observable<HttpResponse<ArrayBuffer>> {
    this.postRequest = "/rest/api/user/logout";

    const header = new HttpHeaders().set('X-CSRF-TOKEN' , user.csrf);

    return this.http.post<ArrayBuffer>(this.postRequest,user,{headers:header,observe:'response',withCredentials: true}).pipe((err) => err);
  }

  private login(user:LoginDetails) : Observable<HttpResponse<ArrayBuffer>> {
    this.postRequest = "/welcome/login";

    const header = new HttpHeaders().set('X-CSRF-TOKEN' , user.csrf).set('RECAPTCHA', user.captcha);

    return this.http.post<ArrayBuffer>(this.postRequest,user,{headers:header,observe:'response',withCredentials: true}).pipe((err) => err);
  }

  private uploadFile(file:any) : Observable<HttpResponse<ArrayBuffer>> {
    this.postRequest = "/upload/";

    return this.http.post<ArrayBuffer>(this.postRequest,file,{observe:'response',withCredentials: true}).pipe((err) => err);
  }

  private contactRequest(file:any) : Observable<HttpResponse<ArrayBuffer>> {
    this.postRequest = "/rest/api/user/contact";

    return this.http.post<ArrayBuffer>(this.postRequest,file,{observe:'response',withCredentials: true}).pipe((err) => err);
  }

  private getCSRFToken()  {
    this.getRequest = "/welcome/csrftoken";
    const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');

    return this.http.get(this.getRequest,{headers:headers,responseType:'text'}).pipe((err) => err);
  }
}
