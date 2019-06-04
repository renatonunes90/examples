import { Component } from '@angular/core';
import { GenericListEditComponent } from '../shared/components/generic-list-edit.component';
import { GenericService } from '../shared/services/generic-service.service';
import { PHONE_TYPE_CONSTANTS } from './phone-type.constants';
import { PhoneType } from '../../../models/domain/phone-type.model';
import { PhoneTypeService } from '../../../services/domain/phone-type.service';

/**
 * Component that controlls the view of the phone type page.
 */
@Component({
    selector: 'app-phone-type',
    templateUrl: '../shared/components/generic-list-edit.view.component.html'
})
export class PhoneTypeComponent extends GenericListEditComponent<PhoneType> {

    public constants = PHONE_TYPE_CONSTANTS;

    constructor(private phoneTypeService: PhoneTypeService) {
        super();
    }

    public getService(): GenericService<PhoneType> {
        return this.phoneTypeService;
    }

    public getEmptyObject(): PhoneType {
        return new PhoneType(null);
    }

    public getLabel(): string {
        return this.constants.phoneType;
    }

    public getPluralFormLabel(): string {
        return this.constants.pluralFormPhoneType;
    }

}
