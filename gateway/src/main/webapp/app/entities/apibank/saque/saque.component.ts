import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISaque } from 'app/shared/model/apibank/saque.model';
import { SaqueService } from './saque.service';
import { SaqueDeleteDialogComponent } from './saque-delete-dialog.component';

@Component({
  selector: 'jhi-saque',
  templateUrl: './saque.component.html',
})
export class SaqueComponent implements OnInit, OnDestroy {
  saques?: ISaque[];
  eventSubscriber?: Subscription;

  constructor(protected saqueService: SaqueService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.saqueService.query().subscribe((res: HttpResponse<ISaque[]>) => (this.saques = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSaques();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISaque): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSaques(): void {
    this.eventSubscriber = this.eventManager.subscribe('saqueListModification', () => this.loadAll());
  }

  delete(saque: ISaque): void {
    const modalRef = this.modalService.open(SaqueDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.saque = saque;
  }
}
