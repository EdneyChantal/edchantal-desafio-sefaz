import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { TransferenciaComponent } from './transferencia.component';
import { TransferenciaDetailComponent } from './transferencia-detail.component';
import { TransferenciaUpdateComponent } from './transferencia-update.component';
import { TransferenciaDeleteDialogComponent } from './transferencia-delete-dialog.component';
import { transferenciaRoute } from './transferencia.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(transferenciaRoute)],
  declarations: [TransferenciaComponent, TransferenciaDetailComponent, TransferenciaUpdateComponent, TransferenciaDeleteDialogComponent],
  entryComponents: [TransferenciaDeleteDialogComponent],
})
export class ApibankTransferenciaModule {}
