import { IDossierPharmacie } from 'app/shared/model/dossier-pharmacie.model';
import { IDossierAutre } from 'app/shared/model/dossier-autre.model';
import { IZone } from 'app/shared/model/zone.model';

export interface ILocal {
  id?: number;
  longitude?: number | null;
  latitude?: number | null;
  adresse?: string | null;
  dossierPharmacieLists?: IDossierPharmacie[] | null;
  dossierAutreLists?: IDossierAutre[] | null;
  zone?: IZone | null;
}

export const defaultValue: Readonly<ILocal> = {};
