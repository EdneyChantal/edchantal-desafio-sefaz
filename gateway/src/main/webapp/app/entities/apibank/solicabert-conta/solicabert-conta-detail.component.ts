import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISolicabertConta } from 'app/shared/model/apibank/solicabert-conta.model';

@Component({
  selector: 'jhi-solicabert-conta-detail',
  templateUrl: './solicabert-conta-detail.component.html',
})
export class SolicabertContaDetailComponent implements OnInit {
  solicabertConta: ISolicabertConta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ solicabertConta }) => (this.solicabertConta = solicabertConta));
  }

  previousState(): void {
    window.history.back();
  }
}
