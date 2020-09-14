import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISaque } from 'app/shared/model/apibank/saque.model';
import { SaqueService } from './saque.service';

@Component({
  templateUrl: './saque-delete-dialog.component.html',
})
export class SaqueDeleteDialogComponent {
  saque?: ISaque;

  constructor(protected saqueService: SaqueService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.saqueService.delete(id).subscribe(() => {
      this.eventManager.broadcast('saqueListModification');
      this.activeModal.close();
    });
  }
}
