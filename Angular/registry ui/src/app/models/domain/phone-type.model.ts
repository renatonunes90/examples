import { GenericObject } from '../../components/register/shared/models/generic-object.model';
import { IPhoneType } from '../interfaces/domain/phone-type.model';

/**
 * An object mapping an instance of PhoneType.
 */
export class PhoneType implements IPhoneType, GenericObject {

    phoneTypeId: number;
    phoneTypeText: string;

    constructor(base: IPhoneType) {
        Object.assign(this, base);
    }

    public getId(): number {
        return this.phoneTypeId;
    }

    public getText(): string {
        return this.phoneTypeText;
    }

    public setId(id: number): void {
        this.phoneTypeId = id;
    }

    public setText(text: string): void {
        this.phoneTypeText = text;
    }
}
