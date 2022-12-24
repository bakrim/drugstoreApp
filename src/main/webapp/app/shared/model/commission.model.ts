import dayjs from 'dayjs';
import { IRepresentant } from 'app/shared/model/representant.model';
import { IDossierPharmacie } from 'app/shared/model/dossier-pharmacie.model';

export interface ICommission {
  id?: number;
  date?: string | null;
  decision?: string | null;
  motif?: string | null;
  representantLists?: IRepresentant[] | null;
  dossierPharmacie?: IDossierPharmacie | null;
}

export const defaultValue: Readonly<ICommission> = {};
