import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransferencia } from 'app/shared/model/apibank/transferencia.model';
import { TransferenciaService } from './transferencia.service';
import { TransferenciaDeleteDialogComponent } from './transferencia-delete-dialog.component';

@Component({
  selector: 'jhi-transferencia',
  templateUrl: './transferencia.component.html',
})
export class TransferenciaComponent implements OnInit, OnDestroy {
  transferencias?: ITransferencia[];
  eventSubscriber?: Subscription;

  constructor(
    protected transferenciaService: TransferenciaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.transferenciaService.query().subscribe((res: HttpResponse<ITransferencia[]>) => (this.transferencias = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTransferencias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransferencia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTransferencias(): void {
    this.eventSubscriber = this.eventManager.subscribe('transferenciaListModification', () => this.loadAll());
  }

  delete(transferencia: ITransferencia): void {
    const modalRef = this.modalService.open(TransferenciaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transferencia = transferencia;
  }
}
