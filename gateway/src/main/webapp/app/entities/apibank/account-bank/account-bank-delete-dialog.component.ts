import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountBank } from 'app/shared/model/apibank/account-bank.model';
import { AccountBankService } from './account-bank.service';

@Component({
  templateUrl: './account-bank-delete-dialog.component.html',
})
export class AccountBankDeleteDialogComponent {
  accountBank?: IAccountBank;

  constructor(
    protected accountBankService: AccountBankService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accountBankService.delete(id).subscribe(() => {
      this.eventManager.broadcast('accountBankListModification');
      this.activeModal.close();
    });
  }
}
