import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { SaqueComponent } from 'app/entities/apibank/saque/saque.component';
import { SaqueService } from 'app/entities/apibank/saque/saque.service';
import { Saque } from 'app/shared/model/apibank/saque.model';

describe('Component Tests', () => {
  describe('Saque Management Component', () => {
    let comp: SaqueComponent;
    let fixture: ComponentFixture<SaqueComponent>;
    let service: SaqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SaqueComponent],
      })
        .overrideTemplate(SaqueComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SaqueComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SaqueService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Saque(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.saques && comp.saques[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
