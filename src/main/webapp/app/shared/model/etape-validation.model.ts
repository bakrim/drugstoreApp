import dayjs from 'dayjs';
import { IDossierPharmacie } from 'app/shared/model/dossier-pharmacie.model';
import { IDossierAutre } from 'app/shared/model/dossier-autre.model';

export interface IEtapeValidation {
  id?: number;
  libelle?: string | null;
  description?: string | null;
  date?: string | null;
  decision?: string | null;
  dossierPharmacie?: IDossierPharmacie | null;
  dossierAutre?: IDossierAutre | null;
}

export const defaultValue: Readonly<IEtapeValidation> = {};
