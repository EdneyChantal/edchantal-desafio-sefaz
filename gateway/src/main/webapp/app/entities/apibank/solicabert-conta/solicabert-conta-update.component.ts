import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISolicabertConta, SolicabertConta } from 'app/shared/model/apibank/solicabert-conta.model';
import { SolicabertContaService } from './solicabert-conta.service';

@Component({
  selector: 'jhi-solicabert-conta-update',
  templateUrl: './solicabert-conta-update.component.html',
})
export class SolicabertContaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    cpf: [null, [Validators.required, Validators.min(1), Validators.max(99999999999)]],
    saldoinicial: [null, [Validators.required, Validators.min(0.1)]],
    status: [null, [Validators.minLength(0), Validators.maxLength(500)]],
  });

  constructor(
    protected solicabertContaService: SolicabertContaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ solicabertConta }) => {
      this.updateForm(solicabertConta);
    });
  }

  updateForm(solicabertConta: ISolicabertConta): void {
    this.editForm.patchValue({
      id: solicabertConta.id,
      nome: solicabertConta.nome,
      cpf: solicabertConta.cpf,
      saldoinicial: solicabertConta.saldoinicial,
      status: solicabertConta.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const solicabertConta = this.createFromForm();
    if (solicabertConta.id !== undefined) {
      this.subscribeToSaveResponse(this.solicabertContaService.update(solicabertConta));
    } else {
      this.subscribeToSaveResponse(this.solicabertContaService.create(solicabertConta));
    }
  }

  private createFromForm(): ISolicabertConta {
    return {
      ...new SolicabertConta(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      cpf: this.editForm.get(['cpf'])!.value,
      saldoinicial: this.editForm.get(['saldoinicial'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISolicabertConta>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
