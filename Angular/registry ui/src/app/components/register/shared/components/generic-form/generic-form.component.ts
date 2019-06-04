import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { GENERIC_LIST_EDIT_CONSTANTS } from '../generic-list-edit.constants';
import { GenericObject } from '../../models/generic-object.model';
import { GenericService } from '../../services/generic-service.service';

/**
 * Component that contains the form to add or edit a register.
 */
@Component({
    selector: 'app-generic-form',
    templateUrl: 'generic-form.view.component.html',
    styleUrls: ['generic-form.style.component.css'],
})
export class GenericFormComponent<T extends GenericObject> implements OnInit {

    public constants = GENERIC_LIST_EDIT_CONSTANTS;

    /**
     * Current object.
     */
    @Input() object: T;

    /**
     * Executes communication with backend.
     */
    @Input() service: GenericService<T>;

    /**
     * Label used in component.
     */
    @Input() label: string;

    /**
     * Event output to indicates that the list has to be shown.
     */
    @Output() back = new EventEmitter<boolean>();

    public form: FormGroup;

    constructor(private toastr: ToastrService) {
    }

    get textInput() { return this.form.get('text'); }

    ngOnInit() {
        this.form = new FormGroup({
            'text': new FormControl(this.object.getText(), [
                Validators.required
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
     * Requests the save of object information and closes the form.
     */
    public onSubmit(): void {
        let callbackSuccess = function (response: HttpResponse<T>) {
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

        if (this.form.valid) {

            // sets the text in the object to save
            this.object.setText(this.textInput.value);

            if (this.object.getId() > 0) {
                this.service.update(this.object).subscribe(resp => callbackSuccess(resp), error => callbackError(error));
            } else {
                this.service.create(this.object).subscribe(resp => callbackSuccess(resp), error => callbackError(error));
            }
        } else {
            this.textInput.markAsTouched();
        }
    }
}
