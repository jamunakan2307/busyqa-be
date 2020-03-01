import { LeadsCentralComponent } from './leads-central/leads-central.component';
import { TrainerInfoComponent } from './trainer-info/trainer-info.component';
import {ViewComponentComponent} from './view-component/view-component.component';
import {BatchEditComponent} from './batch-edit/batch-edit.component';
import {BatchAddComponent} from './batch-add/batch-add.component';
import {BatchListComponent} from './batch-list/batch-list.component';
import {CourseEditComponent} from './course-edit/course-edit.component';
import {CourseAddComponent} from './course-add/course-add.component';
import {CourseListComponent} from './course-list/course-list.component';
import {EditAluminiComponent} from './edit-alumini/edit-alumini.component';
import {EditResumeComponent} from './edit-resume/edit-resume.component';
import {InternResumeComponent} from './intern-resume/intern-resume.component';
import {ResumeListComponent} from './resume-list/resume-list.component';
import {LeadsListComponent} from './leads-list/leads-list.component';
import {UnregisteredClientComponent} from './unregistered-client/unregistered-client.component';
import {EditClientComponent} from './edit-client/edit-client.component';
import {ClientpanelComponent} from './clientpanel/clientpanel.component';
import {AdminclientignupComponent} from './adminclientignup/adminclientignup.component';
import {ClientListComponent} from './client-list/client-list.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {RegisterComponent} from './register/register.component';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {UserComponent} from './user/user.component';
import {PmComponent} from './pm/pm.component';
import {AdminComponent} from './admin/admin.component';
import {EditUserComponent} from './edit-user/edit-user.component';
import {AdminregisterComponentComponent} from "./adminregister/adminregister-component.component";
import {RegisteredClientComponent} from './registered-client/registered-client.component';
import {ThankYouComponent} from './thank-you/thank-you.component';
import {StudentListComponent} from './student-list/student-list.component';
import {EditStudentComponent} from './edit-student/edit-student.component';
import {PaymentStudentComponent} from './payment-student/payment-student.component';
import {InternListComponent} from './intern-list/intern-list.component';
import {EditInternComponent} from './edit-intern/edit-intern.component';
import {EditMockComponent} from './edit-mock/edit-mock.component';
import {MockListComponent} from './mock-list/mock-list.component';
import {AluminiListComponent} from './alumini-list/alumini-list.component';
import {TrainerListComponent} from './trainer-list/trainer-list.component';
import {TrainerAddComponent} from './trainer-add/trainer-add.component';
import {ViewTractionsComponent} from './view-tractions/view-tractions.component';
import {PayClientComponent} from './pay-client/pay-client.component';
import {ChangeStatusComponent} from './change-status/change-status.component';
import {SalesInternComponent} from './sales-intern/sales-intern.component';
import { ReportViewComponent } from './report-view/report-view.component';
import { StudentCentralComponent } from './student-central/student-central.component';
import { LocationListComponent } from './location-list/location-list.component';
import { LocationAddComponent } from './location-add/location-add.component';
import { LocationEditComponent } from './location-edit/location-edit.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'user',
    component: UserComponent
  },
  {
    path: 'pm',
    component: PmComponent
  },
  {
    path: 'sales-intern',
    component: SalesInternComponent
  },
  {
    path: 'leads-central',
    component: LeadsCentralComponent
  },
  {
    path: 'trainer-info',
    component: TrainerInfoComponent
  },
  {
    path: 'student-central',
    component: StudentCentralComponent
  },
  {
    path: 'admin',
    component: AdminComponent
  },
  {
    path: 'auth/login',
    component: LoginComponent
  },
  {
    path: 'edit-user',
    component: EditUserComponent
  },
  {
    path: 'client-list',
    component: ClientListComponent
  },
  {
    path: 'thank-you',
    component: ThankYouComponent
  },
  {
    path: 'view-component',
    component: ViewComponentComponent
  },
  {
    path: 'report-view',
    component: ReportViewComponent
  },
  {
    path: 'unregistered-client/:id',
    component: UnregisteredClientComponent
  },
  {
    path: 'registered-client/:id',
    component: RegisteredClientComponent
  },
  {
    path: 'pay-client',
    component: PayClientComponent
  },

  {
    path: 'change-status',
    component: ChangeStatusComponent
  },

  {
    path: 'view-tractions',
    component: ViewTractionsComponent
  },

  {
    path: 'course-list',
    component: CourseListComponent
  },

  {
    path: 'course-add',
    component: CourseAddComponent
  },
  {
    path: 'course-edit',
    component: CourseEditComponent
  },

  {
    path: 'location-list',
    component: LocationListComponent
  },

  {
    path: 'location-add',
    component: LocationAddComponent
  },
  {
    path: 'location-edit',
    component: LocationEditComponent
  },

  {
    path: 'batch-list',
    component: BatchListComponent
  },

  {
    path: 'batch-add',
    component: BatchAddComponent
  },
  {
    path: 'batch-edit',
    component: BatchEditComponent
  },

  {
    path: 'trainer-list',
    component: TrainerListComponent
  },

  {
    path: 'trainer-add',
    component: TrainerAddComponent
  },
  {
    path: 'signup',
    component: RegisterComponent
  },
  {
    path: 'leads-list',
    component: LeadsListComponent
  },
  {
    path: 'edit-resume',
    component: EditResumeComponent
  },
  {
    path: 'edit-mock',
    component: EditMockComponent
  },
  {
    path: 'edit-alumini',
    component: EditAluminiComponent
  },
  {
    path: 'alumini-list',
    component: AluminiListComponent
  },

  {
    path: 'intern-resume',
    component: InternResumeComponent
  },
  {
    path: 'edit-student',
    component: EditStudentComponent
  },
  {
    path: 'edit-intern',
    component: EditInternComponent
  },
  {
    path: 'intern-list',
    component: InternListComponent
  },
  {
    path: 'mock-list',
    component: MockListComponent
  },
  {
    path: 'payment-student',
    component: PaymentStudentComponent
  },
  {
    path: 'student-list',
    component: StudentListComponent
  },
  {
    path: 'resume-list',
    component: ResumeListComponent
  },
  {
    path: 'adminsignup',
    component: AdminregisterComponentComponent
  },
  {
    path: 'clientpanel',
    component: ClientpanelComponent
  },
  {
    path: 'edit-client',
    component: EditClientComponent
  },
  {
    path: 'adminclientignup',
    component: AdminclientignupComponent
  },

  {
    path: '',
    component: LoginComponent

  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
