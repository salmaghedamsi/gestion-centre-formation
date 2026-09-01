import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GroupStudent } from '../../models/group-student';

@Injectable({ providedIn: 'root' })
export class GroupStudentService {
  private apiUrl = 'http://localhost:8083/api/group-students';

  constructor(private http: HttpClient) {}

  getById(id: number): Observable<GroupStudent> {
    return this.http.get<GroupStudent>(`${this.apiUrl}/${id}`);
  }
}