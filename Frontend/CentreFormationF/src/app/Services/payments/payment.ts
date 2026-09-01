import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Payment } from '../../models/Payment';
import { PaymentSummary } from '../../models/payment-sammary';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private apiUrl = 'http://localhost:8083/api/payments';

  constructor(private http: HttpClient) { }

  getByGroupStudent(groupStudentId: number): Observable<Payment[]> {
    return this.http.get<Payment[]>(`${this.apiUrl}/group-student/${groupStudentId}`);
  }

  create(payment: any): Observable<Payment> {
    return this.http.post<Payment>(this.apiUrl, payment);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
getSummary(groupStudentId: number): Observable<PaymentSummary> {
    return this.http.get<PaymentSummary>(`${this.apiUrl}/group-student/${groupStudentId}/summary`);
  }
}