import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './core/components/login/login.component';
import { LogoutComponent } from './core/components/logout/logout.component';
import { HomeComponent } from './components/home/home.component';
import { BreedComponent } from './components/register/breed/breed.component';
import { MaritalStatusComponent } from './components/register/marital-status/marital-status.component';
import { PhoneTypeComponent } from './components/register/phone-type/phone-type.component';
import { TabsComponent } from './components/person/tabs/tabs.component';
import { PersonComponent } from './components/person/person.component';
import { PersonTabComponent } from './components/person/tabs/person-tab/person-tab.component';
import { EmailTabComponent } from './components/person/tabs/email-tab/email-tab.component';
import { PhoneTabComponent } from './components/person/tabs/phone-tab/phone-tab.component';
import { AuthGuard } from './guards/auth.guard';
import { NotFoundComponent } from './shared/components/not-found/not-found.component';
import { APP_CONSTANTS } from './app.constants';
import { PersonGuard } from './guards/person.guard';

const appRoutes: Routes = [
  { path: '', redirectTo: '/' + APP_CONSTANTS.routes.home, pathMatch: 'full', canActivate: [AuthGuard] },
  { path: APP_CONSTANTS.routes.login, component: LoginComponent },
  { path: APP_CONSTANTS.routes.logout, component: LogoutComponent },
  { path: APP_CONSTANTS.routes.home, component: HomeComponent, canActivate: [AuthGuard] },
  { path: APP_CONSTANTS.routes.maritalStatus, component: MaritalStatusComponent, canActivate: [AuthGuard] },
  { path: APP_CONSTANTS.routes.breed, component: BreedComponent, canActivate: [AuthGuard] },
  { path: APP_CONSTANTS.routes.phoneType, component: PhoneTypeComponent, canActivate: [AuthGuard] },
  { path: APP_CONSTANTS.routes.searchPerson, component: PersonComponent, canActivate: [AuthGuard] },
  {
    path: APP_CONSTANTS.routes.person, component: TabsComponent, canActivate: [AuthGuard, PersonGuard],
    children: [
      { path: '', redirectTo: APP_CONSTANTS.routes.tabs.main, pathMatch: 'full' },
      { path: APP_CONSTANTS.routes.tabs.main, component: PersonTabComponent },
      { path: APP_CONSTANTS.routes.tabs.email, component: EmailTabComponent },
      { path: APP_CONSTANTS.routes.tabs.phone, component: PhoneTabComponent },
    ]
  },
  { path: APP_CONSTANTS.routes.notFound, component: NotFoundComponent },
  { path: '**', redirectTo: '/' + APP_CONSTANTS.routes.notFound }

];

export const routing = RouterModule.forRoot(appRoutes);
