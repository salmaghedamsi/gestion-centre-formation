import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Student } from '../../models/student';
import { Groupe } from '../../models/group';
import { GroupService } from '../../Services/groups/group';
import { StudentService } from '../../Services/student/studentService';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionService } from '../../Services/sessions/session';
import { Session } from '../../models/session';
import { GroupStudent } from '../../models/group-student';
import { Payment } from '../../models/Payment';
import { PaymentService } from '../../Services/payments/payment';
@Component({
  selector: 'app-group-detail',
  imports: [CommonModule, RouterLink,FormsModule],
  templateUrl: './group-detail.html',
  styleUrl: './group-detail.css',
})
export class GroupDetail implements OnInit {

  group: Groupe | null = null;
  eleves: Student[] = [];
  eleveASelectId: number | null = null;
  groupId!: number;
sessions: Session[] = [];
  nouvelleDate: string = '';
  nouvelleHeureDebut: string = '';
  nouvelleHeureFin: string = '';
  groupStudents: GroupStudent[] = [];
groupStudentOuvertId: number | null = null;
paiements: Payment[] = [];
nouveauMontant: number | null = null;
nouvelleDatePaiement: string = '';
  dateMinSeance(): string {
    if (!this.group?.startDate) return '';
    return this.group.startDate.substring(0, 10);
  }

  dateMaxSeance(): string {
    if (!this.group?.endDate) return '';
    return this.group.endDate.substring(0, 10);
  }
  constructor(
    private route: ActivatedRoute,
    private groupService: GroupService,
    private studentService: StudentService,
  private router: Router,
      private sessionService: SessionService,
      private paymentService: PaymentService

  ) { }

  ngOnInit(): void {
    this.groupId = Number(this.route.snapshot.paramMap.get('id'));
    this.chargerGroupe();
    this.chargerSessions();
    this.chargerGroupStudents();


    this.studentService.getAll().subscribe({
      next: (data) => this.eleves = data,
      error: (err: any) => console.error('Erreur de chargement des élèves', err)
    });
  }

  chargerGroupe(): void {
    this.groupService.getById(this.groupId).subscribe({
      next: (data) => this.group = data,
      error: (err: any) => console.error('Erreur de chargement du groupe', err)
    });
  }

  chargerSessions(): void {
    this.sessionService.getByGroup(this.groupId).subscribe({
      next: (data) => this.sessions = data.sort((a, b) => a.date.localeCompare(b.date)),
      error: (err: any) => console.error('Erreur de chargement des séances', err)
    });
  }

  
chargerGroupStudents(): void {
  this.groupService.getStudentsByGroup(this.groupId).subscribe({
    next: (data) => this.groupStudents = data,
    error: (err) => console.error('Erreur de chargement des élèves du groupe', err)
  });
}

inscriptionsActives() {
  return this.groupStudents.filter(gs => gs.active) ?? [];
}
    supprimerSession(id: number | undefined, event: Event): void {
    event.stopPropagation();
    if (!id) return;
    if (!confirm('Supprimer cette séance ?')) return;

    this.sessionService.delete(id).subscribe({
      next: () => this.chargerSessions(),
      error: (err: any) => console.error('Erreur lors de la suppression', err)
    });
  }



  elevesDisponibles(): Student[] {
    const idsInscrits = this.inscriptionsActives().map(gs => gs.student.id);
    return this.eleves.filter(e => !idsInscrits.includes(e.id));
  }
ajouterEleve(): void {
  if (!this.eleveASelectId) return;

  this.groupService.addStudent(this.groupId, this.eleveASelectId).subscribe({
    next: () => {
      this.chargerGroupStudents();
      this.eleveASelectId = null;
    },
    error: (err: any) => {
      const message = err?.error?.message || 'Erreur lors de l\'ajout de l\'élève';
      alert(message);
    }
  });
}
  groupeComplet(): boolean {
    if (!this.group) return false;
    return this.inscriptionsActives().length >= this.group.maxPlaces;
  }

retirerEleve(eleveId: number | undefined): void {
  if (!eleveId) return;

  this.groupService.removeStudent(this.groupId, eleveId).subscribe({
    next: () => this.chargerGroupStudents(),
    error: (err) => console.error('Erreur lors du retrait', err)
  });
}

supprimerGroupe(): void {
    if (!confirm('Voulez-vous vraiment supprimer ce groupe ?')) return;

    this.groupService.delete(this.groupId).subscribe({
      next: () => this.router.navigate(['/groups']),
      error: (err: any) => console.error('Erreur lors de la suppression', err)
    });
  }
  afficherGestionEleves = false;

toggleGestionEleves() {
  this.afficherGestionEleves = !this.afficherGestionEleves;
}

private avatarColors = ['avatar-1', 'avatar-2', 'avatar-3', 'avatar-4', 'avatar-5'];

avatarClass(id: number): string {
  return this.avatarColors[id % this.avatarColors.length];
}
toggleFichePaiement(groupStudentId: number | undefined): void {
  if (!groupStudentId) return;

  if (this.groupStudentOuvertId === groupStudentId) {
    this.groupStudentOuvertId = null;
    return;
  }

  this.groupStudentOuvertId = groupStudentId;
  this.chargerPaiements(groupStudentId);
}

chargerPaiements(groupStudentId: number): void {
  this.paymentService.getByGroupStudent(groupStudentId).subscribe({
    next: (data) => this.paiements = data,
    error: (err: any) => console.error('Erreur de chargement des paiements', err)
    });
}

ajouterSession(): void {
  if (!this.nouvelleDate) return;

  const min = this.dateMinSeance();
  const max = this.dateMaxSeance();

  if (min && this.nouvelleDate < min) {
    alert('La date de la séance ne peut pas être avant le début du groupe (' + min + ').');
    return;
  }
  if (max && this.nouvelleDate > max) {
    alert('La date de la séance ne peut pas être après la fin du groupe (' + max + ').');
    return;
  }

  if (this.nouvelleHeureDebut && this.nouvelleHeureFin && this.nouvelleHeureFin <= this.nouvelleHeureDebut) {
    alert('L\'heure de fin doit être après l\'heure de début.');
    return;
  }

  const payload = {
    groupId: this.groupId,
    date: this.nouvelleDate,
    startTime: this.nouvelleHeureDebut || null,
    endTime: this.nouvelleHeureFin || null,
    free: false
  };

  this.sessionService.create(payload).subscribe({
    next: () => {
      this.chargerSessions();
      this.nouvelleDate = '';
      this.nouvelleHeureDebut = '';
      this.nouvelleHeureFin = '';
    },
    error: (err: any) => {
      const message = err?.error?.message || 'Erreur lors de la création de la séance';
      alert(message);
    }
  });
}}