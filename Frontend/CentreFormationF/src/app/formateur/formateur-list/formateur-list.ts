import { Component } from '@angular/core';
import { Enseignant } from '../../models/enseignant';
import { FormateurService } from '../../Services/formateur/formateur';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-formateur-list',
  imports: [RouterLink,CommonModule,FormsModule],
  templateUrl: './formateur-list.html',
  styleUrl: './formateur-list.css',
})
export class FormateurList {
 formateur: Enseignant[] = [];
 errorPopup: string | null = null;


  constructor(private formateurService: FormateurService) {}

  ngOnInit(): void {
    this.loadFormateurs();
  }
searchTerm: string = '';

private colorClasses = ['badge-color-1', 'badge-color-2', 'badge-color-3', 'badge-color-4', 'badge-color-5'];
private statColorClasses = ['stat-purple', 'stat-teal', 'stat-coral', 'stat-pink', 'stat-blue'];

filteredFormateur() {
  const term = this.searchTerm.trim().toLowerCase();
  if (!term) return this.formateur;
  return this.formateur.filter(f =>
    f.firstName.toLowerCase().includes(term) ||
    f.lastName.toLowerCase().includes(term) ||
    f.speciality.toLowerCase().includes(term)
  );
}

avatarClass(i: number): string {
  return `avatar-${(i % 5) + 1}`;
}

uniqueSpecialites(): string[] {
  return [...new Set(this.formateur.map(f => f.speciality))];
}

topSpecialites(): string[] {
  // Affiche jusqu'à 2 spécialités les plus fréquentes pour compléter les cartes de stats
  const counts = this.uniqueSpecialites()
    .map(spec => ({ spec, count: this.countBySpecialite(spec) }))
    .sort((a, b) => b.count - a.count);
  return counts.slice(0, 2).map(c => c.spec);
}

countBySpecialite(spec: string): number {
  return this.formateur.filter(f => f.speciality === spec).length;
}

specialiteColorClass(spec: string): string {
  const index = this.hashString(spec) % this.colorClasses.length;
  return this.colorClasses[index];
}

statColorClass(i: number): string {
  return this.statColorClasses[i % this.statColorClasses.length];
}

private hashString(str: string): number {
  let hash = 0;
  for (let i = 0; i < str.length; i++) {
    hash = str.charCodeAt(i) + ((hash << 5) - hash);
  }
  return Math.abs(hash);
}
  loadFormateurs(): void {
    this.formateurService.getAll().subscribe({
      next: (data) => {
        this.formateur = data;
      },
      error: (err) => {
        console.error('Error fetching formateurs:', err);
      }
    });
  }
    onDelete(id: number): void {
    if (!confirm('Voulez-vous vraiment supprimer cet enseignant ?')) {
      return;
    }
    this.formateurService.delete(id).subscribe({
      next: () => {
        this.formateur = this.formateur.filter(f => f.id !== id);
      },
      error: (err) => {
        this.errorPopup = err?.error?.message || 'Erreur lors de la suppression de l\'enseignant.';
      }
    });
  }
}
