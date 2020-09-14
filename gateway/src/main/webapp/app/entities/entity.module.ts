import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'parametro',
        loadChildren: () => import('./apibank/parametro/parametro.module').then(m => m.ApibankParametroModule),
      },
      {
        path: 'account-bank',
        loadChildren: () => import('./apibank/account-bank/account-bank.module').then(m => m.ApibankAccountBankModule),
      },
      {
        path: 'person',
        loadChildren: () => import('./apibank/person/person.module').then(m => m.ApibankPersonModule),
      },
      {
        path: 'saque',
        loadChildren: () => import('./apibank/saque/saque.module').then(m => m.ApibankSaqueModule),
      },
      {
        path: 'deposito',
        loadChildren: () => import('./apibank/deposito/deposito.module').then(m => m.ApibankDepositoModule),
      },
      {
        path: 'solicabert-conta',
        loadChildren: () => import('./apibank/solicabert-conta/solicabert-conta.module').then(m => m.ApibankSolicabertContaModule),
      },
      {
        path: 'transferencia',
        loadChildren: () => import('./apibank/transferencia/transferencia.module').then(m => m.ApibankTransferenciaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GatewayEntityModule {}
