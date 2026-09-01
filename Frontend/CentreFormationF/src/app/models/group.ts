import { Enseignant } from './enseignant';
import { FormationGroup } from './formation-group';
import { GroupStudent } from './group-student';

export type PaymentType = 'MONTHLY' | 'PER_SESSION';

export interface Groupe {
  id?: number;
  name: string;
  paymentType: PaymentType;
  startDate: string;
  endDate: string;
  maxPlaces: number;
  activeStudentsCount?: number;
  teacher?: Enseignant;
  formationGroup?: FormationGroup;
  groupStudents?: GroupStudent[];
}