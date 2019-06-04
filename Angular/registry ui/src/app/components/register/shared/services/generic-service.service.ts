import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GenericObject } from '../models/generic-object.model';

/**
 * A generic service to be used in generic-list-edit object.
 */
export interface GenericService<T extends GenericObject> {

    create(object: T): Observable<HttpResponse<T>>;

    delete(object: T): Observable<HttpResponse<string>>;

    getAll(): Observable<T[]>;

    update(object: T): Observable<HttpResponse<T>>;
}
