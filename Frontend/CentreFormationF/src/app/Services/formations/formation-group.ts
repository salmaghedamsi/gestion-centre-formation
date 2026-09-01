import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FormationGroup } from '../../models/formation-group';

@Injectable({
  providedIn: 'root',
})
export class FormationGroupService {
  private apiUrl="http://localhost:8083/api/formation-groups";
  constructor(private http: HttpClient) {
  }
  getAll(): Observable<FormationGroup[]> {
    return this.http.get<FormationGroup[]>(this.apiUrl);
  }
  getById(id:number): Observable<FormationGroup> {
    return this.http.get<FormationGroup>(`${this.apiUrl}/${id}`);
  }
  create(formationGroup: FormationGroup): Observable<FormationGroup> {
    return this.http.post<FormationGroup>(this.apiUrl, formationGroup);
  }
  update(id: number, formationGroup: FormationGroup): Observable<FormationGroup> {
    return this.http.put<FormationGroup>(`${this.apiUrl}/${id}`, formationGroup);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
