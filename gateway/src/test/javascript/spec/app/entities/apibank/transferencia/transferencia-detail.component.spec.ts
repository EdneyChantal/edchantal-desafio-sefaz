import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { TransferenciaDetailComponent } from 'app/entities/apibank/transferencia/transferencia-detail.component';
import { Transferencia } from 'app/shared/model/apibank/transferencia.model';

describe('Component Tests', () => {
  describe('Transferencia Management Detail Component', () => {
    let comp: TransferenciaDetailComponent;
    let fixture: ComponentFixture<TransferenciaDetailComponent>;
    const route = ({ data: of({ transferencia: new Transferencia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [TransferenciaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TransferenciaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransferenciaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load transferencia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transferencia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
