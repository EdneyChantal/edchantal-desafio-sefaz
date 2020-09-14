import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransferencia } from 'app/shared/model/apibank/transferencia.model';
import { TransferenciaService } from './transferencia.service';

@Component({
  templateUrl: './transferencia-delete-dialog.component.html',
})
export class TransferenciaDeleteDialogComponent {
  transferencia?: ITransferencia;

  constructor(
    protected transferenciaService: TransferenciaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transferenciaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transferenciaListModification');
      this.activeModal.close();
    });
  }
}
