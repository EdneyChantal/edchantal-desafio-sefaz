import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransferencia } from 'app/shared/model/apibank/transferencia.model';

@Component({
  selector: 'jhi-transferencia-detail',
  templateUrl: './transferencia-detail.component.html',
})
export class TransferenciaDetailComponent implements OnInit {
  transferencia: ITransferencia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transferencia }) => (this.transferencia = transferencia));
  }

  previousState(): void {
    window.history.back();
  }
}
