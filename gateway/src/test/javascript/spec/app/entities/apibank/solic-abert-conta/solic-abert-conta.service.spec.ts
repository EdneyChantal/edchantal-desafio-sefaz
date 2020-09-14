import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SolicAbertContaService } from 'app/entities/apibank/solic-abert-conta/solic-abert-conta.service';
import { ISolicAbertConta, SolicAbertConta } from 'app/shared/model/apibank/solic-abert-conta.model';

describe('Service Tests', () => {
  describe('SolicAbertConta Service', () => {
    let injector: TestBed;
    let service: SolicAbertContaService;
    let httpMock: HttpTestingController;
    let elemDefault: ISolicAbertConta;
    let expectedResult: ISolicAbertConta | ISolicAbertConta[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SolicAbertContaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SolicAbertConta(0, 'AAAAAAA', 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SolicAbertConta', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SolicAbertConta()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SolicAbertConta', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            cpf: 1,
            saldoinicial: 1,
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SolicAbertConta', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            cpf: 1,
            saldoinicial: 1,
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SolicAbertConta', () => {
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
