import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParametro } from 'app/shared/model/apibank/parametro.model';
import { ParametroService } from './parametro.service';

@Component({
  templateUrl: './parametro-delete-dialog.component.html',
})
export class ParametroDeleteDialogComponent {
  parametro?: IParametro;

  constructor(protected parametroService: ParametroService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.parametroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('parametroListModification');
      this.activeModal.close();
    });
  }
}
