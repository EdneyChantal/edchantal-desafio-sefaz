import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISolicabertConta } from 'app/shared/model/apibank/solicabert-conta.model';
import { SolicabertContaService } from './solicabert-conta.service';

@Component({
  templateUrl: './solicabert-conta-delete-dialog.component.html',
})
export class SolicabertContaDeleteDialogComponent {
  solicabertConta?: ISolicabertConta;

  constructor(
    protected solicabertContaService: SolicabertContaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.solicabertContaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('solicabertContaListModification');
      this.activeModal.close();
    });
  }
}
