import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SolicAbertContaDetailComponent } from 'app/entities/apibank/solic-abert-conta/solic-abert-conta-detail.component';
import { SolicAbertConta } from 'app/shared/model/apibank/solic-abert-conta.model';

describe('Component Tests', () => {
  describe('SolicAbertConta Management Detail Component', () => {
    let comp: SolicAbertContaDetailComponent;
    let fixture: ComponentFixture<SolicAbertContaDetailComponent>;
    const route = ({ data: of({ solicAbertConta: new SolicAbertConta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SolicAbertContaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SolicAbertContaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SolicAbertContaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load solicAbertConta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.solicAbertConta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
