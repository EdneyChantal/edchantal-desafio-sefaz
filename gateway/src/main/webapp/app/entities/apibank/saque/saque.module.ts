import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { SaqueComponent } from './saque.component';
import { SaqueDetailComponent } from './saque-detail.component';
import { SaqueUpdateComponent } from './saque-update.component';
import { SaqueDeleteDialogComponent } from './saque-delete-dialog.component';
import { saqueRoute } from './saque.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(saqueRoute)],
  declarations: [SaqueComponent, SaqueDetailComponent, SaqueUpdateComponent, SaqueDeleteDialogComponent],
  entryComponents: [SaqueDeleteDialogComponent],
})
export class ApibankSaqueModule {}
