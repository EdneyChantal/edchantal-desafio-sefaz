import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { DepositoComponent } from 'app/entities/apibank/deposito/deposito.component';
import { DepositoService } from 'app/entities/apibank/deposito/deposito.service';
import { Deposito } from 'app/shared/model/apibank/deposito.model';

describe('Component Tests', () => {
  describe('Deposito Management Component', () => {
    let comp: DepositoComponent;
    let fixture: ComponentFixture<DepositoComponent>;
    let service: DepositoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [DepositoComponent],
      })
        .overrideTemplate(DepositoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepositoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepositoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Deposito(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.depositos && comp.depositos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
