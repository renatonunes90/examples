import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgHttpLoaderModule } from 'ng-http-loader';

import { ConfirmCreationComponent } from '../components/confirm-creation/confirm-creation.component';
import { CreateAccountFormComponent } from '../components/create-person-form/create-person-form.component';

import { RegistryService } from '../services/registry.service';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { CommRegistryComponent } from './comm-registry.component';

@NgModule({
  declarations: [CommRegistryComponent,
    ConfirmCreationComponent,
    CreateAccountFormComponent],
  entryComponents: [
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    NgHttpLoaderModule.forRoot(),
    NgbModule.forRoot(),
    ReactiveFormsModule
  ],
  exports: [
    CommRegistryComponent
  ],
  providers: [
    RegistryService,
    Validators,
    [{ provide: 'Callback', useValue: (<any>window).CALLBACK }]
  ]
})
export class CommRegistryModule { }
