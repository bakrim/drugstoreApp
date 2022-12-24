import dayjs from 'dayjs';
import { IDossierPharmacie } from 'app/shared/model/dossier-pharmacie.model';
import { IDossierAutre } from 'app/shared/model/dossier-autre.model';

export interface IDocument {
  id?: number;
  name?: string | null;
  thumbnailName?: string | null;
  mimeType?: string | null;
  thumbnailPath?: string | null;
  downloadPath?: string | null;
  thumbnailDownloadPath?: string | null;
  uploadDate?: string | null;
  path?: string | null;
  documentSize?: number | null;
  dossierPharmacieLists?: IDossierPharmacie[] | null;
  dossierAutreLists?: IDossierAutre[] | null;
}

export const defaultValue: Readonly<IDocument> = {};
