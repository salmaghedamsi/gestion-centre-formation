import { Groupe } from './group';

export interface Session {
  id?: number;
  group?: Groupe;
  date: string;
  startTime?: string;
  endTime?: string;
  free: boolean;
  comment?: string;
}