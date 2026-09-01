export interface Payment {
  id?: number;
  groupStudentId?: number;
  paymentDate: string;
  amount: number;
  monthsPaid?: number;
  sessionsPaid?: number;
  comment?: string;
}