import { Moment } from 'moment';
import { Periode } from 'app/shared/model/enumerations/periode.model';

export interface IContrat {
  id?: number;
  dateDebut?: Moment;
  montantLoyerNu?: number;
  montantCharges?: number;
  periodiciteLoyer?: Periode;
  montantDepotGarantie?: number;
  infosComplementaires?: string;
  bienId?: number;
  locataireId?: number;
}

export class Contrat implements IContrat {
  constructor(
    public id?: number,
    public dateDebut?: Moment,
    public montantLoyerNu?: number,
    public montantCharges?: number,
    public periodiciteLoyer?: Periode,
    public montantDepotGarantie?: number,
    public infosComplementaires?: string,
    public bienId?: number,
    public locataireId?: number
  ) {}
}
