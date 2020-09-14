import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAccountBank } from 'app/shared/model/apibank/account-bank.model';

type EntityResponseType = HttpResponse<IAccountBank>;
type EntityArrayResponseType = HttpResponse<IAccountBank[]>;

@Injectable({ providedIn: 'root' })
export class AccountBankService {
  public resourceUrl = SERVER_API_URL + 'services/apibank/api/account-banks';

  constructor(protected http: HttpClient) {}

  create(accountBank: IAccountBank): Observable<EntityResponseType> {
    return this.http.post<IAccountBank>(this.resourceUrl, accountBank, { observe: 'response' });
  }

  update(accountBank: IAccountBank): Observable<EntityResponseType> {
    return this.http.put<IAccountBank>(this.resourceUrl, accountBank, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccountBank>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccountBank[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
