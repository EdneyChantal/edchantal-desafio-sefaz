import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SaqueUpdateComponent } from 'app/entities/apibank/saque/saque-update.component';
import { SaqueService } from 'app/entities/apibank/saque/saque.service';
import { Saque } from 'app/shared/model/apibank/saque.model';

describe('Component Tests', () => {
  describe('Saque Management Update Component', () => {
    let comp: SaqueUpdateComponent;
    let fixture: ComponentFixture<SaqueUpdateComponent>;
    let service: SaqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SaqueUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SaqueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SaqueUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SaqueService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Saque(123);
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
        const entity = new Saque();
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
