export interface IPerson {
  id?: number;
  nome?: string;
  cpf?: number;
}

export class Person implements IPerson {
  constructor(public id?: number, public nome?: string, public cpf?: number) {}
}
