import { Component, EventEmitter, Input, AfterViewInit, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { PERSON_CONSTANTS } from '../person.constants';

/**
 * Component that implements a person search form.
 */
@Component({
    selector: 'app-search-person-form',
    templateUrl: 'search-person-form.view.component.html',
    styleUrls: ['search-person-form.style.component.css']
})
export class SearchPersonFormComponent implements AfterViewInit, OnInit {

    public constants = PERSON_CONSTANTS;

    @Input()
    public defaultName: string;

    @Output()
    public searchEmitter = new EventEmitter<FormGroup>();

    public personForm: FormGroup;

    get cpfInput() { return this.personForm.get('cpf'); }
    get nameInput() { return this.personForm.get('name'); }

    constructor() {
    }

    ngOnInit() {
        this.personForm = new FormGroup({
            'cpf': new FormControl(''),
            'name': new FormControl(this.defaultName ? this.defaultName : '')
        });
    }

    ngAfterViewInit() {
        // if has pre-inputed name, executes the search
        if (this.defaultName) {
            this.search();
        }
    }

    /**
     * Clear the fields of the form.
     */
    public clearFields() {
        this.cpfInput.setValue('');
        this.nameInput.setValue('');
    }

    /**
     * Fires event to search for people.
     */
    public search(): void {
        this.searchEmitter.emit(this.personForm);
    }
}
