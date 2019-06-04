import { Component } from '@angular/core';
import { Breed } from '../../../models/domain/breed.model';
import { BREED_CONSTANTS } from './breed.constants';
import { BreedService } from '../../../services/domain/breed.service';
import { GenericListEditComponent } from '../shared/components/generic-list-edit.component';
import { GenericService } from '../shared/services/generic-service.service';

/**
 * Component that controlls the view of the breed page.
 */
@Component({
    selector: 'app-breed',
    templateUrl: '../shared/components/generic-list-edit.view.component.html'
})
export class BreedComponent extends GenericListEditComponent<Breed> {

    public constants = BREED_CONSTANTS;

    constructor(private breedService: BreedService) {
        super();
    }

    public getService(): GenericService<Breed> {
        return this.breedService;
    }

    public getEmptyObject(): Breed {
        return new Breed(null);
    }

    public getLabel(): string {
        return this.constants.breed;
    }

    public getPluralFormLabel(): string {
        return this.constants.pluralFormBreed;
    }

}
