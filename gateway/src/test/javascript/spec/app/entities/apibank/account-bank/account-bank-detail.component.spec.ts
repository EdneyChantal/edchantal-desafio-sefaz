import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { AccountBankDetailComponent } from 'app/entities/apibank/account-bank/account-bank-detail.component';
import { AccountBank } from 'app/shared/model/apibank/account-bank.model';

describe('Component Tests', () => {
  describe('AccountBank Management Detail Component', () => {
    let comp: AccountBankDetailComponent;
    let fixture: ComponentFixture<AccountBankDetailComponent>;
    const route = ({ data: of({ accountBank: new AccountBank(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [AccountBankDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AccountBankDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountBankDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accountBank on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountBank).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
