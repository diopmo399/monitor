import {
  HttpEvent, HttpHandler, HttpRequest, HttpErrorResponse,
  HttpInterceptor
} from '@angular/common/http';
import { tap } from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {AlertService} from '../shared/services/alert.service';
import {Observable} from 'rxjs';
import {LoaderService} from '../shared/services/loader.service';

@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {
 constructor(private alertService: AlertService, private loaderService: LoaderService) {}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
   return next.handle(request).pipe(
      tap(
        () => {},
        (err: any) => {
          if (err instanceof HttpErrorResponse) {
             this.alertService.error( 'status: ' + err.status + ' message: ' + err.message );
            this.loaderService.stopLoading();
          }
        }
      )
    );
  }
}
