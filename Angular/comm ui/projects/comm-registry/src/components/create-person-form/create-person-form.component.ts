import { Component, EventEmitter, OnInit, Output, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RegistryService } from '../../services/registry.service';
import { COMM_REGISTRY_CONSTANTS } from '../../lib/comm-registry.constants';
import { PersonInfo } from '../../models/person-info.model';

@Component({
    selector: 'lib-create-person-form',
    templateUrl: './create-person-form.view.component.html',
    styleUrls: ['../../lib/comm-registry.style.scss']
})
export class CreateAccountFormComponent implements OnInit {

    public constants = COMM_REGISTRY_CONSTANTS;

    public createPersonForm: FormGroup;

    public personInfo = new PersonInfo();
    public errorMessage = '';

    /**
     * Event indicating the registration was successfully.
     */
    @Output() goToSuccessPage = new EventEmitter<Object>();

    constructor(private accountService: RegistryService) {
    }

    get cpfInput() { return this.createPersonForm.get('cpf'); }
    get nameInput() { return this.createPersonForm.get('name'); }

    ngOnInit() {
        this.createPersonForm = new FormGroup({
            'name': new FormControl('', [
                Validators.required
            ]),
            'cpf': new FormControl('', [
                Validators.required
            ])
        });
        this.createPersonForm.setValidators([this.checkEmptyName, this.checkValidName]);
    }

    private checkEmptyName(group: FormGroup) {
        const name = group.controls.name.value;
        return name.toString().trim() ? null : { nameIsEmpty: true };
    }

    private checkValidName(group: FormGroup) {
        let name = group.controls.name.value;
        name = name.trim();

        const nameAsArray = name.split(' ');
        let isValid = nameAsArray.length > 1;

        const invalidNames = ['E', 'DA', 'DE', 'DO', 'DAS', 'DOS'];

        if (nameAsArray.length === 2) {
            if (invalidNames.indexOf(nameAsArray[0].toUpperCase()) !== -1) {
                isValid = false;
            } else if (invalidNames.indexOf(nameAsArray[1].toUpperCase()) !== -1) {
                isValid = false;
            }
        }

        return isValid ? null : { nameIsInvalid: true };
    }

    public doRegistry(): void {

        if (this.createPersonForm.valid) {

            this.personInfo.cpf = this.cpfInput.value;
            this.personInfo.name = this.nameInput.value;

            this.accountService.createAccount(this.personInfo)
                .subscribe(
                    (resp) => {
                        this.goToSuccessPage.emit(resp.body);
                    },
                    (error) => {
                        console.log(error);
                        if (!error.error && !error.error.error_description) {
                            this.errorMessage = error.error.error_description;
                        } else if (error.error.length > 0 && error.error[0].userMessage) {
                            this.errorMessage = error.error[0].userMessage;
                        } else {
                            this.errorMessage = this.constants.serverGenericError;
                        }
                    });

        } else {

            this.cpfInput.markAsTouched();
            this.nameInput.markAsTouched();

            if (this.checkEmptyName(this.createPersonForm)) {
                this.errorMessage = this.constants.nameIsEmpty;
            } else if (this.checkValidName(this.createPersonForm)) {
                this.errorMessage = this.constants.nameIsInvalid;
            } else {
                this.errorMessage = this.constants.formGenericError;
            }
        }
    }
}
