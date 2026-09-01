import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs';
import { AuthService } from '../../Services/auth/auth';

@Component({
  selector: 'app-sidebar',
  imports: [CommonModule, RouterLink, RouterLinkActive, RouterOutlet],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
  afficherSidebar = true;
  anneeCourante = new Date().getFullYear();

  constructor(
    private router: Router,
    private authService: AuthService
  ) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      this.afficherSidebar = event.urlAfterRedirects !== '/login';
    });
  }

  get nomUtilisateur(): string | null {
    return localStorage.getItem('name');
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}