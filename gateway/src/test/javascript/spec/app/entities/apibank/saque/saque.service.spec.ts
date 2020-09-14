import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SaqueService } from 'app/entities/apibank/saque/saque.service';
import { ISaque, Saque } from 'app/shared/model/apibank/saque.model';

describe('Service Tests', () => {
  describe('Saque Service', () => {
    let injector: TestBed;
    let service: SaqueService;
    let httpMock: HttpTestingController;
    let elemDefault: ISaque;
    let expectedResult: ISaque | ISaque[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SaqueService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Saque(0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataSaque: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Saque', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataSaque: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataSaque: currentDate,
          },
          returnedFromService
        );

        service.create(new Saque()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Saque', () => {
        const returnedFromService = Object.assign(
          {
            dataSaque: currentDate.format(DATE_FORMAT),
            valor: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataSaque: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Saque', () => {
        const returnedFromService = Object.assign(
          {
            dataSaque: currentDate.format(DATE_FORMAT),
            valor: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataSaque: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Saque', () => {
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
