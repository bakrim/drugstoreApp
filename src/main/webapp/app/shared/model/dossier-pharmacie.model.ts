import dayjs from 'dayjs';
import { IEtapeValidation } from 'app/shared/model/etape-validation.model';
import { ICommission } from 'app/shared/model/commission.model';
import { IUser } from 'app/shared/model/user.model';
import { IDocument } from 'app/shared/model/document.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { ILocal } from 'app/shared/model/local.model';

export interface IDossierPharmacie {
  id?: number;
  numero?: string | null;
  dateDepot?: string | null;
  nature?: string | null;
  statut?: string | null;
  dateDerniereModif?: string | null;
  commentaire?: string | null;
  nom?: string | null;
  prenom?: string | null;
  telephone?: string | null;
  cIN?: string | null;
  etapeValidationLists?: IEtapeValidation[] | null;
  commisionLists?: ICommission[] | null;
  user?: IUser | null;
  documentLists?: IDocument[] | null;
  utilisateur?: IUtilisateur | null;
  local?: ILocal | null;
}

export const defaultValue: Readonly<IDossierPharmacie> = {};
