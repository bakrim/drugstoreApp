import { ICommission } from 'app/shared/model/commission.model';

export interface IRepresentant {
  id?: number;
  entite?: string | null;
  nom?: string | null;
  prenom?: string | null;
  telephone?: string | null;
  cIN?: string | null;
  commissionLists?: ICommission[] | null;
}

export const defaultValue: Readonly<IRepresentant> = {};
