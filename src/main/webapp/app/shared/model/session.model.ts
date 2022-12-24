import dayjs from 'dayjs';
import { IHistorique } from 'app/shared/model/historique.model';
import { IUser } from 'app/shared/model/user.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface ISession {
  id?: number;
  adresseIp?: string | null;
  dateConnect?: string | null;
  dateDeconnect?: string | null;
  historiqueLists?: IHistorique[] | null;
  user?: IUser | null;
  utilisateur?: IUtilisateur | null;
}

export const defaultValue: Readonly<ISession> = {};
