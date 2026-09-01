import { Component, OnInit } from '@angular/core';
import { StudentService } from '../../Services/student/studentService';
import { Student } from '../../models/student';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-students-list',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './students-list.html',
  styleUrl: './students-list.css',
})
export class StudentsList implements OnInit {
  students: Student[] = [];

  constructor(private studentService: StudentService) {}

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents(): void {
    this.studentService.getAll().subscribe({
      next: (data) => {
        this.students = data;
      },
      error: (err) => {
        console.error('Error fetching students:', err);
      }
    });
  }
    onDelete(id: number): void {
    if (!confirm('Voulez-vous vraiment supprimer cet élève ?')) {
      return;
    }
    this.studentService.delete(id).subscribe({
      next: () => {
        this.students = this.students.filter(s => s.id !== id);
      },
      error: (err) => {
        const message = err?.error?.message || 'Erreur lors de la suppression de l\'élève.';
        alert(message);
      }
    });
  }
  searchTerm: string = '';

private avatarClasses = ['avatar-1', 'avatar-2', 'avatar-3', 'avatar-4', 'avatar-5'];

filteredStudents() {
  if (!this.searchTerm?.trim()) {
    return this.students;
  }
  const term = this.searchTerm.toLowerCase();
  return this.students.filter(s =>
    s.firstName.toLowerCase().includes(term) ||
    s.lastName.toLowerCase().includes(term) ||
    s.level.toLowerCase().includes(term)
  );
}

avatarClass(index: number): string {
  return this.avatarClasses[index % this.avatarClasses.length];
}

categoryOf(level: string | null | undefined): 'Primaire' | 'Collège' | 'Lycée' {

  if (!level) {
    return 'Primaire';
  }

  const n = level.toLowerCase();

  if (n.includes('bac') || n.includes('lycée') || n.includes('lycee')) {
    return 'Lycée';
  }

  if (
    ['7', '8', '9', 'collège', 'college']
      .some(k => n.includes(k))
  ) {
    return 'Collège';
  }

  return 'Primaire';
}

countByCategory(cat: 'Primaire' | 'Collège' | 'Lycée'): number {
  return this.students.filter(s => this.categoryOf(s.level) === cat).length;
}

}