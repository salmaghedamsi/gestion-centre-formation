import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormationGroup } from '../../models/formation-group';
import { FormationGroupService } from '../../Services/formations/formation-group';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-formation-group-list',
  standalone: true,
  imports: [CommonModule, RouterLink,FormsModule],
  templateUrl: './formation-group-list.html',
  styleUrl: './formation-group-list.css'
})
export class FormationGroupList implements OnInit {

  formations: FormationGroup[] = [];

  constructor(private formationGroupService: FormationGroupService) { }

  ngOnInit(): void {
    this.formationGroupService.getAll().subscribe({
      next: (data) => this.formations = data,
      error: (err) => console.error('Erreur lors du chargement des formations', err)
    });
  }

  supprimer(id: number | undefined): void {
    if (!id) return;
    if (!confirm('Voulez-vous vraiment supprimer cette formation ?')) return;

    this.formationGroupService.delete(id).subscribe({
      next: () => this.formations = this.formations.filter(f => f.id !== id),
      error: (err) => console.error('Erreur lors de la suppression', err)
    });
  }
  searchTerm: string = '';

filteredFormations() {
  const term = this.searchTerm.trim().toLowerCase();
  if (!term) return this.formations;
  return this.formations.filter(f =>
    f.Subject.toLowerCase().includes(term)
  );
}

avatarClass(i: number): string {
  return `avatar-${(i % 5) + 1}`;
}

countByPayment(type: string): number {
  return this.formations.filter(f => f.paymentType === type).length;
}

prixMoyen(): string {
  if (this.formations.length === 0) return '0';
  const total = this.formations.reduce((sum, f) => sum + f.Price, 0);
  return (total / this.formations.length).toFixed(0);
}

}