import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DepositoService } from 'app/entities/apibank/deposito/deposito.service';
import { IDeposito, Deposito } from 'app/shared/model/apibank/deposito.model';

describe('Service Tests', () => {
  describe('Deposito Service', () => {
    let injector: TestBed;
    let service: DepositoService;
    let httpMock: HttpTestingController;
    let elemDefault: IDeposito;
    let expectedResult: IDeposito | IDeposito[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DepositoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Deposito(0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataDeposito: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Deposito', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataDeposito: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataDeposito: currentDate,
          },
          returnedFromService
        );

        service.create(new Deposito()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Deposito', () => {
        const returnedFromService = Object.assign(
          {
            dataDeposito: currentDate.format(DATE_FORMAT),
            valor: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataDeposito: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Deposito', () => {
        const returnedFromService = Object.assign(
          {
            dataDeposito: currentDate.format(DATE_FORMAT),
            valor: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataDeposito: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Deposito', () => {
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
