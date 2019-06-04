import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { APP_CONSTANTS } from '../app.constants';
import { CurrentPersonService } from '../components/person/shared/services/current-person.service';

/**
 * Validates if exists a selected person to show the person's page.
 */
@Injectable()
export class PersonGuard implements CanActivate {

  constructor(private router: Router, private servicePerson: CurrentPersonService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.servicePerson.getPerson()) {
      return true;
    }

    this.router.navigate([APP_CONSTANTS.routes.searchPerson]);

    return false;
  }
}
