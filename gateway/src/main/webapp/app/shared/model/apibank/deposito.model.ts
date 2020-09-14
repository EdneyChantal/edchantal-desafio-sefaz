import { Moment } from 'moment';

export interface IDeposito {
  id?: number;
  dataDeposito?: Moment;
  valor?: number;
  accountNumeroConta?: string;
  accountId?: number;
}

export class Deposito implements IDeposito {
  constructor(
    public id?: number,
    public dataDeposito?: Moment,
    public valor?: number,
    public accountNumeroConta?: string,
    public accountId?: number
  ) {}
}
