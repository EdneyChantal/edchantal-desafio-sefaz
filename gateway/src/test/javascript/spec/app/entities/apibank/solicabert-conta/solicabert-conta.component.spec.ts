import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { SolicabertContaComponent } from 'app/entities/apibank/solicabert-conta/solicabert-conta.component';
import { SolicabertContaService } from 'app/entities/apibank/solicabert-conta/solicabert-conta.service';
import { SolicabertConta } from 'app/shared/model/apibank/solicabert-conta.model';

describe('Component Tests', () => {
  describe('SolicabertConta Management Component', () => {
    let comp: SolicabertContaComponent;
    let fixture: ComponentFixture<SolicabertContaComponent>;
    let service: SolicabertContaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SolicabertContaComponent],
      })
        .overrideTemplate(SolicabertContaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SolicabertContaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SolicabertContaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SolicabertConta(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.solicabertContas && comp.solicabertContas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
