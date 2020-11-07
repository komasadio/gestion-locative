export interface IPays {
  id?: number;
  nom?: string;
}

export class Pays implements IPays {
  constructor(public id?: number, public nom?: string) {}
}
