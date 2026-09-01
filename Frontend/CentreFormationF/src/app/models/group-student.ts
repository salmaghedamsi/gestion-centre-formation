import { Enseignant } from './enseignant';
import { Groupe } from './group';
import { Student } from './student';

export interface GroupStudent {
  id?: number;
  group?: Groupe;
  student: Student;
  startDate: string;
  endDate?: string;
  active: boolean;
}