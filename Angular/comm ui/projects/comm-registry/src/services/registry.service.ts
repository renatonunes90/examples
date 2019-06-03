import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../config/environments/environment';
import { PersonInfo } from '../models/person-info.model';

@Injectable()
export class RegistryService {

    private commEndpoint = environment.environmentHost.concat('ws-comm/');

    constructor(private http: HttpClient) { }

    /**
     * Requests the creation of a new person.
     */
    public createAccount(personInfo: PersonInfo): Observable<HttpResponse<PersonInfo>> {
        return this.http.post<PersonInfo>(
            this.commEndpoint.concat('person'),
            personInfo,
            {
                headers: new HttpHeaders({
                    'Content-Type': 'application/x-www-form-urlencoded'
                }),
                observe: 'response'
            }
        );
    }

    public getAccount(personId: string): Observable<HttpResponse<Object>> {
        return this.http.get<Object>(
            this.commEndpoint.concat(`person/?personId=${personId}`),
            {
                observe: 'response'
            }
        );
    }
}
