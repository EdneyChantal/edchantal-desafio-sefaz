import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { AccountBankComponent } from 'app/entities/apibank/account-bank/account-bank.component';
import { AccountBankService } from 'app/entities/apibank/account-bank/account-bank.service';
import { AccountBank } from 'app/shared/model/apibank/account-bank.model';

describe('Component Tests', () => {
  describe('AccountBank Management Component', () => {
    let comp: AccountBankComponent;
    let fixture: ComponentFixture<AccountBankComponent>;
    let service: AccountBankService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [AccountBankComponent],
      })
        .overrideTemplate(AccountBankComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountBankComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountBankService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AccountBank(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.accountBanks && comp.accountBanks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
