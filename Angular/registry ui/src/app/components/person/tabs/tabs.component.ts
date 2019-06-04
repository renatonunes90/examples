import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { APP_CONSTANTS } from '../../../app.constants';
import { CurrentPersonService } from '../shared/services/current-person.service';
import { Person } from '../../../models/person.model';
import { TABS_CONST } from './tabs.constants';

@Component({
    selector: 'app-tabs',
    templateUrl: 'tabs.view.component.html',
    styleUrls: ['tabs.style.component.css'],
})
export class TabsComponent {

    public constants = TABS_CONST;

    private prefix = '/' + APP_CONSTANTS.routes.person + '/';
    public links = [{
        'label': this.constants.person, 'link': this.prefix + APP_CONSTANTS.routes.tabs.main, 'enabled': true,
        'selected': false
    },

    {
        'label': this.constants.phones, 'link': this.prefix + APP_CONSTANTS.routes.tabs.phone, 'enabled': true,
        'selected': false
    },
    {
        'label': this.constants.emails, 'link': this.prefix + APP_CONSTANTS.routes.tabs.email, 'enabled': true,
        'selected': false
    }];
    activeLink = this.links[0];

    public person: Person;

    constructor(
        private selectedPerson: CurrentPersonService,
        private router: Router) {
        this.person = this.selectedPerson.getPerson();

        this.selectTab(this.router.url);
    }

    public backClicked(): void {
        this.router.navigate(['/' + APP_CONSTANTS.routes.searchPerson]);
    }

    public selectTab(tabSelected: string): void {
        this.links.forEach((nav) => {
            if (tabSelected === nav.link) {
                nav.selected = true;
            } else {
                nav.selected = false;
            }
        });
    }


}
