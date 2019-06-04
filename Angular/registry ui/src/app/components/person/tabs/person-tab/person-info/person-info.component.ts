import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { formatDate } from '@angular/common';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { ConfirmDialogComponent } from '../../../../../shared/components/confirm-dialog/confirm.dialog.component';
import { APP_CONSTANTS } from '../../../../../app.constants';
import { Person } from '../../../../../models/person.model';
import { PERSON_TAB_CONSTANTS } from '../person-tab.constants';
import { PersonService } from '../../../../../services/person.service';

/**
 * Component with the main info of person.
 */
@Component({
    selector: 'app-person-info',
    templateUrl: 'person-info.view.component.html'
})
export class PersonInfoComponent implements OnInit {

    public constants = PERSON_TAB_CONSTANTS;

    /**
     * Current person.
     */
    @Input() person: Person;

    /**
     * Variable used to indicate that person will be edited.
     */
    @Output() edit = new EventEmitter<boolean>();

    /**
     * Variable used to don't change person original values.
     */
    public translatedPerson: Person;

    public birthDateConverted: string;
    public breed: string;
    public maritalStatus: string;

    constructor(private personService: PersonService,
        private toastr: ToastrService,
        private modalService: NgbModal) {
    }

    ngOnInit() {
        this.translatedPerson = Object.create(this.person);

        this.translatedPerson.genderType = this.person.genderType === 'F' ? this.constants.female : this.constants.male;

        this.translatedPerson.name = this.verifyValid(this.person.name);
        this.translatedPerson.cpf = this.verifyValid(this.person.cpf);
        this.translatedPerson.birthCountry = this.verifyValid(this.person.birthCountry);
        this.translatedPerson.birthCity = this.verifyValid(this.person.birthCity);

        this.translatedPerson.fatherName = this.verifyValid(this.person.fatherName);
        this.translatedPerson.motherName = this.verifyValid(this.person.motherName);

        this.birthDateConverted = formatDate(new Date(this.person.birthDate), 'dd/MM/yyyy', 'en-US', 'UTC');
        this.breed = this.person.breed !== null ? this.person.breed.breedText : '-';
        this.maritalStatus = this.person.maritalStatus !== null ? this.person.maritalStatus.maritalStatusText : '-';
    }

    /**
     * Emits event indicating the person will be edited.
     */
    public editClicked() {
        this.edit.emit(true);
    }

    /**
     * Requests removal of a person.
     */
    public removeClicked() {
        let callback = function (response: HttpResponse<String>) {
            this.toastr.success(this.constants.successDelete, '');
            this.router.navigate(['/' + APP_CONSTANTS.routes.searchPerson]);
        };
        callback = callback.bind(this);

        const mRef = this.modalService.open(ConfirmDialogComponent);
        mRef.componentInstance.text = this.constants.confirmDelete;
        mRef.componentInstance.passEntry.subscribe((response: boolean) => {
            if (response && this.person !== undefined) {
                this.personService.delete(this.person).subscribe(resp => callback(resp), resp => callback(resp));
            }
        });
    }

    private verifyValid(param): string {
        return param === '' || param === null ? '-' : param;
    }

}
