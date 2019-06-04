import { PhoneType } from '../domain/phone-type.model';

export interface Phone {
    phoneId: number;
    personId: number;
    phoneType: PhoneType;
    number: string;
    ddd: string;
    ddi: string;
}
