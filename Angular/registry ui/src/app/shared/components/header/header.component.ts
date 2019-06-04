import { Component, OnInit } from '@angular/core';
import { APP_CONSTANTS } from '../../../app.constants';
import { environment } from '../../../config/environments/environment';

@Component({
    selector: 'app-header',
    templateUrl: './header.view.component.html',
    styleUrls: ['./header.style.component.css']
})
export class HeaderComponent implements OnInit {
    public constants = APP_CONSTANTS;

    public envName: string;
    public username: string;

    constructor() {
    }

    ngOnInit() {
        this.envName = environment.environmentName;
        this.username = `Operador`;
    }
}
