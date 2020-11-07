export interface IAdresse {
  id?: number;
  libelle?: string;
  ville?: string;
  commune?: string;
  departement?: string;
  paysId?: number;
}

export class Adresse implements IAdresse {
  constructor(
    public id?: number,
    public libelle?: string,
    public ville?: string,
    public commune?: string,
    public departement?: string,
    public paysId?: number
  ) {}
}
