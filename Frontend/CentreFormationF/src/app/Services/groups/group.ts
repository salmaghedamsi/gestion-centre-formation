import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Groupe } from '../../models/group';
import { GroupStudent } from '../../models/group-student';
@Injectable({
  providedIn: 'root',
})
export class GroupService {
  private apiUrl = 'http://localhost:8083/api/groups';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Groupe[]> {
    return this.http.get<Groupe[]>(this.apiUrl);
  }

  getById(id: number): Observable<Groupe> {
    return this.http.get<Groupe>(`${this.apiUrl}/${id}`);
  }
  getStudentsByGroup(groupId: number): Observable<GroupStudent[]> {
  return this.http.get<GroupStudent[]>(`${this.apiUrl}/${groupId}/students`);
}

  create(group: any): Observable<Groupe> {
  return this.http.post<Groupe>(this.apiUrl, group);
}

update(id: number, group: any): Observable<Groupe> {
  return this.http.put<Groupe>(`${this.apiUrl}/${id}`, group);
}

  addStudent(groupId: number, studentId: number): Observable<Groupe> {
    return this.http.patch<Groupe>(`${this.apiUrl}/${groupId}/students/${studentId}`, {});
  }

  removeStudent(groupId: number, studentId: number): Observable<Groupe> {
    return this.http.delete<Groupe>(`${this.apiUrl}/${groupId}/students/${studentId}`);
  }
delete(id: number): Observable<void> {
  return this.http.delete<void>(`${this.apiUrl}/${id}`);
}

}
