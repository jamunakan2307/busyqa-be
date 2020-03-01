import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {UserComponent} from './user/user.component';
import {RegisterComponent} from './register/register.component';
import {HomeComponent} from './home/home.component';
import {AdminComponent} from './admin/admin.component';
import {PmComponent} from './pm/pm.component';
import {EditUserComponent} from './edit-user/edit-user.component';
import {NgMultiSelectDropDownModule} from 'ng-multiselect-dropdown';

import {httpInterceptorProviders} from './auth/auth-interceptor';
import {AdminregisterComponentComponent} from './adminregister/adminregister-component.component';
import {ClientListComponent} from './client-list/client-list.component';
import {AdminclientignupComponent} from './adminclientignup/adminclientignup.component';
import {ClientpanelComponent} from './clientpanel/clientpanel.component';
import {EditClientComponent} from './edit-client/edit-client.component';
import {UnregisteredClientComponent} from './unregistered-client/unregistered-client.component';
import {RegisteredClientComponent} from './registered-client/registered-client.component';
import {ThankYouComponent} from './thank-you/thank-you.component';
import {FileUploadModule} from 'ng2-file-upload';
import {LeadsListComponent} from './leads-list/leads-list.component';
import {StudentListComponent} from './student-list/student-list.component';
import {EditStudentComponent} from './edit-student/edit-student.component';
import {PaymentStudentComponent} from './payment-student/payment-student.component';
import {InternListComponent} from './intern-list/intern-list.component';
import {EditInternComponent} from './edit-intern/edit-intern.component';
import {ResumeListComponent} from './resume-list/resume-list.component';
import {InternResumeComponent} from './intern-resume/intern-resume.component';
import {EditResumeComponent} from './edit-resume/edit-resume.component';
import {EditMockComponent} from './edit-mock/edit-mock.component';
import {MockListComponent} from './mock-list/mock-list.component';
import {AluminiListComponent} from './alumini-list/alumini-list.component';
import {EditAluminiComponent} from './edit-alumini/edit-alumini.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatBadgeModule} from '@angular/material/badge';
import {MatBottomSheetModule} from '@angular/material/bottom-sheet';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatCardModule} from '@angular/material/card';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatChipsModule} from '@angular/material/chips';
import {MatStepperModule} from '@angular/material/stepper';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatDialogModule} from '@angular/material/dialog';
import {MatDividerModule} from '@angular/material/divider';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatInputModule} from '@angular/material/input';
import {MatMenuModule} from '@angular/material/menu';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import {MatSliderModule} from '@angular/material/slider';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import {MatTabsModule} from '@angular/material/tabs';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatTreeModule} from '@angular/material/tree';



import {
  MatButtonModule,
  MatIconModule,
  MatListModule,
  MatNativeDateModule,
  MatSidenavModule,
  MatToolbarModule
} from '@angular/material';
import {CourseListComponent} from './course-list/course-list.component';
import {CourseAddComponent} from './course-add/course-add.component';
import {CourseEditComponent} from './course-edit/course-edit.component';
import {TrainerAddComponent} from './trainer-add/trainer-add.component';
import {TrainerListComponent} from './trainer-list/trainer-list.component';
import {BatchListComponent} from './batch-list/batch-list.component';
import {BatchAddComponent} from './batch-add/batch-add.component';
import {BatchEditComponent} from './batch-edit/batch-edit.component';
import {ViewComponentComponent} from './view-component/view-component.component';
import {ViewTractionsComponent} from './view-tractions/view-tractions.component';
import {PayClientComponent} from './pay-client/pay-client.component';
import {ChangeStatusComponent} from './change-status/change-status.component';
import {SalesInternComponent} from './sales-intern/sales-intern.component';
import { ReportViewComponent } from './report-view/report-view.component';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { MultiDataSet, Label, ChartsModule } from 'ng2-charts';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { TrainerInfoComponent } from './trainer-info/trainer-info.component';
import { LeadsCentralComponent } from './leads-central/leads-central.component';
import { StudentCentralComponent } from './student-central/student-central.component';
import { LocationAddComponent } from './location-add/location-add.component';
import { LocationListComponent } from './location-list/location-list.component';
import { LocationEditComponent } from './location-edit/location-edit.component';
import { ClientDisplayComponent } from './client-display/client-display.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    UserComponent,
    RegisterComponent,
    HomeComponent,
    AdminComponent,
    EditUserComponent,
    PmComponent,
    AdminregisterComponentComponent,
    ClientListComponent,
    AdminclientignupComponent,
    ClientpanelComponent,
    EditClientComponent,
    UnregisteredClientComponent,
    RegisteredClientComponent,
    ThankYouComponent,
    LeadsListComponent,
    StudentListComponent,
    EditStudentComponent,
    PaymentStudentComponent,
    InternListComponent,
    EditInternComponent,
    ResumeListComponent,
    InternResumeComponent,
    EditResumeComponent,
    EditMockComponent,
    MockListComponent,
    AluminiListComponent,
    EditAluminiComponent,
    CourseListComponent,
    CourseAddComponent,
    CourseEditComponent,
    TrainerAddComponent,
    TrainerListComponent,
    BatchListComponent,
    BatchAddComponent,
    BatchEditComponent,
    ViewComponentComponent,
    ViewTractionsComponent,
    PayClientComponent,
    ChangeStatusComponent,
    SalesInternComponent,
    ReportViewComponent,
    TrainerInfoComponent,
    LeadsCentralComponent,
    StudentCentralComponent,
    LocationAddComponent,
    LocationListComponent,
    LocationEditComponent,
    ClientDisplayComponent],
  imports: [
    BrowserModule,
    Ng2SearchPipeModule,
    AppRoutingModule,
    FormsModule,
    Ng2SearchPipeModule,
    ReactiveFormsModule,
    NgMultiSelectDropDownModule.forRoot(),
    FileUploadModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ChartsModule,
    CommonModule, MatButtonModule, MatToolbarModule, MatNativeDateModule, MatIconModule, MatSidenavModule, MatListModule
    ,  MatIconModule, 
    MatSidenavModule, 
    MatListModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule
  ],
  exports: [
    CommonModule, 
    MatButtonModule, 
    MatToolbarModule, 
    MatNativeDateModule, 
  MatIconModule, 
  MatSidenavModule, 
  MatListModule,
  MatAutocompleteModule,
  MatBadgeModule,
  MatBottomSheetModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatStepperModule,
  MatDatepickerModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatTreeModule],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
