import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { formatDate } from '@angular/common';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { Breed } from '../../../../../models/domain/breed.model';
import { BreedService } from '../../../../../services/domain/breed.service';
import { MaritalStatus } from '../../../../../models/domain/marital-status.model';
import { MaritalStatusService } from '../../../../../services/domain/marital-status.service';
import { PERSON_TAB_CONSTANTS } from '../person-tab.constants';
import { Person } from '../../../../../models/person.model';
import { PersonService } from '../../../../../services/person.service';

/**
 * Component that contains the form to add or edit a person.
 */
@Component({
    selector: 'app-person-form',
    templateUrl: 'person-form.view.component.html',
    styleUrls: ['person-form.style.component.css']
})
export class PersonFormComponent implements OnInit {

    public constants = PERSON_TAB_CONSTANTS;

    /**
     * Current person.
     */
    @Input() person: Person;

    @Output() back = new EventEmitter<boolean>();

    public personForm: FormGroup;

    public breeds: Breed[];
    public maritalStatus: MaritalStatus[];

    constructor(private breedService: BreedService,
        private maritalStatusService: MaritalStatusService,
        private personService: PersonService,
        private toastr: ToastrService) {
    }

    get genderTypeInput() { return this.personForm.get('genderType'); }
    get birthDateInput() { return this.personForm.get('birthDate'); }
    get nameInput() { return this.personForm.get('name'); }
    get cpfInput() { return this.personForm.get('cpf'); }
    get birthCountryInput() { return this.personForm.get('birthCountry'); }
    get breedInput() { return this.personForm.get('breed'); }
    get maritalStatusInput() { return this.personForm.get('maritalStatus'); }
    get birthCityInput() { return this.personForm.get('birthCity'); }
    get fatherNameInput() { return this.personForm.get('fatherName'); }
    get motherNameInput() { return this.personForm.get('motherName'); }

    ngOnInit() {
        this.personForm = new FormGroup({
            'name': new FormControl(this.person.name, [
                Validators.required
            ]),
            'genderType': new FormControl(this.person.genderType),
            'birthDate': new FormControl(this.person.birthDate ? formatDate(this.person.birthDate, 'yyyy-MM-dd', 'en-US') : '', [
                Validators.required
            ]),
            'cpf': new FormControl(this.person.cpf),
            'birthCountry': new FormControl(this.person.birthCountry),
            'breed': new FormControl(this.person.breed ? this.person.breed.breedId : ''),
            'maritalStatus': new FormControl(this.person.maritalStatus ? this.person.maritalStatus.maritalStatusId : ''),
            'birthCity': new FormControl(this.person.birthCity),
            'fatherName': new FormControl(this.person.fatherName),
            'motherName': new FormControl(this.person.motherName)
        });

        this.breedService.getAll().subscribe(response => {
            this.breeds = response;
        });

        this.maritalStatusService.getAll().subscribe(response => {
            this.maritalStatus = response;
        });
    }

    /**
     * Emits event indicanting the operation was cancelled.
     */
    public cancelClicked(): void {
        this.back.emit(null);
    }

    /**
     * Validator to guarantee only numbers on some inputs.
     */
    public onlyNumber() {
        const numberPattern = new RegExp('[0-9]');

        const evt = window.event as KeyboardEvent;

        // stops edition if it's not a number
        if (numberPattern.exec(evt.key) === null) {
            evt.preventDefault();
        }
    }

    /**
     * Requests the save of person information and closes the form.
     */
    public onSubmit(): void {

        let callbackSuccess = function (response: HttpResponse<Person>) {
            this.toastr.success(this.constants.successFeedback);
            this.back.emit(response.body);
        };
        callbackSuccess = callbackSuccess.bind(this);

        let callbackError = function (response) {
            const error = response.error !== undefined && response.error.length > 0 ? response.error[0].userMessage
                : this.constants.errorFeedback;
            this.toastr.error(error);
        };
        callbackError = callbackError.bind(this);

        // getting form values
        if (this.personForm.valid) {
            this.person.name = this.nameInput.value;
            this.person.genderType = this.genderTypeInput.value;
            this.person.cpf = this.cpfInput.value;
            this.person.birthDate = this.birthDateInput.value;
            this.person.birthCountry = this.birthCountryInput.value;
            this.person.birthCity = this.birthCityInput.value;
            this.person.fatherName = this.fatherNameInput.value;
            this.person.motherName = this.motherNameInput.value;

            if (!this.person.maritalStatus) {
                this.person.maritalStatus = new MaritalStatus(null);
            }
            this.person.maritalStatus.maritalStatusId = this.maritalStatusInput.value;

            if (!this.person.breed) {
                this.person.breed = new Breed(null);
            }
            this.person.breed.breedId = this.breedInput.value;

            if (this.person.personId > 0) {
                this.personService.update(this.person).subscribe(resp => callbackSuccess(resp), error => callbackError(error));
            } else {
                this.personService.create(this.person).subscribe(resp => callbackSuccess(resp), error => callbackError(error));
            }
        } else {
            // show which fields invalidated the form
            this.nameInput.markAsTouched();
            this.genderTypeInput.markAsTouched();
            this.birthDateInput.markAsTouched();
        }
    }
}
