export interface IParametro {
  id?: number;
  vlrMinAbreConta?: number;
  vlrMaxTransfer?: number;
}

export class Parametro implements IParametro {
  constructor(public id?: number, public vlrMinAbreConta?: number, public vlrMaxTransfer?: number) {}
}
