import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransferencia, Transferencia } from 'app/shared/model/apibank/transferencia.model';
import { TransferenciaService } from './transferencia.service';
import { IAccountBank } from 'app/shared/model/apibank/account-bank.model';
import { AccountBankService } from 'app/entities/apibank/account-bank/account-bank.service';

@Component({
  selector: 'jhi-transferencia-update',
  templateUrl: './transferencia-update.component.html',
})
export class TransferenciaUpdateComponent implements OnInit {
  isSaving = false;
  accountbanks: IAccountBank[] = [];
  dataTransferenciaDp: any;

  editForm = this.fb.group({
    id: [],
    dataTransferencia: [null, [Validators.required]],
    valor: [null, [Validators.required, Validators.min(1)]],
    accountSaqueId: [null, Validators.required],
    accountDepositoId: [null, Validators.required],
  });

  constructor(
    protected transferenciaService: TransferenciaService,
    protected accountBankService: AccountBankService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transferencia }) => {
      this.updateForm(transferencia);

      this.accountBankService.query().subscribe((res: HttpResponse<IAccountBank[]>) => (this.accountbanks = res.body || []));
    });
  }

  updateForm(transferencia: ITransferencia): void {
    this.editForm.patchValue({
      id: transferencia.id,
      dataTransferencia: transferencia.dataTransferencia,
      valor: transferencia.valor,
      accountSaqueId: transferencia.accountSaqueId,
      accountDepositoId: transferencia.accountDepositoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transferencia = this.createFromForm();
    if (transferencia.id !== undefined) {
      this.subscribeToSaveResponse(this.transferenciaService.update(transferencia));
    } else {
      this.subscribeToSaveResponse(this.transferenciaService.create(transferencia));
    }
  }

  private createFromForm(): ITransferencia {
    return {
      ...new Transferencia(),
      id: this.editForm.get(['id'])!.value,
      dataTransferencia: this.editForm.get(['dataTransferencia'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      accountSaqueId: this.editForm.get(['accountSaqueId'])!.value,
      accountDepositoId: this.editForm.get(['accountDepositoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransferencia>>): void {
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

  trackById(index: number, item: IAccountBank): any {
    return item.id;
  }
}
