import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { AccountBankUpdateComponent } from 'app/entities/apibank/account-bank/account-bank-update.component';
import { AccountBankService } from 'app/entities/apibank/account-bank/account-bank.service';
import { AccountBank } from 'app/shared/model/apibank/account-bank.model';

describe('Component Tests', () => {
  describe('AccountBank Management Update Component', () => {
    let comp: AccountBankUpdateComponent;
    let fixture: ComponentFixture<AccountBankUpdateComponent>;
    let service: AccountBankService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [AccountBankUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AccountBankUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountBankUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountBankService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountBank(123);
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
        const entity = new AccountBank();
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
