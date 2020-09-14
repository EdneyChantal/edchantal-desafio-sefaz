import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDeposito, Deposito } from 'app/shared/model/apibank/deposito.model';
import { DepositoService } from './deposito.service';
import { IAccountBank } from 'app/shared/model/apibank/account-bank.model';
import { AccountBankService } from 'app/entities/apibank/account-bank/account-bank.service';

@Component({
  selector: 'jhi-deposito-update',
  templateUrl: './deposito-update.component.html',
})
export class DepositoUpdateComponent implements OnInit {
  isSaving = false;
  accountbanks: IAccountBank[] = [];
  dataDepositoDp: any;

  editForm = this.fb.group({
    id: [],
    dataDeposito: [null, [Validators.required]],
    valor: [null, [Validators.required, Validators.min(1)]],
    accountId: [null, Validators.required],
  });

  constructor(
    protected depositoService: DepositoService,
    protected accountBankService: AccountBankService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deposito }) => {
      this.updateForm(deposito);

      this.accountBankService.query().subscribe((res: HttpResponse<IAccountBank[]>) => (this.accountbanks = res.body || []));
    });
  }

  updateForm(deposito: IDeposito): void {
    this.editForm.patchValue({
      id: deposito.id,
      dataDeposito: deposito.dataDeposito,
      valor: deposito.valor,
      accountId: deposito.accountId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deposito = this.createFromForm();
    if (deposito.id !== undefined) {
      this.subscribeToSaveResponse(this.depositoService.update(deposito));
    } else {
      this.subscribeToSaveResponse(this.depositoService.create(deposito));
    }
  }

  private createFromForm(): IDeposito {
    return {
      ...new Deposito(),
      id: this.editForm.get(['id'])!.value,
      dataDeposito: this.editForm.get(['dataDeposito'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      accountId: this.editForm.get(['accountId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeposito>>): void {
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
