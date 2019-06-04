import { Component } from '@angular/core';
import { GenericObject } from '../models/generic-object.model';
import { GenericService } from '../services/generic-service.service';

/**
 * Component that controlls the view of the marital status page.
 */
@Component({
    selector: 'app-generic-list-edit'
})
export abstract class GenericListEditComponent<T extends GenericObject> {

    /**
     * Selected object.
     */
    public manipulatedObj: T;

    /**
     * Flag indicating if the form must be rendered.
     */
    public showForm: boolean;

    /**
     * Cancels the object edition and shows the list.
     */
    public backList(): void {
        this.showForm = false;
    }

    /**
     * Stores the selected objct and shows the form.
     */
    public editObj(manipulatedObj: T): void {
        this.manipulatedObj = manipulatedObj;
        this.showForm = true;
    }

    /**
     * Must return the service who will communicate with backend.
     */
    public abstract getService(): GenericService<T>;

    /**
     * Must return a new instance of T.
     */
    public abstract getEmptyObject(): T;

    /**
     * Must return the label used in component.
     */
    public abstract getLabel(): string;

    /**
     * Must return the plural form of the label used in component.
     */
    public abstract getPluralFormLabel(): string;
}
