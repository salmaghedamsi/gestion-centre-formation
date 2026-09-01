import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Session } from '../../models/session';
import { Presence ,PresenceRequest} from '../../models/presence';
@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private apiUrl = 'http://localhost:8083/api/sessions';
  private presenceUrl = 'http://localhost:8083/api/presences';

  constructor(private http: HttpClient) {}

  getByGroup(groupId: number): Observable<Session[]> {
    return this.http.get<Session[]>(
      `${this.apiUrl}/group/${groupId}`
    );
  }

  getById(id: number): Observable<Session> {
    return this.http.get<Session>(
      `${this.apiUrl}/${id}`
    );
  }

  create(session: any): Observable<Session> {
    return this.http.post<Session>(
      this.apiUrl,
      session
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );
  }

  // ============================
  // Présences
  // ============================

  getPresencesBySession(
    sessionId: number
  ): Observable<Presence[]> {

    return this.http.get<Presence[]>(
      `${this.presenceUrl}/session/${sessionId}`
    );
  }

  savePresences(
    sessionId: number,
    presences: PresenceRequest[]
  ): Observable<Presence[]> {

    return this.http.put<Presence[]>(
      `${this.presenceUrl}/session/${sessionId}`,
      presences
    );
  }
}