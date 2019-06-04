import { GenericObject } from '../../components/register/shared/models/generic-object.model';
import { IBreed } from '../interfaces/domain/breed.model';

/**
 * An object mapping an instance of Breed.
 */
export class Breed implements IBreed, GenericObject {

    breedId: number;
    breedText: string;
    operatorCd: string;
    ipCd: string;

    constructor(base: IBreed) {
        Object.assign(this, base);
    }

    getId(): number {
        return this.breedId;
    }

    getText(): string {
        return this.breedText;
    }

    setId(id: number): void {
        this.breedId = id;
    }

    setText(text: string): void {
        this.breedText = text;
    }
}
