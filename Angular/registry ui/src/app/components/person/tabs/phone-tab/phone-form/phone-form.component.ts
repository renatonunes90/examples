import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { Phone } from '../../../../../models/persondata/phone.model';
import { PHONE_TAB_CONSTANTS } from '../phone-tab.constants';
import { PhoneService } from '../../../../../services/persondata/phone.service';
import { PhoneType } from '../../../../../models/domain/phone-type.model';
import { PhoneTypeService } from '../../../../../services/domain/phone-type.service';

/**
 * Component that contains the form to add or edit a phone number.
 */
@Component({
    selector: 'app-phone-form',
    templateUrl: 'phone-form.view.component.html',
    styleUrls: ['phone-form.style.component.css']
})
export class PhoneFormComponent implements OnInit {

    public constants = PHONE_TAB_CONSTANTS;

    /**
     * Current phone number.
     */
    @Input() phone: Phone;

    @Output() back = new EventEmitter<boolean>();

    public phoneForm: FormGroup;

    public phoneTypes: PhoneType[];

    constructor(private phoneService: PhoneService,
        private phoneTypeService: PhoneTypeService,
        private toastr: ToastrService) {
    }

    get ddiInput() { return this.phoneForm.get('ddi'); }
    get dddInput() { return this.phoneForm.get('ddd'); }
    get numberInput() { return this.phoneForm.get('number'); }
    get phoneTypeInput() { return this.phoneForm.get('phoneType'); }

    ngOnInit() {
        this.phoneForm = new FormGroup({
            'ddi': new FormControl(this.phone.ddi),
            'ddd': new FormControl(this.phone.ddd),
            'number': new FormControl(this.phone.number, [
                Validators.required
            ]),
            'phoneType': new FormControl(this.phone.phoneType ? this.phone.phoneType.phoneTypeId : '', [
                Validators.required
            ])
        });

        this.phoneTypeService.getAll().subscribe(response => {
            this.phoneTypes = response;
        });
    }

    /**
     * Emits event indicanting the operation was cancelled.
     */
    public cancelClicked(): void {
        this.back.emit(false);
    }

    /**
     * Requests the save of phone number information and closes the form.
     */
    public onSubmit(): void {
        let callbackSuccess = function (response: HttpResponse<Phone>) {
            this.toastr.success(this.constants.successFeedback);
            this.back.emit(true);
        };
        callbackSuccess = callbackSuccess.bind(this);

        let callbackError = function (response) {
            const error = response.error !== undefined && response.error.length > 0 ? response.error[0].userMessage
                : this.constants.errorFeedback;
            this.toastr.error(error);
        };
        callbackError = callbackError.bind(this);

        if (this.phoneForm.valid) {
            this.phone.ddi = this.ddiInput.value;
            this.phone.ddd = this.dddInput.value;
            this.phone.number = this.numberInput.value;
            this.phone.phoneType.phoneTypeId = this.phoneTypeInput.value;

            if (this.phone.phoneId > 0) {
                this.phoneService.update(this.phone).subscribe(resp => callbackSuccess(resp), error => callbackError(error));
            } else {
                this.phoneService.create(this.phone).subscribe(resp => callbackSuccess(resp), error => callbackError(error));
            }
        } else {
            // show which fields invalidated the form
            this.numberInput.markAsTouched();
            this.phoneTypeInput.markAsTouched();
        }
    }
}
