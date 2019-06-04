import { Component } from '@angular/core';
import { CurrentPersonService } from '../../shared/services/current-person.service';
import { Person } from '../../../../models/person.model';
import { Phone } from '../../../../models/persondata/phone.model';

/**
 * Component that controlls the view of the phone tab.
 */
@Component({
    selector: 'app-phone-tab',
    templateUrl: 'phone-tab.view.component.html'
})
export class PhoneTabComponent {

    /**
     * Current person.
     */
    public person: Person;

    /**
     * Current phone.
     */
    public phone: Phone;

    /**
     * Flag indicating if the form must be rendered.
     */
    public showForm: boolean;

    constructor(private selectedPerson: CurrentPersonService) {
        this.person = this.selectedPerson.getPerson();
    }

    /**
     * Cancels the phone number edition and shows the list.
     */
    public backList(): void {
        this.showForm = false;
    }

    /**
     * Stores the current phone number and shows the form.
     */
    public editPhone(phone: Phone): void {
        this.phone = phone;
        this.showForm = true;
    }

}
