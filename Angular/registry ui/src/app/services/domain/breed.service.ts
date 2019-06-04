import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { APP_CONSTANTS } from '../../app.constants';
import { Breed } from '../../models/domain/breed.model';
import { environment } from '../../config/environments/environment';
import { GenericService } from '../../components/register/shared/services/generic-service.service';
import { IBreed } from '../../models/interfaces/domain/breed.model';

@Injectable()
export class BreedService implements GenericService<Breed> {

    private endpoint = `/domain/breed/`;

    constructor(private http: HttpClient) { }

    public create(object: Breed): Observable<HttpResponse<Breed>> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, this.endpoint);
        return this.http.post<Breed>(
            wsUrl,
            object,
            { observe: 'response' });
    }

    public delete(object: Breed): Observable<HttpResponse<string>> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, this.endpoint);
        return this.http.request<string>('delete',
            wsUrl,
            {
                observe: 'response',
                body: object
            }
        );
    }

    /**
     * Search for all breeds.
     */
    public getAll(): Observable<Breed[]> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, this.endpoint);
        return this.http.get<IBreed[]>(
            wsUrl,
            { observe: 'response' }
        ).pipe(
            map(
                (res) => {
                    // converts to a real object
                    const ret = [];
                    for (let i = 0; i < res.body.length; i++) {
                        ret.push(new Breed(res.body[i]));
                    }
                    return ret;
                }
            )
        );
    }

    public update(object: Breed): Observable<HttpResponse<Breed>> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, this.endpoint);
        return this.http.put<Breed>(
            wsUrl,
            object,
            { observe: 'response' });
    }
}
