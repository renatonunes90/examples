import { Component, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { APP_CONSTANTS } from '../../app.constants';
import { CurrentPersonService } from './shared/services/current-person.service';
import { Person } from '../../models/person.model';
import { PERSON_CONSTANTS } from './person.constants';

/**
 * Component that implements a person search form.
 */
@Component({
    selector: 'app-person',
    templateUrl: 'person.view.component.html'
})
export class PersonComponent {

    public constants = PERSON_CONSTANTS;

    public searchPersonEventEmmiter = new EventEmitter<FormGroup>();

    public person: Person;

    /**
     * Flag indicating if the person form must be rendered.
     */
    public showAddForm: boolean;

    constructor(private selectedPerson: CurrentPersonService,
        private router: Router) {
    }

    /**
     * Shows the form to add a person.
     */
    public addClicked() {

        // creates an empty person
        this.person = new Person();

        this.showAddForm = true;
    }

    /**
     * Cancels the person add and returns to the person search or goes to the person's page.
     */
    public backSearch(event: Person): void {
        if (event) {
            this.selectedPerson.setPerson(event);
            this.router.navigate(['/' + APP_CONSTANTS.routes.person]);
        }

        this.showAddForm = false;
    }

    /**
     * Transfer params from search form to table.
     */
    public getSearchParams(event: FormGroup): void {
        if (event) {
            this.searchPersonEventEmmiter.emit(event);
        }

        this.showAddForm = false;
    }

    /**
     * Sets the person who was selected in grid and goes to the tabs page.
     *
     * @param person Person selected.
     */
    public personSelected(event: Person): void {
        this.selectedPerson.setPerson(event);
        this.router.navigate(['/' + APP_CONSTANTS.routes.person]);
    }

}
