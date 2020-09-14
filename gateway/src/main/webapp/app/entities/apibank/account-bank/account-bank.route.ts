import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAccountBank, AccountBank } from 'app/shared/model/apibank/account-bank.model';
import { AccountBankService } from './account-bank.service';
import { AccountBankComponent } from './account-bank.component';
import { AccountBankDetailComponent } from './account-bank-detail.component';
import { AccountBankUpdateComponent } from './account-bank-update.component';

@Injectable({ providedIn: 'root' })
export class AccountBankResolve implements Resolve<IAccountBank> {
  constructor(private service: AccountBankService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccountBank> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((accountBank: HttpResponse<AccountBank>) => {
          if (accountBank.body) {
            return of(accountBank.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AccountBank());
  }
}

export const accountBankRoute: Routes = [
  {
    path: '',
    component: AccountBankComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankAccountBank.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccountBankDetailComponent,
    resolve: {
      accountBank: AccountBankResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankAccountBank.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccountBankUpdateComponent,
    resolve: {
      accountBank: AccountBankResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankAccountBank.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccountBankUpdateComponent,
    resolve: {
      accountBank: AccountBankResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankAccountBank.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
