import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITransferencia, Transferencia } from 'app/shared/model/apibank/transferencia.model';
import { TransferenciaService } from './transferencia.service';
import { TransferenciaComponent } from './transferencia.component';
import { TransferenciaDetailComponent } from './transferencia-detail.component';
import { TransferenciaUpdateComponent } from './transferencia-update.component';

@Injectable({ providedIn: 'root' })
export class TransferenciaResolve implements Resolve<ITransferencia> {
  constructor(private service: TransferenciaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransferencia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((transferencia: HttpResponse<Transferencia>) => {
          if (transferencia.body) {
            return of(transferencia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Transferencia());
  }
}

export const transferenciaRoute: Routes = [
  {
    path: '',
    component: TransferenciaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankTransferencia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransferenciaDetailComponent,
    resolve: {
      transferencia: TransferenciaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankTransferencia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransferenciaUpdateComponent,
    resolve: {
      transferencia: TransferenciaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankTransferencia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransferenciaUpdateComponent,
    resolve: {
      transferencia: TransferenciaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankTransferencia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
