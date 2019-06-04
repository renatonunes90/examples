import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { APP_CONSTANTS } from '../../app.constants';
import { Email } from '../../models/persondata/email.model';
import { environment } from '../../config/environments/environment';
import { PagedData } from '../../models/datatable/paged-data.model';

@Injectable()
export class EmailService {

    private endpoint = environment.baseUrl.concat(APP_CONSTANTS.serviceName, '/person/email/');

    constructor(private http: HttpClient) { }

    public create(email: Email): Observable<HttpResponse<Email>> {
        return this.http.post<Email>(
            this.endpoint,
            email,
            { observe: 'response' });
    }

    public delete(email: Email): Observable<HttpResponse<string>> {
        return this.http.request<string>('delete',
            this.endpoint,
            {
                observe: 'response',
                body: email
            }
        );
    }

    /**
     * Search for e-mails of the person.
     *
     * @param personId Identifier of the person.
     */
    public search(personId: number): Observable<HttpResponse<PagedData<Email>>> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, `/person/${personId}/email/`);
        return this.http.get<PagedData<Email>>(
            wsUrl,
            { observe: 'response' }
        );
    }

    public update(email: Email): Observable<HttpResponse<Email>> {
        return this.http.put<Email>(
            this.endpoint,
            email,
            { observe: 'response' });
    }
}
