import { Component, OnInit } from '@angular/core';
import { Spinkit } from 'ng-http-loader';
import { APP_CONSTANTS } from './app.constants';
import { AuthenticationService } from './core/services/authentication.service';
import { environment } from './config/environments/environment';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})

export class AppComponent implements OnInit {

  public constants = APP_CONSTANTS;

  public spinkit = Spinkit;
  public envName = environment.environmentName;

  isLoggedIn$: Observable<boolean>;

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.isLoggedIn$ = this.authenticationService.isLoggedIn;
  }
}
