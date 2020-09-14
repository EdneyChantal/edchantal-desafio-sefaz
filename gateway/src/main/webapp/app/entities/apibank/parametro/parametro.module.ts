import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ParametroComponent } from './parametro.component';
import { ParametroDetailComponent } from './parametro-detail.component';
import { ParametroUpdateComponent } from './parametro-update.component';
import { ParametroDeleteDialogComponent } from './parametro-delete-dialog.component';
import { parametroRoute } from './parametro.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(parametroRoute)],
  declarations: [ParametroComponent, ParametroDetailComponent, ParametroUpdateComponent, ParametroDeleteDialogComponent],
  entryComponents: [ParametroDeleteDialogComponent],
})
export class ApibankParametroModule {}
