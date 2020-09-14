import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SolicabertContaDetailComponent } from 'app/entities/apibank/solicabert-conta/solicabert-conta-detail.component';
import { SolicabertConta } from 'app/shared/model/apibank/solicabert-conta.model';

describe('Component Tests', () => {
  describe('SolicabertConta Management Detail Component', () => {
    let comp: SolicabertContaDetailComponent;
    let fixture: ComponentFixture<SolicabertContaDetailComponent>;
    const route = ({ data: of({ solicabertConta: new SolicabertConta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SolicabertContaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SolicabertContaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SolicabertContaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load solicabertConta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.solicabertConta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
