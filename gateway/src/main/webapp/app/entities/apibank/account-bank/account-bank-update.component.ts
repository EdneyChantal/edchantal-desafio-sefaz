import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAccountBank, AccountBank } from 'app/shared/model/apibank/account-bank.model';
import { AccountBankService } from './account-bank.service';
import { IPerson } from 'app/shared/model/apibank/person.model';
import { PersonService } from 'app/entities/apibank/person/person.service';

@Component({
  selector: 'jhi-account-bank-update',
  templateUrl: './account-bank-update.component.html',
})
export class AccountBankUpdateComponent implements OnInit {
  isSaving = false;
  people: IPerson[] = [];

  editForm = this.fb.group({
    id: [],
    saldo: [null, [Validators.required]],
    numeroConta: [null, [Validators.required]],
    personId: [null, Validators.required],
  });

  constructor(
    protected accountBankService: AccountBankService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountBank }) => {
      this.updateForm(accountBank);

      this.personService.query().subscribe((res: HttpResponse<IPerson[]>) => (this.people = res.body || []));
    });
  }

  updateForm(accountBank: IAccountBank): void {
    this.editForm.patchValue({
      id: accountBank.id,
      saldo: accountBank.saldo,
      numeroConta: accountBank.numeroConta,
      personId: accountBank.personId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accountBank = this.createFromForm();
    if (accountBank.id !== undefined) {
      this.subscribeToSaveResponse(this.accountBankService.update(accountBank));
    } else {
      this.subscribeToSaveResponse(this.accountBankService.create(accountBank));
    }
  }

  private createFromForm(): IAccountBank {
    return {
      ...new AccountBank(),
      id: this.editForm.get(['id'])!.value,
      saldo: this.editForm.get(['saldo'])!.value,
      numeroConta: this.editForm.get(['numeroConta'])!.value,
      personId: this.editForm.get(['personId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountBank>>): void {
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

  trackById(index: number, item: IPerson): any {
    return item.id;
  }
}
