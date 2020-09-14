import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ParametroDetailComponent } from 'app/entities/apibank/parametro/parametro-detail.component';
import { Parametro } from 'app/shared/model/apibank/parametro.model';

describe('Component Tests', () => {
  describe('Parametro Management Detail Component', () => {
    let comp: ParametroDetailComponent;
    let fixture: ComponentFixture<ParametroDetailComponent>;
    const route = ({ data: of({ parametro: new Parametro(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ParametroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ParametroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParametroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load parametro on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.parametro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
