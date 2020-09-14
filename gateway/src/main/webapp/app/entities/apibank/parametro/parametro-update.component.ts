import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IParametro, Parametro } from 'app/shared/model/apibank/parametro.model';
import { ParametroService } from './parametro.service';

@Component({
  selector: 'jhi-parametro-update',
  templateUrl: './parametro-update.component.html',
})
export class ParametroUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    vlrMinAbreConta: [null, [Validators.required]],
    vlrMaxTransfer: [null, [Validators.required]],
  });

  constructor(protected parametroService: ParametroService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parametro }) => {
      this.updateForm(parametro);
    });
  }

  updateForm(parametro: IParametro): void {
    this.editForm.patchValue({
      id: parametro.id,
      vlrMinAbreConta: parametro.vlrMinAbreConta,
      vlrMaxTransfer: parametro.vlrMaxTransfer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parametro = this.createFromForm();
    if (parametro.id !== undefined) {
      this.subscribeToSaveResponse(this.parametroService.update(parametro));
    } else {
      this.subscribeToSaveResponse(this.parametroService.create(parametro));
    }
  }

  private createFromForm(): IParametro {
    return {
      ...new Parametro(),
      id: this.editForm.get(['id'])!.value,
      vlrMinAbreConta: this.editForm.get(['vlrMinAbreConta'])!.value,
      vlrMaxTransfer: this.editForm.get(['vlrMaxTransfer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParametro>>): void {
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
