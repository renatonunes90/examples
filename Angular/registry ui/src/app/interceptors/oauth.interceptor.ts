import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { APP_CONSTANTS } from '../app.constants';

@Injectable()
export class OAuthInterceptor implements HttpInterceptor {
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // envia o token para todas as requisições
        const token = JSON.parse(localStorage.getItem(APP_CONSTANTS.localStorageAuth));
        if (token && token.value) {
            request = request.clone({
                setHeaders: {
                    Authorization: 'Bearer ' + token.value
                }
            });
        }

        return next.handle(request);
    }
}
