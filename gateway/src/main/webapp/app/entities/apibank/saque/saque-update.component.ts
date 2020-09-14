import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISaque, Saque } from 'app/shared/model/apibank/saque.model';
import { SaqueService } from './saque.service';
import { IAccountBank } from 'app/shared/model/apibank/account-bank.model';
import { AccountBankService } from 'app/entities/apibank/account-bank/account-bank.service';

@Component({
  selector: 'jhi-saque-update',
  templateUrl: './saque-update.component.html',
})
export class SaqueUpdateComponent implements OnInit {
  isSaving = false;
  accountbanks: IAccountBank[] = [];
  dataSaqueDp: any;

  editForm = this.fb.group({
    id: [],
    dataSaque: [null, [Validators.required]],
    valor: [null, [Validators.required, Validators.min(1)]],
    accountId: [null, Validators.required],
  });

  constructor(
    protected saqueService: SaqueService,
    protected accountBankService: AccountBankService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ saque }) => {
      this.updateForm(saque);

      this.accountBankService.query().subscribe((res: HttpResponse<IAccountBank[]>) => (this.accountbanks = res.body || []));
    });
  }

  updateForm(saque: ISaque): void {
    this.editForm.patchValue({
      id: saque.id,
      dataSaque: saque.dataSaque,
      valor: saque.valor,
      accountId: saque.accountId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const saque = this.createFromForm();
    if (saque.id !== undefined) {
      this.subscribeToSaveResponse(this.saqueService.update(saque));
    } else {
      this.subscribeToSaveResponse(this.saqueService.create(saque));
    }
  }

  private createFromForm(): ISaque {
    return {
      ...new Saque(),
      id: this.editForm.get(['id'])!.value,
      dataSaque: this.editForm.get(['dataSaque'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      accountId: this.editForm.get(['accountId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISaque>>): void {
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
