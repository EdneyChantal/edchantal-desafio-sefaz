import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ParametroUpdateComponent } from 'app/entities/apibank/parametro/parametro-update.component';
import { ParametroService } from 'app/entities/apibank/parametro/parametro.service';
import { Parametro } from 'app/shared/model/apibank/parametro.model';

describe('Component Tests', () => {
  describe('Parametro Management Update Component', () => {
    let comp: ParametroUpdateComponent;
    let fixture: ComponentFixture<ParametroUpdateComponent>;
    let service: ParametroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ParametroUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ParametroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParametroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParametroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Parametro(123);
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
        const entity = new Parametro();
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
