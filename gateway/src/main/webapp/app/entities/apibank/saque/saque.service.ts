import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISaque } from 'app/shared/model/apibank/saque.model';

type EntityResponseType = HttpResponse<ISaque>;
type EntityArrayResponseType = HttpResponse<ISaque[]>;

@Injectable({ providedIn: 'root' })
export class SaqueService {
  public resourceUrl = SERVER_API_URL + 'services/apibank/api/saques';

  constructor(protected http: HttpClient) {}

  create(saque: ISaque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(saque);
    return this.http
      .post<ISaque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(saque: ISaque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(saque);
    return this.http
      .put<ISaque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISaque>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISaque[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(saque: ISaque): ISaque {
    const copy: ISaque = Object.assign({}, saque, {
      dataSaque: saque.dataSaque && saque.dataSaque.isValid() ? saque.dataSaque.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataSaque = res.body.dataSaque ? moment(res.body.dataSaque) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((saque: ISaque) => {
        saque.dataSaque = saque.dataSaque ? moment(saque.dataSaque) : undefined;
      });
    }
    return res;
  }
}
