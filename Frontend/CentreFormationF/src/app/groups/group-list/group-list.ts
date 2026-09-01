import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { GroupService } from '../../Services/groups/group';
import { Groupe } from '../../models/group';

@Component({
  selector: 'app-group-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './group-list.html',
  styleUrl: './group-list.css',
})
export class GroupList implements OnInit {
  groups: Groupe[] = [];

  constructor(private groupService: GroupService) { }

  ngOnInit(): void {
    this.groupService.getAll().subscribe({
      next: (data) => this.groups = data,
      error: (err) => console.error('Erreur lors du chargement des groupes', err)
    });
  }

  supprimer(id: number | undefined, event: Event): void {
    event.stopPropagation();

    if (!id) return;

    if (!confirm('Voulez-vous vraiment supprimer ce groupe ?')) return;

    this.groupService.delete(id).subscribe({
      next: () => {
        this.groups = this.groups.filter(g => g.id !== id);
      },
error: (err: any) => {
  const message = err?.error?.message || 'Erreur lors de la suppression';
  alert(message);
}    });
  }

  tauxRemplissage(group: Groupe): number {
    const inscrits = group.activeStudentsCount || 0;
    if (!group.maxPlaces) return 0;
    return Math.min((inscrits / group.maxPlaces) * 100, 100);
  }

  capaciteRestante(group: Groupe): number {
    const inscrits = group.activeStudentsCount || 0;
    return Math.max(group.maxPlaces - inscrits, 0);
  }

capaciteClass(group: any): string {
  const taux = this.tauxRemplissage(group);
  if (taux >= 90) return 'capacite-pleine';
  if (taux >= 60) return 'capacite-moyenne';
  return 'capacite-ok';
}

}