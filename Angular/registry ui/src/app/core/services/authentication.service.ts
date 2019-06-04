import { BehaviorSubject } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { APP_CONSTANTS } from '../../app.constants';
import { environment } from '../../config/environments/environment';
import { Login } from '../models/login.model';
import { Token } from '../models/interfaces/token.model';

@Injectable()
export class AuthenticationService {

    constructor(private http: HttpClient) { }

    public login(login: Login): Observable<HttpResponse<Token>> {
        return this.http.post<Token>(
            environment.environmentHost.concat(APP_CONSTANTS.authenticationPath),
            `username=${login.username}&password=${login.password}`,
            {
                headers: new HttpHeaders({
                    'Content-Type': 'application/x-www-form-urlencoded'
                }),
                observe: 'response'
            }
        );
    }

    public logout() {
        localStorage.removeItem(APP_CONSTANTS.localStorageAuth);
        window.location.href = '';
    }

    get isLoggedIn() {
        const ls = localStorage.getItem(APP_CONSTANTS.localStorageAuth);
        const loggedIn = new BehaviorSubject<boolean>(ls != null);
        return loggedIn.asObservable();
    }


}
