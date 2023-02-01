import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Users } from './users';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public articles =  Array(14).fill([[]]);
  public data:any  = [];
  public articlesData:any  = [];

  private types = ["news","business","sport","technology","travel"];

  constructor(private http:HttpClient) { }

  private postRequest:any;

  public registerUser(user:Users,type:string) : Promise<any[]> {
    var array:any = this.articles.map(() => Object.values([]));
    var arrayMap:any = this.articles.map(() => Object.values([]));

    return new Promise((resolve, reject) => {
      try {
        this.data = this.register(user).subscribe(user => console.log(user));
        resolve(this.data);
      } catch(err) {
        reject(err);
      }
    })
  }

  public loginUser(user:Users) : Promise<any[]> {
    var array:any = Array(2).fill([[]]);
    var arrayMap:any = Array(2).fill([[]]);

    return new Promise((resolve,reject) => {
      try{
        this.data = this.login(user).subscribe(user => console.log(user));
        resolve(this.data);
      }catch(err) {
        reject(err);
      }
    })

   }

  private register(user:Users) : Observable<Users> {
    this.postRequest = "http://localhost:8080/whatsnew/welcomeapi/register";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.post<Users>(this.postRequest,user).pipe((err) => err);
  }

  private login(user:Users) : Observable<Users> {
    this.postRequest = "http://localhost:8080/whatsnew/welcomeapi/login";
    var header = new HttpHeaders();
    header.append('Content-Type','application/json' );

    return this.http.post<Users>(this.postRequest,user).pipe((err) => err);
  }


}
