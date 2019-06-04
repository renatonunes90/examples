import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { PERSON_CONSTANTS } from '../person.constants';
import { Person } from '../../../models/person.model';
import { PersonService } from '../../../services/person.service';

@Component({
    selector: 'app-person-table',
    templateUrl: 'person-table.view.component.html',
    styleUrls: ['person-table.style.component.css'],
})
export class PersonTableComponent implements OnInit {

    public constants = PERSON_CONSTANTS;

    @Input() public showEditBtn: boolean;
    @Input() public subscriber: Observable<any>;

    @Output()
    public personEmitter = new EventEmitter<Person>();

    public data = new Array<Person>();

    /**
     * Structure necessary to manage the paged search.
     */
    public page = {
        begin: 0,
        end: 0,
        size: 5,
        totalElements: 0,
        pageNumber: 0
    };

    /**
     * Filters of person.
     */
    private searchData = {
        code: 0,
        cpf: '',
        name: ''
    };

    constructor(private personService: PersonService) {
    }

    ngOnInit() {
        this.subscriber.subscribe((form: FormGroup) => this.refresh(form));
    }

    /**
     * Populate the table with new data based on the page number
     */
    private load(): void {
        this.personService.search(this.searchData.code, this.searchData.cpf, this.searchData.name,
            '', this.page).subscribe(response => {
                this.page.pageNumber = response.body.number;
                this.page.size = response.body.size;
                this.page.totalElements = response.body.totalElements;

                this.data = response.body.content;

                // calculates the elements that are being showed
                this.page.begin = response.body.numberOfElements > 0 ? (response.body.pageable.offset + 1) : 0;
                this.page.end = response.body.numberOfElements > 0 ? (this.page.begin - 1) + response.body.numberOfElements : 0;
            });
    }

    /**
     * Refreshes the person filters.
     */
    public refresh({ value }) {
        this.searchData.cpf = value.cpf === '' ? null : value.cpf;
        this.searchData.name = value.name !== '' ? value.name : null;
        this.page.pageNumber = 0;

        this.load();
    }

    /**
     * Manages the previous page button action.
     */
    public previousPage() {
        this.page.pageNumber--;
        this.load();
    }

    /**
     * Manages the next page button action.
     */
    public nextPage() {
        this.page.pageNumber++;
        this.load();
    }

    /**
     * Fires event of person selected.
     *
     * @param person Person selected.
     */
    public personSelected(person: Person): void {
        this.personEmitter.emit(person);
    }

}
