import { Component, Input, Output, EventEmitter } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CONSTANTS } from './confirm.dialog.constants';

@Component({
    selector: 'app-confirm-dialog',
    templateUrl: './confirm.dialog.view.component.html'
})
export class ConfirmDialogComponent {

    public constants = CONSTANTS;

    @Input() public text: string;
    @Output() passEntry: EventEmitter<boolean> = new EventEmitter();

    constructor(public activeModal: NgbActiveModal) { }

    confirm() {
        this.passEntry.emit(true);
        this.activeModal.dismiss();
    }
}
