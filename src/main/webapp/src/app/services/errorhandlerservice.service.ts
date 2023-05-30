import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable, ErrorHandler } from '@angular/core';
import { Observable } from 'rxjs';
import { ApplicationError } from '../models/applicationerror';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService implements ErrorHandler {

  constructor(private http:HttpClient) { }

  handleError(error: any): void {
    if(error instanceof HttpErrorResponse) {
      this.register(error);
    }

    if(error instanceof Error) {
      this.register(error);
    }
  }

  private register(error:Error) : Observable<ApplicationError> {
    var postRequest = "/errors/errorlog";
    var header = new HttpHeaders();

    return this.http.post<ApplicationError>(postRequest,error,{headers:header}).pipe((err) => err);
  }
}