import { TypeBien } from 'app/shared/model/enumerations/type-bien.model';

export interface IBiens {
  id?: number;
  estMeuble?: boolean;
  description?: string;
  type?: TypeBien;
  surface?: number;
  adresseId?: number;
}

export class Biens implements IBiens {
  constructor(
    public id?: number,
    public estMeuble?: boolean,
    public description?: string,
    public type?: TypeBien,
    public surface?: number,
    public adresseId?: number
  ) {
    this.estMeuble = this.estMeuble || false;
  }
}
