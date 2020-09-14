import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IParametro, Parametro } from 'app/shared/model/apibank/parametro.model';
import { ParametroService } from './parametro.service';
import { ParametroComponent } from './parametro.component';
import { ParametroDetailComponent } from './parametro-detail.component';
import { ParametroUpdateComponent } from './parametro-update.component';

@Injectable({ providedIn: 'root' })
export class ParametroResolve implements Resolve<IParametro> {
  constructor(private service: ParametroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParametro> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((parametro: HttpResponse<Parametro>) => {
          if (parametro.body) {
            return of(parametro.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Parametro());
  }
}

export const parametroRoute: Routes = [
  {
    path: '',
    component: ParametroComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankParametro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParametroDetailComponent,
    resolve: {
      parametro: ParametroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankParametro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParametroUpdateComponent,
    resolve: {
      parametro: ParametroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankParametro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParametroUpdateComponent,
    resolve: {
      parametro: ParametroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.apibankParametro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
