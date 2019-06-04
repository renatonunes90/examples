import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { APP_CONSTANTS } from '../app.constants';
import { environment } from '../config/environments/environment';
import { PagedSearch } from '../models/datatable/paged-search.model';
import { Person } from '../models/person.model';
import { PagedData } from '../models/datatable/paged-data.model';

@Injectable()
export class PersonService {

    private endpoint = environment.baseUrl.concat(APP_CONSTANTS.serviceName, '/person/');

    constructor(private http: HttpClient) { }

    public create(person: Person): Observable<HttpResponse<Person>> {
        return this.http.post<Person>(
            this.endpoint,
            person,
            { observe: 'response' });
    }

    public delete(person: Person): Observable<HttpResponse<string>> {
        return this.http.request<string>('delete',
            this.endpoint,
            {
                observe: 'response',
                body: person
            }
        );
    }

    public getById(personId: string): Observable<HttpResponse<Person>> {
        const wsUrl = this.endpoint.concat(personId);
        return this.http.get<Person>(
            wsUrl,
            { observe: 'response' }
        );
    }

    /**
     * Search people by code.
     *
     * @param personCode Code that person code should contains.
     */
    public search(personCode: number, cpf: string, civilName: string, socialName: string, page: PagedSearch):
        Observable<HttpResponse<PagedData<Person>>> {
        const params = [];
        if (personCode !== null) {
            params.push(`personCode=${personCode}`);
        }
        if (cpf !== null && cpf !== '') {
            params.push(`cpf=${cpf}`);
        }
        if (civilName !== null && civilName !== '') {
            params.push(`civilName=${civilName}`);
        }
        if (socialName !== null && socialName !== '') {
            params.push(`socialName=${socialName}`);
        }
        if (page !== null) {
            params.push(`page=${page.pageNumber}&`);
            params.push(`size=${page.size}`);
        }

        let paramsString = '';
        if (params.length > 0) {
            paramsString = paramsString.concat('?', params.join('&'));
        }

        const wsUrl = this.endpoint.concat(paramsString);
        return this.http.get<PagedData<Person>>(
            wsUrl,
            { observe: 'response' }
        );
    }

    /**
     * Search people with account.
     *
     * @param enrollment Enrollment number of person.
     * @param cpf Person's CPF of account.
     * @param name Person's name of account.
     * @param authentication AD authentication.
     * @param pageObject Object with information about paged search.
     */
    public searchWithAccount(enrollment: string, cpf: string, name: string, authentication: string, page: PagedSearch):
        Observable<HttpResponse<PagedData<Person>>> {
        let paramsString = 'account';
        let result = null;

        const params = [];
        if (enrollment) {
            params.push(`enrollment=${enrollment}`);
        }
        if (cpf) {
            params.push(`cpf=${cpf}`);
        }
        if (authentication) {
            params.push(`authentication=${authentication}`);
        }
        if (name) {
            params.push(`name=${name}`);
        }
        if (page) {
            params.push(`page=${page.pageNumber}`);
            params.push(`size=${page.size}`);
        }
        if (params.length > 0) {
            paramsString = paramsString.concat('?', params.join('&'));
        }
        const wsUrl = this.endpoint.concat(paramsString);
        result = this.http.get<PagedData<Person>>(
            wsUrl,
            { observe: 'response' }
        );

        return result;
    }

    public update(person: Person): Observable<HttpResponse<Person>> {
        return this.http.put<Person>(
            this.endpoint,
            person,
            { observe: 'response' });
    }
}
