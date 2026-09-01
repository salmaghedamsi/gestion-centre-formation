import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { GroupService } from '../../Services/groups/group';
import { FormateurService } from '../../Services/formateur/formateur';
import { FormationGroupService } from '../../Services/formations/formation-group';
import { Enseignant } from '../../models/enseignant';
import { FormationGroup } from '../../models/formation-group';

@Component({
  selector: 'app-group-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './group-form.html',
  styleUrl: './group-form.css'
})
export class GroupForm implements OnInit {

  form: FormGroup;
  formateurs: Enseignant[] = [];
  formations: FormationGroup[] = [];
  isEditMode = false;
  groupId: number | null = null;
  saving = false;

  constructor(
    private fb: FormBuilder,
    private groupService: GroupService,
    private formateurService: FormateurService,
    private formationGroupService: FormationGroupService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      maxPlaces: [20, [Validators.required, Validators.min(1)]],
      enseignantId: ['', Validators.required],
      formationGroupId: ['', Validators.required]
    }, { validators: this.dateRangeValidator });
  }

  ngOnInit(): void {
    this.formateurService.getAll().subscribe({
      next: (data) => this.formateurs = data,
      error: (err) => console.error('Erreur de chargement des formateurs', err)
    });

    this.formationGroupService.getAll().subscribe({
      next: (data) => {
        this.formations = data;
        this.chargerGroupeSiEdition();
      },
      error: (err) => console.error('Erreur de chargement des formations', err)
    });
  }

  formationSelectionnee(): FormationGroup | undefined {
    const id = this.form.get('formationGroupId')?.value;
    return this.formations.find(f => f.id === Number(id));
  }

  private dateRangeValidator(group: AbstractControl) {
    const start = group.get('startDate')?.value;
    const end = group.get('endDate')?.value;
    if (start && end && new Date(end) <= new Date(start)) {
      return { dateRangeInvalid: true };
    }
    return null;
  }

  chargerGroupeSiEdition(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.isEditMode = true;
      this.groupId = Number(idParam);

      this.groupService.getById(this.groupId).subscribe({
        next: (group) => {
          this.form.patchValue({
            startDate: group.startDate,
            endDate: group.endDate,
            maxPlaces: group.maxPlaces,
            enseignantId: group.teacher?.id,
            formationGroupId: group.formationGroup?.id
          });
        },
        error: (err) => console.error('Erreur de chargement du groupe', err)
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload = {
      startDate: this.form.value.startDate,
      endDate: this.form.value.endDate,
      maxPlaces: this.form.value.maxPlaces,
      enseignantId: this.form.value.enseignantId,
      formationGroupId: this.form.value.formationGroupId
    };

    this.saving = true;

    if (this.isEditMode && this.groupId) {
      this.groupService.update(this.groupId, payload).subscribe({
        next: () => this.router.navigate(['/groups']),
        error: (err) => {
          console.error('Erreur de modification', err);
          this.saving = false;
        }
      });
    } else {
      this.groupService.create(payload).subscribe({
        next: () => this.router.navigate(['/groups']),
        error: (err) => {
          console.error('Erreur de création', err);
          this.saving = false;
        }
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/groups']);
  }

}