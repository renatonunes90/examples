import { Component } from '@angular/core';
import { MaritalStatus } from '../../../models/domain/marital-status.model';
import { MARITAL_STATUS_CONSTANTS } from './marital-status.constants';
import { MaritalStatusService } from '../../../services/domain/marital-status.service';
import { GenericListEditComponent } from '../shared/components/generic-list-edit.component';
import { GenericService } from '../shared/services/generic-service.service';

/**
 * Component that controlls the view of the marital status page.
 */
@Component({
    selector: 'app-marital-status',
    templateUrl: '../shared/components/generic-list-edit.view.component.html'
})
export class MaritalStatusComponent extends GenericListEditComponent<MaritalStatus> {

    public constants = MARITAL_STATUS_CONSTANTS;

    constructor(private maritalStatusService: MaritalStatusService) {
        super();
    }

    public getService(): GenericService<MaritalStatus> {
        return this.maritalStatusService;
    }

    public getEmptyObject(): MaritalStatus {
        return new MaritalStatus(null);
    }

    public getLabel(): string {
        return this.constants.maritalStatus;
    }

    public getPluralFormLabel(): string {
        return this.constants.pluralFormMaritalStatus;
    }

}
