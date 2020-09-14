import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISolicabertConta } from 'app/shared/model/apibank/solicabert-conta.model';
import { SolicabertContaService } from './solicabert-conta.service';
import { SolicabertContaDeleteDialogComponent } from './solicabert-conta-delete-dialog.component';

@Component({
  selector: 'jhi-solicabert-conta',
  templateUrl: './solicabert-conta.component.html',
})
export class SolicabertContaComponent implements OnInit, OnDestroy {
  solicabertContas?: ISolicabertConta[];
  eventSubscriber?: Subscription;

  constructor(
    protected solicabertContaService: SolicabertContaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.solicabertContaService.query().subscribe((res: HttpResponse<ISolicabertConta[]>) => (this.solicabertContas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSolicabertContas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISolicabertConta): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSolicabertContas(): void {
    this.eventSubscriber = this.eventManager.subscribe('solicabertContaListModification', () => this.loadAll());
  }

  delete(solicabertConta: ISolicabertConta): void {
    const modalRef = this.modalService.open(SolicabertContaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.solicabertConta = solicabertConta;
  }
}
