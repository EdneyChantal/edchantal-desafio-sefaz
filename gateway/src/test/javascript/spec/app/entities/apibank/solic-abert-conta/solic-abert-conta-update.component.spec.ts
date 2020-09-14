import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SolicAbertContaUpdateComponent } from 'app/entities/apibank/solic-abert-conta/solic-abert-conta-update.component';
import { SolicAbertContaService } from 'app/entities/apibank/solic-abert-conta/solic-abert-conta.service';
import { SolicAbertConta } from 'app/shared/model/apibank/solic-abert-conta.model';

describe('Component Tests', () => {
  describe('SolicAbertConta Management Update Component', () => {
    let comp: SolicAbertContaUpdateComponent;
    let fixture: ComponentFixture<SolicAbertContaUpdateComponent>;
    let service: SolicAbertContaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SolicAbertContaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SolicAbertContaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SolicAbertContaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SolicAbertContaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SolicAbertConta(123);
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
        const entity = new SolicAbertConta();
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
