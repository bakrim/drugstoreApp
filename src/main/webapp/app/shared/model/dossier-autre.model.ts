import dayjs from 'dayjs';
import { IEtapeValidation } from 'app/shared/model/etape-validation.model';
import { IUser } from 'app/shared/model/user.model';
import { IDocument } from 'app/shared/model/document.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { ILocal } from 'app/shared/model/local.model';

export interface IDossierAutre {
  id?: number;
  numero?: string | null;
  numeroEnvoi?: string | null;
  dateDepot?: string | null;
  dateEnvoi?: string | null;
  typeDemendeur?: string | null;
  profession?: string | null;
  nature?: string | null;
  statut?: string | null;
  dateDerniereModif?: string | null;
  commentaire?: string | null;
  nom?: string | null;
  prenom?: string | null;
  telephone?: string | null;
  cIN?: string | null;
  raisonSocial?: string | null;
  iCE?: string | null;
  numRC?: string | null;
  iF?: string | null;
  numAffiliation?: string | null;
  etapeValidationLists?: IEtapeValidation[] | null;
  user?: IUser | null;
  documentLists?: IDocument[] | null;
  utilisateur?: IUtilisateur | null;
  local?: ILocal | null;
}

export const defaultValue: Readonly<IDossierAutre> = {};
