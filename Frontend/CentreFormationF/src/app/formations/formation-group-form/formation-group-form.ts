import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormationGroupService } from '../../Services/formations/formation-group';

@Component({
  selector: 'app-formation-group-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './formation-group-form.html',
  styleUrl: './formation-group-form.css'
})
export class FormationGroupForm implements OnInit {

  form: FormGroup;
  isEditMode = false;
  formationId: number | null = null;
  saving = false;

  constructor(
    private fb: FormBuilder,
    private formationGroupService: FormationGroupService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      Subject: ['', Validators.required],
      Price: [0, [Validators.required, Validators.min(0)]],
      paymentType: ['MONTHLY', Validators.required]
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.isEditMode = true;
      this.formationId = Number(idParam);

      this.formationGroupService.getById(this.formationId).subscribe({
        next: (data) => this.form.patchValue(data),
        error: (err) => console.error('Erreur de chargement', err)
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    if (this.isEditMode && this.formationId) {
      this.formationGroupService.update(this.formationId, this.form.value).subscribe({
        next: () => this.router.navigate(['/formations']),
        error: (err) => console.error('Erreur de modification', err)
      });
    } else {
      this.formationGroupService.create(this.form.value).subscribe({
        next: () => this.router.navigate(['/formations']),
        error: (err) => console.error('Erreur de création', err)
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/formations']);
  }

}