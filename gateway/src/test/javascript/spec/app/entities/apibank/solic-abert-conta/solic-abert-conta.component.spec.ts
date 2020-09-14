import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { SolicAbertContaComponent } from 'app/entities/apibank/solic-abert-conta/solic-abert-conta.component';
import { SolicAbertContaService } from 'app/entities/apibank/solic-abert-conta/solic-abert-conta.service';
import { SolicAbertConta } from 'app/shared/model/apibank/solic-abert-conta.model';

describe('Component Tests', () => {
  describe('SolicAbertConta Management Component', () => {
    let comp: SolicAbertContaComponent;
    let fixture: ComponentFixture<SolicAbertContaComponent>;
    let service: SolicAbertContaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SolicAbertContaComponent],
      })
        .overrideTemplate(SolicAbertContaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SolicAbertContaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SolicAbertContaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SolicAbertConta(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.solicAbertContas && comp.solicAbertContas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
