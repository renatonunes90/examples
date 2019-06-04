import { Injectable } from '@angular/core';
import { Person } from '../../../../models/person.model';

@Injectable()
export class CurrentPersonService {
    subject: Person;

    setPerson(person: Person) {
        this.subject = person;
    }

    getPerson(): Person {
        return this.subject;
    }
}
