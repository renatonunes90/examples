import { OnInit, Component } from '@angular/core';
import { AuthenticationService } from '../../../../core/services/authentication.service';
import { CurrentPersonService } from '../../shared/services/current-person.service';
import { environment } from '../../../../config/environments/environment';
import { Person } from '../../../../models/person.model';

/**
 * Component that controlls the view of the main tab.
 */
@Component({
    selector: 'app-person-tab',
    templateUrl: 'person-tab.view.component.html'
})
export class PersonTabComponent implements OnInit {

    /**
    * Current person.
    */
    public person: Person;

    /**
     * Flag indicating if the form must be rendered.
     */
    public showForm: boolean;

    constructor(private authenticationService: AuthenticationService,
        private selectedPerson: CurrentPersonService) {
    }

    ngOnInit() {
        this.person = this.selectedPerson.getPerson();
    }

    /**
     * Cancels the person edition and shows the info.
     */
    public backList(event: Person): void {
        if (event) {
            this.selectedPerson.setPerson(event);
            this.person = event;
        } else {
            this.person = this.selectedPerson.getPerson();
        }
        this.showForm = false;
    }

    /**
     * Shows the form.
     */
    public editPerson(): void {
        this.person = this.selectedPerson.getPerson();
        this.person.projectCd = environment.projectCode;
        this.person.ipCd = window.location.origin;
        this.person.operatorCd = this.authenticationService.getOperatorCd();

        this.showForm = true;
    }

}
