import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface IRole {
  id?: number;
  libelle?: string | null;
  utilisateurLists?: IUtilisateur[] | null;
}

export const defaultValue: Readonly<IRole> = {};
