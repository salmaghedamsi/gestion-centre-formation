import { Component } from '@angular/core';

import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../Services/auth/auth';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatCheckboxModule,
    MatFormFieldModule,
    ReactiveFormsModule,CommonModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})

export class Login {
form: FormGroup;
  erreur: string | null = null;
  chargement = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    console.log('onSubmit appelé', this.form.value, this.form.valid);

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
this.erreur = null;
    this.chargement = true;

    const { email, password } = this.form.value;

    this.authService.login(email, password).subscribe({
      next: () => {
        this.chargement = false;
        this.router.navigate(['/students']);
      },
      error: (err) => {
        this.chargement = false;
        this.erreur = 'Email ou mot de passe incorrect.';
      }
    });
  }

}