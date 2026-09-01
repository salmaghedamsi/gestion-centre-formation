import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { GroupStudent } from '../../models/group-student';
import { Payment } from '../../models/Payment';
import { PaymentSummary } from '../../models/payment-sammary';

import { GroupStudentService } from '../../Services/Group-students/group-student';
import { PaymentService } from '../../Services/payments/payment';

@Component({
  selector: 'app-payment-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './payment-detail.html',
  styleUrl: './payment-detail.css'
})
export class PaymentDetail implements OnInit {

  groupStudentId!: number;
  groupStudent: GroupStudent | null = null;
  payments: Payment[] = [];
  summary: PaymentSummary | null = null;

  nouvelleDatePaiement = '';
  nouveauMontant: number | null = null;
  nouvellesUnites: number | null = null;
  nouveauCommentaire = '';

  saving = false;

  constructor(
    private route: ActivatedRoute,
    private groupStudentService: GroupStudentService,
    private paymentService: PaymentService
  ) {}

  ngOnInit(): void {
    this.groupStudentId = Number(this.route.snapshot.paramMap.get('id'));
    this.chargerGroupStudent();
    this.chargerPaiements();
    this.chargerResume();
  }

  chargerGroupStudent(): void {
    this.groupStudentService.getById(this.groupStudentId).subscribe({
      next: (data) => this.groupStudent = data,
      error: (err) => console.error("Erreur de chargement de l'inscription", err)
    });
  }

  chargerPaiements(): void {
    this.paymentService.getByGroupStudent(this.groupStudentId).subscribe({
      next: (data) =>
        this.payments = data.sort((a, b) => b.paymentDate.localeCompare(a.paymentDate)),
      error: (err) => console.error('Erreur de chargement des paiements', err)
    });
  }

  chargerResume(): void {
    this.paymentService.getSummary(this.groupStudentId).subscribe({
      next: (data) => {
        this.summary = data;
        this.preRemplirUnites();
      },
      error: (err) => console.error('Erreur de chargement du résumé', err)
    });
  }

  preRemplirUnites(): void {
    if (!this.summary) return;

    const du = this.summary.unitsConsumed - this.summary.unitsPaid;
    this.nouvellesUnites = du > 0 ? du : 0;

    if (this.summary.pricePerUnit != null) {
      this.nouveauMontant = this.nouvellesUnites * this.summary.pricePerUnit;
    }
  }
  get estMensuel(): boolean {
    return this.summary?.paymentType === 'MONTHLY';
  }

    ajouterPaiement(): void {
    if (!this.nouveauMontant || !this.nouvelleDatePaiement) return;

    const payload: any = {
      groupStudentId: this.groupStudentId,
      paymentDate: this.nouvelleDatePaiement,
      amount: this.nouveauMontant,
      comment: this.nouveauCommentaire || null
    };

    if (this.estMensuel) {
      payload.monthsPaid = this.nouvellesUnites;
    } else {
      payload.sessionsPaid = this.nouvellesUnites;
    }

    this.saving = true;

    this.paymentService.create(payload).subscribe({
      next: () => {
        this.chargerPaiements();
        this.chargerResume(); // re-pré-remplit automatiquement via preRemplirUnites()
        this.nouvelleDatePaiement = '';
        this.nouveauCommentaire = '';
        this.saving = false;
      },
      error: (err) => {
        console.error("Erreur lors de l'ajout du paiement", err);
        this.saving = false;
      }
    });
  }

  supprimerPaiement(id: number | undefined): void {
    if (!id) return;
    if (!confirm('Supprimer ce paiement ?')) return;

    this.paymentService.delete(id).subscribe({
      next: () => {
        this.chargerPaiements();
        this.chargerResume();
      },
      error: (err) => console.error('Erreur lors de la suppression', err)
    });
  }
  onUnitesChange(): void {
    if (this.nouvellesUnites != null && this.summary?.pricePerUnit != null) {
      this.nouveauMontant = this.nouvellesUnites * this.summary.pricePerUnit;
    }
  }
  soldeLabel(): string {
    if (!this.summary) return '';
    const unite = this.estMensuel ? 'mois' : 'séance(s)';
    const b = this.summary.balanceUnits;
    if (b === 0) return 'À jour';
    if (b > 0) return `En avance de ${b} ${unite}`;
    return `En retard de ${Math.abs(b)} ${unite}`;
  }

  soldeClass(): string {
    if (!this.summary) return 'text-muted';
    if (this.summary.balanceUnits === 0) return 'text-success';
    if (this.summary.balanceUnits > 0) return 'text-info';
    return 'text-danger';
  }
}