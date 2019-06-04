import { GenericObject } from '../../components/register/shared/models/generic-object.model';
import { IMaritalStatus } from '../interfaces/domain/marital-status.model';

/**
 * An object mapping an instance of MaritalStatus.
 */
export class MaritalStatus implements IMaritalStatus, GenericObject {

    maritalStatusId: number;
    maritalStatusText: string;

    constructor(base: IMaritalStatus) {
        Object.assign(this, base);
    }

    public getId(): number {
        return this.maritalStatusId;
    }

    public getText(): string {
        return this.maritalStatusText;
    }

    public setId(id: number): void {
        this.maritalStatusId = id;
    }

    public setText(text: string): void {
        this.maritalStatusText = text;
    }
}
