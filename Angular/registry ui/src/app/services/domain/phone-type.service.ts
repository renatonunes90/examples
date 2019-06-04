import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { APP_CONSTANTS } from '../../app.constants';
import { environment } from '../../config/environments/environment';
import { GenericService } from '../../components/register/shared/services/generic-service.service';
import { IPhoneType } from '../../models/interfaces/domain/phone-type.model';
import { PhoneType } from '../../models/domain/phone-type.model';

@Injectable()
export class PhoneTypeService implements GenericService<PhoneType> {

    private endpoint = `/domain/phonetype/`;

    constructor(private http: HttpClient) { }

    public create(phoneType: PhoneType): Observable<HttpResponse<PhoneType>> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, this.endpoint);
        return this.http.post<PhoneType>(
            wsUrl,
            phoneType,
            { observe: 'response' });
    }

    public delete(phoneType: PhoneType): Observable<HttpResponse<string>> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, this.endpoint);
        return this.http.request<string>('delete',
            wsUrl,
            {
                observe: 'response',
                body: phoneType
            }
        );
    }

    /**
     * Search for all phone types.
     */
    public getAll(): Observable<PhoneType[]> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, this.endpoint);
        return this.http.get<IPhoneType[]>(
            wsUrl,
            { observe: 'response' }
        ).pipe(
            map(
                (res) => {
                    // converts to a real object
                    const ret = [];
                    for (let i = 0; i < res.body.length; i++) {
                        ret.push(new PhoneType(res.body[i]));
                    }
                    return ret;
                }
            )
        );
    }

    public update(phoneType: PhoneType): Observable<HttpResponse<PhoneType>> {
        const wsUrl = environment.baseUrl.concat(APP_CONSTANTS.serviceName, this.endpoint);
        return this.http.put<PhoneType>(
            wsUrl,
            phoneType,
            { observe: 'response' });
    }
}
