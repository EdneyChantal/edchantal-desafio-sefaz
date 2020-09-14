import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TransferenciaService } from 'app/entities/apibank/transferencia/transferencia.service';
import { ITransferencia, Transferencia } from 'app/shared/model/apibank/transferencia.model';

describe('Service Tests', () => {
  describe('Transferencia Service', () => {
    let injector: TestBed;
    let service: TransferenciaService;
    let httpMock: HttpTestingController;
    let elemDefault: ITransferencia;
    let expectedResult: ITransferencia | ITransferencia[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TransferenciaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Transferencia(0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataTransferencia: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Transferencia', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataTransferencia: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataTransferencia: currentDate,
          },
          returnedFromService
        );

        service.create(new Transferencia()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Transferencia', () => {
        const returnedFromService = Object.assign(
          {
            dataTransferencia: currentDate.format(DATE_FORMAT),
            valor: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataTransferencia: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Transferencia', () => {
        const returnedFromService = Object.assign(
          {
            dataTransferencia: currentDate.format(DATE_FORMAT),
            valor: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataTransferencia: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Transferencia', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
