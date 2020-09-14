import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISolicabertConta } from 'app/shared/model/apibank/solicabert-conta.model';

type EntityResponseType = HttpResponse<ISolicabertConta>;
type EntityArrayResponseType = HttpResponse<ISolicabertConta[]>;

@Injectable({ providedIn: 'root' })
export class SolicabertContaService {
  public resourceUrl = SERVER_API_URL + 'services/apibank/api/solicabert-contas';

  constructor(protected http: HttpClient) {}

  create(solicabertConta: ISolicabertConta): Observable<EntityResponseType> {
    return this.http.post<ISolicabertConta>(this.resourceUrl, solicabertConta, { observe: 'response' });
  }

  update(solicabertConta: ISolicabertConta): Observable<EntityResponseType> {
    return this.http.put<ISolicabertConta>(this.resourceUrl, solicabertConta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISolicabertConta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISolicabertConta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
