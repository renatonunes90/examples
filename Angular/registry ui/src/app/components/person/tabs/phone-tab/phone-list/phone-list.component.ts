import { AfterViewInit, Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { ConfirmDialogComponent } from '../../../../../shared/components/confirm-dialog/confirm.dialog.component';
import { GenericFunctions } from '../../../../person/shared/utils/generic-functions';
import { Person } from '../../../../../models/person.model';
import { Phone } from '../../../../../models/persondata/phone.model';
import { PHONE_TAB_CONSTANTS } from '../phone-tab.constants';
import { PhoneService } from '../../../../../services/persondata/phone.service';
import { PhoneType } from '../../../../../models/domain/phone-type.model';

/**
 * Component that contains the list of phone numbers.
 */
@Component({
    selector: 'app-phone-list',
    templateUrl: 'phone-list.view.component.html',
    styleUrls: ['phone-list.style.component.css']
})
export class PhoneListComponent implements AfterViewInit {

    public constants = PHONE_TAB_CONSTANTS;

    public genFunc = GenericFunctions.getInstance();

    /**
     * Phone list of the table.
     */
    public data: Phone[];

    /**
     * Current person.
     */
    @Input() person: Person;

    /**
     * Variable used to indicate which phone number will be edited.
     */
    @Output() phone = new EventEmitter<Phone>();

    constructor(private phoneService: PhoneService,
        private toastr: ToastrService,
        private modalService: NgbModal) {
    }

    ngAfterViewInit() {
        // load all person phone numbers
        this.refresh();
    }

    /**
     * Calls phone service to get the person's phone numbers list.
     */
    public refresh(): void {
        if (this.person !== undefined) {
            this.phoneService.search(this.person.personId)
                .subscribe(
                    (resp) => {
                        this.data = resp.body.content;
                    });
        }
    }

    /**
     * Emits event indicating the user will edit a new phone number.
     */
    public addClicked(): void {
        const personId = this.person !== undefined ? this.person.personId : -1;
        const newPhone = Object.create(null);
        newPhone.phoneId = -1;
        newPhone.phoneType = new PhoneType(null);
        newPhone.personId = personId;

        this.phone.emit(newPhone);
    }

    /**
     * Emits event indicating the user will edit a phone number.
     */
    public editClicked(updatedPhone: Phone): void {
        this.phone.emit(updatedPhone);
    }

    /**
     * Requests removal of a phone number.
     *
     * @param phone Phone number to be removed.
     */
    public removeClicked(phone: Phone): void {
        const mRef = this.modalService.open(ConfirmDialogComponent);
        mRef.componentInstance.text = this.constants.confirmDelete;
        mRef.componentInstance.passEntry
            .subscribe(
                (response: boolean) => {
                    if (response && phone !== undefined) {
                        this.phoneService.delete(phone)
                            .subscribe(
                                (resp) => {
                                    this.toastr.success(this.constants.successDelete, '');
                                    this.refresh();
                                });
                    }
                });
    }
}
