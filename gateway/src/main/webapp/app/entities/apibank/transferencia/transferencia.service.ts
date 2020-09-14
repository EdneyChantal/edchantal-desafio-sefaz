import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransferencia } from 'app/shared/model/apibank/transferencia.model';

type EntityResponseType = HttpResponse<ITransferencia>;
type EntityArrayResponseType = HttpResponse<ITransferencia[]>;

@Injectable({ providedIn: 'root' })
export class TransferenciaService {
  public resourceUrl = SERVER_API_URL + 'services/apibank/api/transferencias';

  constructor(protected http: HttpClient) {}

  create(transferencia: ITransferencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferencia);
    return this.http
      .post<ITransferencia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transferencia: ITransferencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferencia);
    return this.http
      .put<ITransferencia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransferencia>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransferencia[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(transferencia: ITransferencia): ITransferencia {
    const copy: ITransferencia = Object.assign({}, transferencia, {
      dataTransferencia:
        transferencia.dataTransferencia && transferencia.dataTransferencia.isValid()
          ? transferencia.dataTransferencia.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataTransferencia = res.body.dataTransferencia ? moment(res.body.dataTransferencia) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transferencia: ITransferencia) => {
        transferencia.dataTransferencia = transferencia.dataTransferencia ? moment(transferencia.dataTransferencia) : undefined;
      });
    }
    return res;
  }
}
