import { Moment } from 'moment';

export interface ITransferencia {
  id?: number;
  dataTransferencia?: Moment;
  valor?: number;
  accountSaqueNumeroConta?: string;
  accountSaqueId?: number;
  accountDepositoNumeroConta?: string;
  accountDepositoId?: number;
}

export class Transferencia implements ITransferencia {
  constructor(
    public id?: number,
    public dataTransferencia?: Moment,
    public valor?: number,
    public accountSaqueNumeroConta?: string,
    public accountSaqueId?: number,
    public accountDepositoNumeroConta?: string,
    public accountDepositoId?: number
  ) {}
}
