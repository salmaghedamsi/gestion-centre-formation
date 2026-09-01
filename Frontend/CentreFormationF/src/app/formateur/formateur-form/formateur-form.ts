import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormateurService } from '../../Services/formateur/formateur';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-formateur-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './formateur-form.html',
  styleUrl: './formateur-form.css',
})
export class FormateurForm implements OnInit {

  form: FormGroup;
  isEditMode = false;
  formateurId: number | null = null;
saving = false;
  constructor(
    private fb: FormBuilder,
    private formateurService: FormateurService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.email],
      phone: [''],
      speciality: ['']
       });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.isEditMode = true;
      this.formateurId = Number(idParam);

      this.formateurService.getById(this.formateurId).subscribe({
        next: (formateur) => this.form.patchValue(formateur),
        error: (err) => console.error('Erreur de chargement', err)
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }if (this.isEditMode && this.formateurId) {
      this.formateurService.update(this.formateurId, this.form.value).subscribe({
        next: () => this.router.navigate(['/enseignants']),
        error: (err) => console.error('Erreur de modification', err)
      });
    } else {
      this.formateurService.create(this.form.value).subscribe({
        next: () => this.router.navigate(['/enseignants']),
        error: (err) => console.error('Erreur de création', err)
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/enseignants']);
  }
}