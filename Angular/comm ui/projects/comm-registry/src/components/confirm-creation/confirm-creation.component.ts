import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { RegistryService } from '../../services/registry.service';
import { COMM_REGISTRY_CONSTANTS } from '../../lib/comm-registry.constants';
import { PersonInfo } from '../../models/person-info.model';

@Component({
    selector: 'lib-confirm-creation',
    templateUrl: './confirm-creation.view.component.html',
    styleUrls: ['../../lib/comm-registry.style.scss']
})
export class ConfirmCreationComponent implements OnInit {

    public constants = COMM_REGISTRY_CONSTANTS;

    @Input() personId: string;

    /**
     * Event indicating the person was created.
     */
    @Output() personCreated = new EventEmitter<Object>();

    /**
     * Event indicating the registration was successfully.
     */
    @Output() goToForm = new EventEmitter<boolean>();

    public accountCredentials: PersonInfo;

    public message = '';
    public titleMessage = '';

    constructor(private registryService: RegistryService) {
    }

    ngOnInit() {

        // verifies if need to confirmate account or show message of e-mail sent
        this.message = this.constants.createSuccessMessage;
        this.titleMessage = this.constants.congratulationsMsg;
    }

    /**
     * Confirm and emits person created.
     */
    public confirm(): void {

        this.registryService.getAccount(this.personId)
            .subscribe(
                (resp) => {
                    this.personCreated.emit(resp.body);
                },
                (error) => {
                    console.log(error);
                });

    }

}
