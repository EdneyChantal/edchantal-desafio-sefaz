import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeposito } from 'app/shared/model/apibank/deposito.model';
import { DepositoService } from './deposito.service';

@Component({
  templateUrl: './deposito-delete-dialog.component.html',
})
export class DepositoDeleteDialogComponent {
  deposito?: IDeposito;

  constructor(protected depositoService: DepositoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.depositoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('depositoListModification');
      this.activeModal.close();
    });
  }
}
