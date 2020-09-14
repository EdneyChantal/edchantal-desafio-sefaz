import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SaqueDetailComponent } from 'app/entities/apibank/saque/saque-detail.component';
import { Saque } from 'app/shared/model/apibank/saque.model';

describe('Component Tests', () => {
  describe('Saque Management Detail Component', () => {
    let comp: SaqueDetailComponent;
    let fixture: ComponentFixture<SaqueDetailComponent>;
    const route = ({ data: of({ saque: new Saque(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SaqueDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SaqueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SaqueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load saque on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.saque).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
