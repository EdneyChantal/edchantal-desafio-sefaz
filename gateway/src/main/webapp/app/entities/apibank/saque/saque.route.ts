import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISaque, Saque } from 'app/shared/model/apibank/saque.model';
import { SaqueService } from './saque.service';
import { SaqueComponent } from './saque.component';
import { SaqueDetailComponent } from './saque-detail.component';
import { SaqueUpdateComponent } from './saque-update.component';

@Injectable({ providedIn: 'root' })
export class SaqueResolve implements Resolve<ISaque> {
  constructor(private service: SaqueService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISaque> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((saque: HttpResponse<Saque>) => {
          if (saque.body) {
            return of(saque.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Saque());
  }
}

export const saqueRoute: Routes = [
  {
    path: '',
    component: SaqueComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankSaque.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SaqueDetailComponent,
    resolve: {
      saque: SaqueResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankSaque.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SaqueUpdateComponent,
    resolve: {
      saque: SaqueResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankSaque.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SaqueUpdateComponent,
    resolve: {
      saque: SaqueResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankSaque.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
