import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

interface LoginResponse {
  token: string;
  email: string;
  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8083/api/auth';

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${this.apiUrl}/login`,
      { email, password }
    ).pipe(
      tap(response => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('name', response.name);
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('name');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {

    const token = this.getToken();

    // Aucun token
    if (!token) {
      return false;
    }

    try {

      // Un JWT est composé de :
      // header.payload.signature

      const payload = JSON.parse(
        atob(token.split('.')[1])
      );

      // exp est en secondes
      const expiration = payload.exp * 1000;

      // Vérifier si la date actuelle dépasse la date d'expiration
      if (Date.now() >= expiration) {
        this.logout();
        return false;
      }

      return true;

    } catch (error) {

      // Token invalide
      this.logout();
      return false;
    }
  }

}