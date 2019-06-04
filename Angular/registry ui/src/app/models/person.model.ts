import { Breed } from './domain/breed.model';
import { MaritalStatus } from './domain/marital-status.model';

/**
 * An object mapping an instance of Person.
 */
export class Person {
    personId: number;
    genderType: string;
    birthDate: string;
    name: string;
    cpf: string;
    birthCountry: string;
    breed: Breed;
    maritalStatus: MaritalStatus;
    birthCity: string;
    fatherName: string;
    motherName: string;
    active: string;

    constructor() {
        this.personId = -1;
        this.maritalStatus = new MaritalStatus(null);
        this.breed = new Breed(null);
    }
}
