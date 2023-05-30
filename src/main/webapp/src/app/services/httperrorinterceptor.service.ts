import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';


export class HttpErrorInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(request)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          var token = localStorage.getItem('X-TOKEN');

          let errorMsg = '';

          if (error.error instanceof ErrorEvent) {
            var ele = document.getElementById('expiredAlert');
            ele?.classList.remove('hide');
          }

          else {
            document.getElementById('expiredAlert')?.classList.remove('hide');
          }

          setTimeout(() => {
            if(!window.location.href.includes('login') && token !== null) {
              localStorage.removeItem('X-CSRF-TOKEN');
              localStorage.removeItem('X-TOKEN');

              window.location.replace("whatsnew.me/user/login");
              location.reload();
            }
          }, 2000);
          return throwError(errorMsg);

        return throwError(""); })
      )
  }
}