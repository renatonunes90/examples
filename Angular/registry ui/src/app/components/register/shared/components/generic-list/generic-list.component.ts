import { AfterViewInit, Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../../../../../core/services/authentication.service';
import { ConfirmDialogComponent } from '../../../../../shared/components/confirm-dialog/confirm.dialog.component';
import { GENERIC_LIST_EDIT_CONSTANTS } from '../generic-list-edit.constants';
import { GenericObject } from '../../models/generic-object.model';
import { GenericService } from '../../services/generic-service.service';

/**
 * Component that contains the list of objects.
 */
@Component({
    selector: 'app-generic-list',
    templateUrl: 'generic-list.view.component.html',
    styleUrls: ['generic-list.style.component.css'],
})
export class GenericListComponent<T extends GenericObject> implements AfterViewInit {

    public constants = GENERIC_LIST_EDIT_CONSTANTS;

    /**
     * Executes communication with backend.
     */
    @Input() service: GenericService<T>;

    /**
     * Object used when user clicks on the add button to pass information to form.
     */
    @Input() emptyObject: T;

    /**
     * Label used in component.
     */
    @Input() label: string;

    /**
     * Plural form of the label used in component.
     */
    @Input() pluralFormLabel: string;

    /**
     * List of the table.
     */
    public data: T[];

    /**
     * Variable used to indicate which object will be edited.
     */
    @Output() emitter = new EventEmitter<GenericObject>();

    constructor(private authenticationService: AuthenticationService,
        private toastr: ToastrService,
        private modalService: NgbModal) {
    }

    ngAfterViewInit() {
        // load all objects
        this.refresh();
    }

    /**
     * Calls service to get the object list.
     */
    public refresh(): void {
        this.service.getAll()
            .subscribe(
                (resp) => {
                    this.data = resp;
                });

    }

    /**
     * Emits event indicating the user will edit a new object.
     */
    public addClicked(): void {
        this.emptyObject.setId(0);
        this.emptyObject.setText('');

        this.emitter.emit(this.emptyObject);
    }

    /**
     * Emits event indicating the user will edit an object.
     */
    public editClicked(object: T): void {
        this.emitter.emit(object);
    }

    /**
     * Requests removal of an object.
     *
     * @param object Object to be removed.
     */
    public removeClicked(object: T): void {
        const mRef = this.modalService.open(ConfirmDialogComponent);
        mRef.componentInstance.text = this.constants.confirmDelete;
        mRef.componentInstance.passEntry
            .subscribe(
                (response: boolean) => {
                    if (response && object !== undefined) {
                        this.service.delete(object)
                            .subscribe(
                                (resp) => {
                                    this.toastr.success(this.constants.successDelete, '');
                                    this.refresh();
                                });
                    }
                });
    }
}
