import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SolicabertContaUpdateComponent } from 'app/entities/apibank/solicabert-conta/solicabert-conta-update.component';
import { SolicabertContaService } from 'app/entities/apibank/solicabert-conta/solicabert-conta.service';
import { SolicabertConta } from 'app/shared/model/apibank/solicabert-conta.model';

describe('Component Tests', () => {
  describe('SolicabertConta Management Update Component', () => {
    let comp: SolicabertContaUpdateComponent;
    let fixture: ComponentFixture<SolicabertContaUpdateComponent>;
    let service: SolicabertContaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SolicabertContaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SolicabertContaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SolicabertContaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SolicabertContaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SolicabertConta(123);
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
        const entity = new SolicabertConta();
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
