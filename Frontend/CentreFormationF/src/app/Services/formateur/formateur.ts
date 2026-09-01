import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enseignant } from '../../models/enseignant';

@Injectable({
  providedIn: 'root',
})
export class FormateurService {
    private apiURL="http://localhost:8083/api/enseignants";
constructor(private http:HttpClient) { }
  getAll(): Observable<Enseignant[]> {
    return this.http.get<Enseignant[]>(this.apiURL);
  }

  getById(id: number): Observable<Enseignant> {
    return this.http.get<Enseignant>(`${this.apiURL}/${id}`);
  }

  create(Formateur: Enseignant): Observable<Enseignant> {
    return this.http.post<Enseignant>(this.apiURL, Formateur);
  }

  update(id: number, Formateur: Enseignant): Observable<Enseignant> {
    return this.http.put<Enseignant>(`${this.apiURL}/${id}`, Formateur);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiURL}/${id}`);
  }

}
