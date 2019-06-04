import { AfterViewInit, Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../../../../../core/services/authentication.service';
import { ConfirmDialogComponent } from '../../../../../shared/components/confirm-dialog/confirm.dialog.component';
import { EMAIL_TAB_CONSTANTS } from '../email-tab.constants';
import { Email } from '../../../../../models/persondata/email.model';
import { EmailService } from '../../../../../services/persondata/email.service';
import { GenericFunctions } from '../../../../person/shared/utils/generic-functions';
import { Person } from '../../../../../models/person.model';

/**
 * Component that contains the list of emails.
 */
@Component({
    selector: 'app-email-list',
    templateUrl: 'email-list.view.component.html',
    styleUrls: ['email-list.style.component.css'],
})
export class EmailListComponent implements AfterViewInit {

    public constants = EMAIL_TAB_CONSTANTS;

    public genFunc = GenericFunctions.getInstance();

    /**
     * E-mails list of the table.
     */
    public data: Email[];

    /**
     * Current person.
     */
    @Input() person: Person;

    /**
     * Variable used to indicate which e-mail will be edited.
     */
    @Output() email = new EventEmitter<Email>();

    constructor(private emailService: EmailService,
        private authenticationService: AuthenticationService,
        private toastr: ToastrService,
        private modalService: NgbModal) {
    }

    ngAfterViewInit() {
        // load all person e-mails
        this.refresh();
    }

    /**
     * Calls e-mail service to get the person's e-mails list.
     */
    public refresh(): void {
        if (this.person !== undefined) {
            this.emailService.search(this.person.personId)
                .subscribe(
                    (resp) => {
                        this.data = resp.body.content;
                    });
        }
    }

    /**
     * Emits event indicating the user will edit a new e-mail.
     */
    public addClicked(): void {
        const personId = this.person !== undefined ? this.person.personId : -1;
        this.email.emit({
            'emailId': -1, 'personId': personId, 'email': ''
        });
    }

    /**
     * Emits event indicating the user will edit an e-mail.
     */
    public editClicked(email: Email): void {
        this.email.emit(email);
    }

    /**
     * Requests removal of an e-mail.
     *
     * @param email E-mail to be removed.
     */
    public removeClicked(email: Email): void {
        const mRef = this.modalService.open(ConfirmDialogComponent);
        mRef.componentInstance.text = this.constants.confirmDelete;
        mRef.componentInstance.passEntry
            .subscribe(
                (response: boolean) => {
                    if (response && email !== undefined) {
                        this.emailService.delete(email)
                            .subscribe(
                                (resp) => {
                                    this.toastr.success(this.constants.successDelete, '');
                                    this.refresh();
                                });
                    }
                });
    }
}
