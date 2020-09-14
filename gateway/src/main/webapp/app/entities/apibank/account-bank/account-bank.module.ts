import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { AccountBankComponent } from './account-bank.component';
import { AccountBankDetailComponent } from './account-bank-detail.component';
import { AccountBankUpdateComponent } from './account-bank-update.component';
import { AccountBankDeleteDialogComponent } from './account-bank-delete-dialog.component';
import { accountBankRoute } from './account-bank.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(accountBankRoute)],
  declarations: [AccountBankComponent, AccountBankDetailComponent, AccountBankUpdateComponent, AccountBankDeleteDialogComponent],
  entryComponents: [AccountBankDeleteDialogComponent],
})
export class ApibankAccountBankModule {}
