import { Component } from '@angular/core';
import { CurrentPersonService } from '../../shared/services/current-person.service';
import { Email } from '../../../../models/persondata/email.model';
import { Person } from '../../../../models/person.model';

/**
 * Component that controlls the view of the email tab.
 */
@Component({
    selector: 'app-email-tab',
    templateUrl: 'email-tab.view.component.html'
})
export class EmailTabComponent {

    /**
     * Current person.
     */
    public person: Person;

    /**
     * Current e-mail.
     */
    public email: Email;

    /**
     * Flag indicating if the form must be rendered.
     */
    public showForm: boolean;

    constructor(private selectedPerson: CurrentPersonService) {
        this.person = this.selectedPerson.getPerson();
    }

    /**
     * Cancels the e-mail edition and shows the list.
     */
    public backList(): void {
        this.showForm = false;
    }

    /**
     * Stores the current e-mail and shows the form.
     */
    public editEmail(email: Email): void {
        this.email = email;
        this.showForm = true;
    }

}
