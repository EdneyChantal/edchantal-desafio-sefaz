import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccountBank } from 'app/shared/model/apibank/account-bank.model';
import { AccountBankService } from './account-bank.service';
import { AccountBankDeleteDialogComponent } from './account-bank-delete-dialog.component';

@Component({
  selector: 'jhi-account-bank',
  templateUrl: './account-bank.component.html',
})
export class AccountBankComponent implements OnInit, OnDestroy {
  accountBanks?: IAccountBank[];
  eventSubscriber?: Subscription;

  constructor(
    protected accountBankService: AccountBankService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.accountBankService.query().subscribe((res: HttpResponse<IAccountBank[]>) => (this.accountBanks = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAccountBanks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAccountBank): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAccountBanks(): void {
    this.eventSubscriber = this.eventManager.subscribe('accountBankListModification', () => this.loadAll());
  }

  delete(accountBank: IAccountBank): void {
    const modalRef = this.modalService.open(AccountBankDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.accountBank = accountBank;
  }
}
