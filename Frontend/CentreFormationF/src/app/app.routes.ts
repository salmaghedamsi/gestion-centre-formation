import { Routes } from '@angular/router';
import { StudentsList } from './Student/students-list/students-list';
import { StudentForm } from './Student/student-form/student-form';
import { FormateurList } from './formateur/formateur-list/formateur-list';
import { FormateurForm } from './formateur/formateur-form/formateur-form';
import { GroupList } from './groups/group-list/group-list';
import { GroupForm } from './groups/group-form/group-form';
import { GroupDetail } from './groups/group-detail/group-detail';
import { Login } from './auth/login/login';
import { authGuard } from './core/auth-guard';
import { FormationGroupList } from './formations/formation-group-list/formation-group-list';
import { FormationGroupForm } from './formations/formation-group-form/formation-group-form';
import { SessionDetail } from './sessions/session-detail/session-detail';
import { PaymentDetail } from './payments/payment-detail/payment-detail';
export const routes: Routes = [
   { path: 'login', component: Login },

  { path: '', redirectTo: 'students', pathMatch: 'full' },

  { path: 'students', component: StudentsList, canActivate: [authGuard] },
  { path: 'student/add', component: StudentForm, canActivate: [authGuard] },
  { path: 'student/edit/:id', component: StudentForm, canActivate: [authGuard] },

  { path: 'enseignants', component: FormateurList, canActivate: [authGuard] },
  { path: 'enseignant/add', component: FormateurForm, canActivate: [authGuard] },
  { path: 'enseignant/edit/:id', component: FormateurForm, canActivate: [authGuard] },

  { path: 'groups', component: GroupList, canActivate: [authGuard] },
  { path: 'groups/add', component: GroupForm, canActivate: [authGuard] },
  { path: 'groups/edit/:id', component: GroupForm, canActivate: [authGuard] },
  { path: 'groups/:id', component: GroupDetail, canActivate: [authGuard] },
  { path: 'formations', component: FormationGroupList, canActivate: [authGuard] },
{ path: 'formations/add', component: FormationGroupForm, canActivate: [authGuard] },
{ path: 'formations/edit/:id', component: FormationGroupForm, canActivate: [authGuard] },
{ path: 'sessions/:id', component: SessionDetail, canActivate: [authGuard] },
  { path: 'group-students/:id/payments', component: PaymentDetail, canActivate: [authGuard] },
]