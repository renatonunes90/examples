/**
 * A generic object to be used in generic-list-edit object.
 */
export interface GenericObject {
    getId(): number;
    getText(): string;
    setId(id: number): void;
    setText(text: string): void;
}
