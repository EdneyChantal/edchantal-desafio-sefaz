import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { TransferenciaComponent } from 'app/entities/apibank/transferencia/transferencia.component';
import { TransferenciaService } from 'app/entities/apibank/transferencia/transferencia.service';
import { Transferencia } from 'app/shared/model/apibank/transferencia.model';

describe('Component Tests', () => {
  describe('Transferencia Management Component', () => {
    let comp: TransferenciaComponent;
    let fixture: ComponentFixture<TransferenciaComponent>;
    let service: TransferenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [TransferenciaComponent],
      })
        .overrideTemplate(TransferenciaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransferenciaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransferenciaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Transferencia(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transferencias && comp.transferencias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
