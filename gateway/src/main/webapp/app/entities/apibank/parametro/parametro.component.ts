import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IParametro } from 'app/shared/model/apibank/parametro.model';
import { ParametroService } from './parametro.service';
import { ParametroDeleteDialogComponent } from './parametro-delete-dialog.component';

@Component({
  selector: 'jhi-parametro',
  templateUrl: './parametro.component.html',
})
export class ParametroComponent implements OnInit, OnDestroy {
  parametros?: IParametro[];
  eventSubscriber?: Subscription;

  constructor(protected parametroService: ParametroService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.parametroService.query().subscribe((res: HttpResponse<IParametro[]>) => (this.parametros = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInParametros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IParametro): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInParametros(): void {
    this.eventSubscriber = this.eventManager.subscribe('parametroListModification', () => this.loadAll());
  }

  delete(parametro: IParametro): void {
    const modalRef = this.modalService.open(ParametroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.parametro = parametro;
  }
}
