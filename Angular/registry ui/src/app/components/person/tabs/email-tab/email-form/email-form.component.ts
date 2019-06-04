import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { EMAIL_TAB_CONSTANTS } from '../email-tab.constants';
import { Email } from '../../../../../models/persondata/email.model';
import { EmailService } from '../../../../../services/persondata/email.service';

/**
 * Component that contains the form to add or edit an e-mail.
 */
@Component({
    selector: 'app-email-form',
    templateUrl: 'email-form.view.component.html',
    styleUrls: ['email-form.style.component.css'],
})
export class EmailFormComponent implements OnInit {

    public constants = EMAIL_TAB_CONSTANTS;

    /**
     * Current email.
     */
    @Input() email: Email;

    @Output() back = new EventEmitter<boolean>();

    public emailForm: FormGroup;

    constructor(private emailService: EmailService,
        private toastr: ToastrService) {
    }

    get emailInput() { return this.emailForm.get('email'); }

    ngOnInit() {
        this.emailForm = new FormGroup({
            'email': new FormControl(this.email.email, [
                Validators.required,
                Validators.pattern('^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$')
            ])
        });
    }

    /**
     * Emits event indicanting the operation was cancelled.
     */
    public cancelClicked(): void {
        this.back.emit(false);
    }

    /**
     * Requests the save of e-mail information and closes the form.
     */
    public onSubmit(): void {
        let callbackSuccess = function (response: HttpResponse<Email>) {
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

        if (this.emailForm.valid) {
            this.email.email = this.emailInput.value;

            if (this.email.emailId > 0) {
                this.emailService.update(this.email).subscribe(resp => callbackSuccess(resp), error => callbackError(error));
            } else {
                this.emailService.create(this.email).subscribe(resp => callbackSuccess(resp), error => callbackError(error));
            }
        } else {
            // show which fields invalidated the form
            this.emailInput.markAsTouched();
        }
    }
}
