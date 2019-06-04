// Route
import { routing } from './app.routing';
import { AuthGuard } from './guards/auth.guard';
import { PersonGuard } from './guards/person.guard';

// Modules
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { OAuthModule } from 'angular-oauth2-oidc';
import { FormsModule } from '@angular/forms';
import { NgHttpLoaderModule } from 'ng-http-loader';
import { TreeModule } from 'angular-tree-component';

// Components
import { HeaderComponent } from './shared/components/header/header.component';
import { TreeMenuComponent } from './shared/components/tree-menu/tree-menu.component';
import { NotFoundComponent } from './shared/components/not-found/not-found.component';
import { LoginComponent } from './core/components/login/login.component';
import { LogoutComponent } from './core/components/logout/logout.component';
import { HomeComponent } from './components/home/home.component';
import { GenericListComponent } from './components/register/shared/components/generic-list/generic-list.component';
import { GenericFormComponent } from './components/register/shared/components/generic-form/generic-form.component';
import { BreedComponent } from './components/register/breed/breed.component';
import { MaritalStatusComponent } from './components/register/marital-status/marital-status.component';
import { PhoneTypeComponent } from './components/register/phone-type/phone-type.component';
import { SearchPersonFormComponent } from './components/person/search-person-form/search-person-form.component';
import { PersonComponent } from './components/person/person.component';
import { PersonTableComponent } from './components/person/table/person-table.component';
import { TabsComponent } from './components/person/tabs/tabs.component';
import { PersonTabComponent } from './components/person/tabs/person-tab/person-tab.component';
import { PersonInfoComponent } from './components/person/tabs/person-tab/person-info/person-info.component';
import { PersonFormComponent } from './components/person/tabs/person-tab/person-form/person-form.component';
import { EmailTabComponent } from './components/person/tabs/email-tab/email-tab.component';
import { EmailListComponent } from './components/person/tabs/email-tab/email-list/email-list.component';
import { EmailFormComponent } from './components/person/tabs/email-tab/email-form/email-form.component';
import { PhoneTabComponent } from './components/person/tabs/phone-tab/phone-tab.component';
import { PhoneListComponent } from './components/person/tabs/phone-tab/phone-list/phone-list.component';
import { PhoneFormComponent } from './components/person/tabs/phone-tab/phone-form/phone-form.component';
import { AppComponent } from './app.component';

// Services
import { AuthenticationService } from './core/services/authentication.service';
import { BreedService } from './services/domain/breed.service';
import { MaritalStatusService } from './services/domain/marital-status.service';
import { PhoneTypeService } from './services/domain/phone-type.service';
import { PersonService } from './services/person.service';
import { EmailService } from './services/persondata/email.service';
import { PhoneService } from './services/persondata/phone.service';
import { CurrentPersonService } from './components/person/shared/services/current-person.service';

// Interceptors
import { ErrorInterceptor } from './interceptors/error.interceptor';
import { OAuthInterceptor } from './interceptors/oauth.interceptor';


import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { ModalDialogModule } from 'ngx-modal-dialog';
import { ErrorDialogComponent } from './core/components/error/error.dialog.component';
import { ConfirmDialogComponent } from './shared/components/confirm-dialog/confirm.dialog.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LogoutComponent,
    HomeComponent,
    GenericListComponent,
    GenericFormComponent,
    BreedComponent,
    MaritalStatusComponent,
    PhoneTypeComponent,
    PersonComponent,
    SearchPersonFormComponent,
    PersonTableComponent,
    TabsComponent,
    PersonTabComponent,
    PersonInfoComponent,
    PersonFormComponent,
    EmailTabComponent,
    EmailListComponent,
    EmailFormComponent,
    PhoneTabComponent,
    PhoneListComponent,
    PhoneFormComponent,
    HeaderComponent,
    NotFoundComponent,
    TreeMenuComponent,
    ErrorDialogComponent,
    ConfirmDialogComponent
  ],
  entryComponents: [
    ConfirmDialogComponent,
    ErrorDialogComponent
  ],
  imports: [
    BrowserModule,
    ModalDialogModule.forRoot(),
    ReactiveFormsModule,
    HttpClientModule,
    OAuthModule.forRoot(),
    routing,
    FormsModule,
    NgHttpLoaderModule.forRoot(),
    NgbModule.forRoot(),
    TreeModule.forRoot(),
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  exports: [
    HeaderComponent,
    TreeMenuComponent
  ],
  providers: [
    BreedService,
    MaritalStatusService,
    PhoneTypeService,
    PersonService,
    EmailService,
    PhoneService,
    CurrentPersonService,
    AuthenticationService,
    AuthGuard,
    PersonGuard,
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: OAuthInterceptor, multi: true },
    Validators

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
