import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { APP_CONSTANTS } from '../../app.constants';
import { environment } from '../../config/environments/environment';
import { GenericService } from '../../components/register/shared/services/generic-service.service';
import { IMaritalStatus } from '../../models/interfaces/domain/marital-status.model';
import { MaritalStatus } from '../../models/domain/marital-status.model';

@Injectable()
export class MaritalStatusService implements GenericService<MaritalStatus> {

    private endpoint = `/domain/maritalstatus/`;

    constructor(private http: HttpClient) { }

    public create(maritalStatus: MaritalStatus): Observable<HttpResponse<MaritalStatus>> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, this.endpoint);
        return this.http.post<MaritalStatus>(
            wsUrl,
            maritalStatus,
            { observe: 'response' });
    }

    public delete(maritalStatus: MaritalStatus): Observable<HttpResponse<string>> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, this.endpoint);
        return this.http.request<string>('delete',
            wsUrl,
            {
                observe: 'response',
                body: maritalStatus
            }
        );
    }

    /**
     * Search for all marital status.
     */
    public getAll(): Observable<MaritalStatus[]> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, this.endpoint);
        return this.http.get<IMaritalStatus[]>(
            wsUrl,
            { observe: 'response' }
        ).pipe(
            map(
                (res) => {
                    // converts to a real object
                    const ret = [];
                    for (let i = 0; i < res.body.length; i++) {
                        ret.push(new MaritalStatus(res.body[i]));
                    }
                    return ret;
                }
            )
        );
    }

    public update(maritalStatus: MaritalStatus): Observable<HttpResponse<MaritalStatus>> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, this.endpoint);
        return this.http.put<MaritalStatus>(
            wsUrl,
            maritalStatus,
            { observe: 'response' });
    }
}
