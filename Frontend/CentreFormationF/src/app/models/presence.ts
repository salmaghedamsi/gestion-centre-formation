import { Student } from './student';

export interface Presence {
  studentId: number;
  firstName: string;
  lastName: string;
  present: boolean;
}
export interface PresenceRequest {
  studentId: number;
  present: boolean;
}