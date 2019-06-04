import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { APP_CONSTANTS } from '../../app.constants';
import { Phone } from '../../models/persondata/phone.model';
import { environment } from '../../config/environments/environment';
import { PagedData } from '../../models/datatable/paged-data.model';

@Injectable()
export class PhoneService {

    private endpoint = environment.baseUrl.concat(APP_CONSTANTS.serviceName, '/person/phone/');

    constructor(private http: HttpClient) { }

    public create(phone: Phone): Observable<HttpResponse<Phone>> {
        return this.http.post<Phone>(
            this.endpoint,
            phone,
            { observe: 'response' });
    }

    public delete(phone: Phone): Observable<HttpResponse<string>> {
        return this.http.request<string>('delete',
            this.endpoint,
            {
                observe: 'response',
                body: phone
            }
        );
    }

    /**
     * Search for phone numbers of the person.
     *
     * @param personId Identifier of the person.
     */
    public search(personId: number): Observable<HttpResponse<PagedData<Phone>>> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, `/person/${personId}/phone`);
        return this.http.get<PagedData<Phone>>(
            wsUrl,
            { observe: 'response' }
        );
    }

    public update(phone: Phone): Observable<HttpResponse<Phone>> {
        return this.http.put<Phone>(
            this.endpoint,
            phone,
            { observe: 'response' });
    }
}
