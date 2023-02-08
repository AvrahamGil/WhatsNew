import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { LoginDetails } from './login-details';
import { Users } from './users';

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
        this.register(user).subscribe((data:any) =>  resolve(data),(error) => { reject(error)});

      } catch(err:any) {
        throw new Error("One or more details are incorrect");
      }
    })
  }

  public loginUser(details:LoginDetails) : Promise<any[]> {

    return new Promise((resolve,reject) => {
      try{
        this.login(details).subscribe((data:any) => resolve(data),(error) => {reject(error)});

      }catch(err) {
        throw new Error("One or more details are incorrect");
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

  private register(user:Users) : Observable<Users> {
    this.postRequest = "http://localhost:8080/whatsnew/welcomeapi/register";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.post<Users>(this.postRequest,user).pipe((err) => err);
  }

  private login(user:LoginDetails) : Observable<Users> {
    this.postRequest = "http://localhost:8080/whatsnew/welcomeapi/login";
    var header = new HttpHeaders();
    const params = new HttpParams()
    .append('token', user.token)
    header.append('Content-Type','application/json' );

    return this.http.post<Users>(this.postRequest,user,{params:params}).pipe((err) => err);
  }

  private getCSRFToken()  {
    this.getRequest = "http://localhost:8080/whatsnew/welcomeapi/token";
    const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');

    return this.http.get(this.getRequest,{headers:headers,responseType:'text'}).pipe((err) => err);
  }
}
