import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountBank } from 'app/shared/model/apibank/account-bank.model';

@Component({
  selector: 'jhi-account-bank-detail',
  templateUrl: './account-bank-detail.component.html',
})
export class AccountBankDetailComponent implements OnInit {
  accountBank: IAccountBank | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountBank }) => (this.accountBank = accountBank));
  }

  previousState(): void {
    window.history.back();
  }
}
