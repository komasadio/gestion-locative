import { Moment } from 'moment';

export interface ILocataire {
  id?: number;
  nom?: string;
  prenom?: string;
  email?: string;
  telephone?: string;
  dateNaissance?: Moment;
  adresseId?: number;
}

export class Locataire implements ILocataire {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public email?: string,
    public telephone?: string,
    public dateNaissance?: Moment,
    public adresseId?: number
  ) {}
}
