export interface ISolicAbertConta {
  id?: number;
  nome?: string;
  cpf?: number;
  saldoinicial?: number;
  status?: string;
}

export class SolicAbertConta implements ISolicAbertConta {
  constructor(public id?: number, public nome?: string, public cpf?: number, public saldoinicial?: number, public status?: string) {}
}
