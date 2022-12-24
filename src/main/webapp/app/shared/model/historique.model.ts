import dayjs from 'dayjs';
import { ISession } from 'app/shared/model/session.model';

export interface IHistorique {
  id?: number;
  action?: string | null;
  date?: string | null;
  session?: ISession | null;
}

export const defaultValue: Readonly<IHistorique> = {};
