/**
 * An array of data with an associated page object used for paging
 */
export class PagedData<T> {
    content = new Array<T>();

    // The number of elements in the page
    size = 0;
    // The total number of elements
    totalElements = 0;
    // The current page number
    number = 0;

    pageable = {
        offset: 0
    };
    numberOfElements = 0;
}
