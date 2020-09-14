export interface ISolicabertConta {
  id?: number;
  nome?: string;
  cpf?: number;
  saldoinicial?: number;
  status?: string;
}

export class SolicabertConta implements ISolicabertConta {
  constructor(public id?: number, public nome?: string, public cpf?: number, public saldoinicial?: number, public status?: string) {}
}
