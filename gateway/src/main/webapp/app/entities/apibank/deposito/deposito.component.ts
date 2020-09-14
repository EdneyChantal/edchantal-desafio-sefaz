import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeposito } from 'app/shared/model/apibank/deposito.model';
import { DepositoService } from './deposito.service';
import { DepositoDeleteDialogComponent } from './deposito-delete-dialog.component';

@Component({
  selector: 'jhi-deposito',
  templateUrl: './deposito.component.html',
})
export class DepositoComponent implements OnInit, OnDestroy {
  depositos?: IDeposito[];
  eventSubscriber?: Subscription;

  constructor(protected depositoService: DepositoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.depositoService.query().subscribe((res: HttpResponse<IDeposito[]>) => (this.depositos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDepositos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDeposito): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDepositos(): void {
    this.eventSubscriber = this.eventManager.subscribe('depositoListModification', () => this.loadAll());
  }

  delete(deposito: IDeposito): void {
    const modalRef = this.modalService.open(DepositoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deposito = deposito;
  }
}
