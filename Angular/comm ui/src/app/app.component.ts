
import { Component, OnInit, ViewChild } from '@angular/core';
import { Spinkit } from 'ng-http-loader';
import { environment } from './config/environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})

export class AppComponent implements OnInit {

  public spinkit = Spinkit;

  public commEnv = '';

  public personCreated = false;

  public person: Object;

  constructor() {
  }

  ngOnInit() {
    this.commEnv = environment.commEnvironment;
  }

  public back(): void {
    this.personCreated = false;
  }

  public getPerson(object): void {
    this.person = object;
    this.personCreated = true;
  }


}
