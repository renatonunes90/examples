import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { APP_CONSTANTS } from '../../../app.constants';
import { AuthenticationService } from '../../services/authentication.service';
import { Login } from '../../models/login.model';
import { Token } from '../../models/interfaces/token.model';

@Component({
    selector: 'app-login',
    templateUrl: './login.view.component.html'
})
export class LoginComponent {

    public usernameControl = new FormControl('');
    public passwordControl = new FormControl('');
    public loginObj = new Login();
    public errorMessage = '';

    private token: Token;

    constructor(private authService: AuthenticationService) {
    }

    public doLogin(): void {

        this.loginObj.username = this.usernameControl.value;
        this.loginObj.password = this.passwordControl.value;

        this.authService.login(this.loginObj)
            .subscribe(
                (resp) => {
                    this.token = resp.body;
                    this.initializeApplication();
                },
                (error) => {
                    console.log(error);
                    this.token = Object.create(null);
                    this.initializeApplication();
                    if (error.error && error.error.errors && error.error.errors.length > 0) {
                        this.errorMessage = error.error.errors[0];
                    } else if (error.error.length > 0 && error.error[0].userMessage) {
                        this.errorMessage = error.error[0].userMessage;
                    } else {
                        this.errorMessage = 'Não foi possível conectar-se ao servidor de login, tente novamente mais tarde.';
                    }
                });
    }

    public initializeApplication(): void {
        localStorage.setItem(APP_CONSTANTS.localStorageAuth, `{ "key":"token", "value":"${this.token.access_token}"}`);
        window.location.href = '';
    }

}
