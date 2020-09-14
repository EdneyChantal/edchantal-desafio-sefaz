import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { ParametroComponent } from 'app/entities/apibank/parametro/parametro.component';
import { ParametroService } from 'app/entities/apibank/parametro/parametro.service';
import { Parametro } from 'app/shared/model/apibank/parametro.model';

describe('Component Tests', () => {
  describe('Parametro Management Component', () => {
    let comp: ParametroComponent;
    let fixture: ComponentFixture<ParametroComponent>;
    let service: ParametroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ParametroComponent],
      })
        .overrideTemplate(ParametroComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParametroComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParametroService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Parametro(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.parametros && comp.parametros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
