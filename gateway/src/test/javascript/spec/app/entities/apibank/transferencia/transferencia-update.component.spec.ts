import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { TransferenciaUpdateComponent } from 'app/entities/apibank/transferencia/transferencia-update.component';
import { TransferenciaService } from 'app/entities/apibank/transferencia/transferencia.service';
import { Transferencia } from 'app/shared/model/apibank/transferencia.model';

describe('Component Tests', () => {
  describe('Transferencia Management Update Component', () => {
    let comp: TransferenciaUpdateComponent;
    let fixture: ComponentFixture<TransferenciaUpdateComponent>;
    let service: TransferenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [TransferenciaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TransferenciaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransferenciaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransferenciaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Transferencia(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Transferencia();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
