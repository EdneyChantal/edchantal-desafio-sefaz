export interface IAccountBank {
  id?: number;
  saldo?: number;
  numeroConta?: number;
  personNome?: string;
  personId?: number;
}

export class AccountBank implements IAccountBank {
  constructor(
    public id?: number,
    public saldo?: number,
    public numeroConta?: number,
    public personNome?: string,
    public personId?: number
  ) {}
}
