import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { OAuthService } from 'angular-oauth2-oidc';
import { APP_CONSTANTS } from '../app.constants';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private oauthService: OAuthService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (localStorage.getItem(APP_CONSTANTS.localStorageAuth)) {
      return true;
    }

    this.router.navigate(['/' + APP_CONSTANTS.routes.login]);

    return false;
  }
}
