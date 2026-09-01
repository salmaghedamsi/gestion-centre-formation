import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { StudentService } from '../../Services/student/studentService';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-student-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './student-form.html',
  styleUrl: './student-form.css',
})
export class StudentForm {
  form: FormGroup;
  isEditMode = false;
  studentId: number | null = null;
  saving = false;

  constructor(
    private fb: FormBuilder,
    private studentService: StudentService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      phone: [''],
      birthDate: [''],
      level: [''],
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.isEditMode = true;
      this.studentId = Number(idParam);

      this.studentService.getById(this.studentId).subscribe({
        next: (student) => {
          this.form.patchValue({
            ...student,
            birthDate: this.formatDateForInput(student.birthDate)
          });
        },
        error: (err) => console.error('Erreur de chargement', err)
      });
    }
  }

  private formatDateForInput(date: string | null | undefined): string {
    if (!date) return '';
    return date.substring(0, 10);
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving = true;

    if (this.isEditMode && this.studentId) {
      this.studentService.update(this.studentId, this.form.value).subscribe({
        next: () => this.router.navigate(['/students']),
        error: (err) => {
          console.error('Erreur de modification', err);
          this.saving = false;
        }
      });
    } else {
      this.studentService.create(this.form.value).subscribe({
        next: () => this.router.navigate(['/students']),
        error: (err) => {
          console.error('Erreur de création', err);
          this.saving = false;
        }
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/students']);
  }

}