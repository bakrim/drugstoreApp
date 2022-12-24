import { ILocal } from 'app/shared/model/local.model';

export interface IZone {
  id?: number;
  libelle?: string | null;
  localLists?: ILocal[] | null;
}

export const defaultValue: Readonly<IZone> = {};
