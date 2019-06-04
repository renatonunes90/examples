import { formatDate } from '@angular/common';

/**
 * An object grouping generic functions.
 */
export class GenericFunctions {

    private static instance: GenericFunctions;

    public static getInstance() {
        if (!this.instance) {
            this.instance = new GenericFunctions();
        }
        return this.instance;
    }

    /**
     * Converts date format. It's used by table html renderer.
     *
     * @param date String with date to be formatted.
     */
    public convertDate(date: string): string {
        return date ? formatDate(new Date(date), 'dd/MM/yyyy', 'en-US', 'UTC') : '-';
    }

    /**
    * Verifies if flag value is not null. It's used by table html renderer.
    *
    * @param flag String with flag value.
    */
    public validateFlag(flag: string): string {
        return flag ? flag : 'N';
    }

    /**
     * Verifies if value is not null. It's used by table html renderer.
     *
     * @param value String with value.
     */
    public validateValue(value: string): string {
        return value ? value : '-';
    }

}
