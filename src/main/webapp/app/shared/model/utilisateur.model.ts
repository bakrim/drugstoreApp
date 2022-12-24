import { IDossierPharmacie } from 'app/shared/model/dossier-pharmacie.model';
import { IDossierAutre } from 'app/shared/model/dossier-autre.model';
import { ISession } from 'app/shared/model/session.model';
import { IRole } from 'app/shared/model/role.model';

export interface IUtilisateur {
  id?: number;
  email?: string | null;
  username?: string | null;
  password?: string | null;
  active?: boolean | null;
  nom?: string | null;
  prenom?: string | null;
  telephone?: string | null;
  dossierPharmacieLists?: IDossierPharmacie[] | null;
  dossierAutreLists?: IDossierAutre[] | null;
  sessionLists?: ISession[] | null;
  roleLists?: IRole[] | null;
}

export const defaultValue: Readonly<IUtilisateur> = {
  active: false,
};
