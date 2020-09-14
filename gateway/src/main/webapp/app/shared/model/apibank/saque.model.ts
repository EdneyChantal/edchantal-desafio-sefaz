import { Moment } from 'moment';

export interface ISaque {
  id?: number;
  dataSaque?: Moment;
  valor?: number;
  accountNumeroConta?: string;
  accountId?: number;
}

export class Saque implements ISaque {
  constructor(
    public id?: number,
    public dataSaque?: Moment,
    public valor?: number,
    public accountNumeroConta?: string,
    public accountId?: number
  ) {}
}
