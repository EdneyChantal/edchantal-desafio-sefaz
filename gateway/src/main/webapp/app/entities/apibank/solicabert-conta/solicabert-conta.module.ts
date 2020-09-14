import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { SolicabertContaComponent } from './solicabert-conta.component';
import { SolicabertContaDetailComponent } from './solicabert-conta-detail.component';
import { SolicabertContaUpdateComponent } from './solicabert-conta-update.component';
import { SolicabertContaDeleteDialogComponent } from './solicabert-conta-delete-dialog.component';
import { solicabertContaRoute } from './solicabert-conta.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(solicabertContaRoute)],
  declarations: [
    SolicabertContaComponent,
    SolicabertContaDetailComponent,
    SolicabertContaUpdateComponent,
    SolicabertContaDeleteDialogComponent,
  ],
  entryComponents: [SolicabertContaDeleteDialogComponent],
})
export class ApibankSolicabertContaModule {}
