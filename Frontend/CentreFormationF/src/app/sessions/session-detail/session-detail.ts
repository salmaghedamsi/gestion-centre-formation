import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { Session } from '../../models/session';
import { Presence, PresenceRequest } from '../../models/presence';
import { Student } from '../../models/student';

import { SessionService } from '../../Services/sessions/session';
import { GroupService } from '../../Services/groups/group';

@Component({
  selector: 'app-session-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './session-detail.html',
  styleUrl: './session-detail.css'
})
export class SessionDetail implements OnInit {

  session: Session | null = null;

  presences: Presence[] = [];

  elevesGroupe: Student[] = [];

  sessionId!: number;

  saving = false;

  constructor(
    private route: ActivatedRoute,
    private sessionService: SessionService,
    private groupService: GroupService,
  ) {}

  ngOnInit(): void {

    this.sessionId = Number(
      this.route.snapshot.paramMap.get('id')
    );

    this.chargerSession();
  }

  chargerSession(): void {

    this.sessionService.getById(this.sessionId).subscribe({

      next: (data) => {

        this.session = data;

        this.chargerElevesDuGroupe(
          data.group!.id!
        );

        this.chargerPresences();
      },

      error: (err: any) => {
        console.error(
          'Erreur de chargement de la séance',
          err
        );
      }

    });
  }

  chargerElevesDuGroupe(groupId: number): void {

    this.groupService.getStudentsByGroup(groupId).subscribe({

      next: (groupStudents) => {

        this.elevesGroupe = groupStudents
          .filter(gs => gs.active)
          .map(gs => gs.student);

      },

      error: (err: any) => {
        console.error(
          'Erreur de chargement des élèves',
          err
        );
      }

    });
  }

  chargerPresences(): void {

    this.sessionService
      .getPresencesBySession(this.sessionId)
      .subscribe({

        next: (data) => {
          this.presences = data;
        },

        error: (err: any) => {
          console.error(
            'Erreur de chargement des présences',
            err
          );
        }

      });
  }

  estPresent(studentId: number | undefined): boolean {

    if (!studentId) {
      return false;
    }

    const presence = this.presences.find(
      p => p.studentId === studentId
    );

    return presence?.present ?? false;
  }

  aUneFiche(studentId: number | undefined): boolean {

    if (!studentId) {
      return false;
    }

    return this.presences.some(
      p => p.studentId === studentId
    );
  }

  togglePresence(studentId: number | undefined): void {

    if (!studentId) {
      return;
    }

    const presence = this.presences.find(
      p => p.studentId === studentId
    );

    if (!presence) {
      return;
    }

    // Modification UNIQUEMENT côté Angular
    presence.present = !presence.present;
  }

  savePresences(): void {

    this.saving = true;

    const requests: PresenceRequest[] =
      this.presences.map(presence => ({
        studentId: presence.studentId,
        present: presence.present
      }));

    this.sessionService
      .savePresences(this.sessionId, requests)
      .subscribe({

        next: (data) => {

          this.presences = data;

          this.saving = false;

          console.log(
            'Toutes les présences ont été enregistrées'
          );
        },

        error: (err: any) => {

          console.error(
            'Erreur lors de l’enregistrement des présences',
            err
          );

          this.saving = false;
        }

      });
  }

  nombrePresents(): number {

    return this.presences.filter(
      presence => presence.present
    ).length;
  }

  nombreAbsents(): number {

    return this.presences.filter(
      presence => !presence.present
    ).length;
  }

  nombreAMarquer(): number {

    return this.elevesGroupe.filter(
      eleve => !this.aUneFiche(eleve.id)
    ).length;
  }
}