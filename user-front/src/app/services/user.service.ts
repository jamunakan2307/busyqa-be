import {BatchInfo} from './../model/batch.model';
import {Course} from './../model/course.model';
import {CourseInfo} from './../auth/course-info';
import {PaymentInfo} from './../auth/payment-info';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {ApiResponse} from "../services/api.response";
import {TokenStorageService} from '../auth/token-storage.service';
import {InternalFormsSharedModule} from '@angular/forms/src/directives';
import {User} from "../model/user.model";
import {TrainerInfo} from '../model/trainer.model';
import { ApiResponse2 } from './api.response2';
import { LocationInfo } from '../model/location.model';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  apiUrl = environment.apiUrl;
  info: any;
  private userUrl = this.apiUrl + '/user';
  private pmUrl = this.apiUrl + '/pm';
  private adminUrl = this.apiUrl + '/admin';

  constructor(private http: HttpClient, private token: TokenStorageService) {
  }

  getUserBoard(): Observable<string> {
    return this.http.get(this.userUrl, {responseType: 'text'});
  }

  getPMBoard(): Observable<string> {
    return this.http.get(this.pmUrl, {responseType: 'text'});
  }

  getAdminBoard(): Observable<string> {
    return this.http.get(this.adminUrl, {responseType: 'text'});
  }

  getUsersByTeam(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/users/" + this.info.token);
  }

  getUsersTrainer(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/users/TEAM_TRAINER");
  }

  getUsersByTrainer(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/users/TEAM_TRAINER");
  }


  getClientssByTeam(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/client/" + this.info.username);
  }

  getLeadsClientsByTeam(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/client/leads");
  }

  getStudentsClientsByTeam(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/client/students");
  }

  getLeadsStudentsByTeam(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/client/students");
  }

  getInternssByTeam(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/client/interns");
  }

  getResumeByTeam(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/client/resume");
  }

  getMockByTeam(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/client/mock");
  }

  getAluminisByTeam(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/client/alumini");
  }

  getClientsByUsername(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.userUrl + "/client/" + this.info.token);
  }

  getPmUserByTeam(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.pmUrl + "/users/" + this.info.token);
  }


  getUserById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.adminUrl + "/user/" + id);
  }

  getClientById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.adminUrl + "/oneclient/" + id);
  }

  getClientById2(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.adminUrl + "/client/OneClient/" + id);
  }

  getunregisteredClient(path: string): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.apiUrl + path);
  }

  getregisteredClient(path: string): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.apiUrl + path);
  }

  updateUser(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.adminUrl + "/user/" + user.id, user);
  }

  updateClient(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.adminUrl + "/oneclient/" + user.id, user);
  }

  updateUnregistredClient(location: string, user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(location, user);
  }

  updateregistredClient(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.apiUrl + "/save-client/" + user.id, user);
  }

  deleteUser(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.adminUrl + "/user/" + id);
  }

  deleteClient(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.adminUrl + "/oneclient/" + id);
  }

  approveClient(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.apiUrl + "/registered-client/" + user.id, user);
  }

  approveStudent2(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.apiUrl + "/registered-client/student/" + user.id, user);
  }


  agreeClient(user: User): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.apiUrl + "/download/agree/" + user.username);
  }

  getPaymentHistory(id: number): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.apiUrl + "/pay-student/" + id);
  }

  getPaymentHistory2(id: number): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.apiUrl + "/pay-student/myself/" + id);
  }

  reCalculationALl(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.apiUrl + "/pay-student/recalculateAll");
  }

  getPaymentHistoryBy(id: number): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.apiUrl + "/pay-student/myself/" + id);
  }

  getPaymentHistoryBy2(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.apiUrl + "/pay-student/myself2/" + this.info.token);
  }


  updatePaymentHistory(id: number, info: PaymentInfo): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.apiUrl + "/pay-student/" + id, info);
  }

  updatePayment(id: number, info: PaymentInfo): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.apiUrl + "/pay-student/update2/" + id, info);
  }

  updatePaymentStatus(id: number, info: PaymentInfo): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.apiUrl + "/pay-student/updateStatus/" + id, info);
  }

  updatePopulatePayment(id: number, info: PaymentInfo): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.apiUrl + "/pay-student/populate/" + id, info);
  }

  getInternOneList(id: number): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.apiUrl + "/intern/" + id);
  }

  getResumeOneList(id: number): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.apiUrl + "/resume/" + id);
  }

  getMockOneList(id: number): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.apiUrl + "/mock/" + id);
  }

  getAluminiOneList(id: number): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.apiUrl + "/alumini/" + id);
  }


  updateInternRecord(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.apiUrl + "/intern/" + user.id, user);
  }

  updateResumeRecord(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.apiUrl + "/resume/" + user.id, user);
  }

  updateMockRecord(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.apiUrl + "/mock/" + user.id, user);
  }

  updateAluminiRecord(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.apiUrl + "/alumini/" + user.id, user);
  }

/// Busy qa 2.0 Course

  getAllCourses(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/course/getAll");
  }

  //Location 
  getAllLocations(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/location/getAll");
  }
  createCourse(info: CourseInfo): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.adminUrl + "/course/create", info);
  }

  //Locations

  createLocation(info: LocationInfo): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.adminUrl + "/location/create", info);
  }

  createCourse2(info: CourseInfo): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.adminUrl + "/course/create2", info);
  }

  
  getOneCourse(id: number): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/course/" + id);
  }

  //location
  getOneLocation(id: number): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/location/" + id);
  }

  updateCourse(course: Course): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.adminUrl + "/course/update", course);
  }

  //Locations
  updateLocation(course: Course): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.adminUrl + "/location/update", course);
  }

/// Trainer

  getAllTrainer(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/users/TEAM_TRAINER");
  }

  createTrainer(info: TrainerInfo): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.adminUrl + "/trainer/create", info);
  }

  getOneTrainer(id: number): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/trainer/" + id);
  }

  updateTrainer(course: Course): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.adminUrl + "/trainer/update", course);
  }

  getScheduledBatch(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/batch/status/Scheduled");
  }

  getOngoingBatch(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/batch/status/Ongoing");
  }

  getCompletedBatch(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/batch/status/Completed");
  }

//Batch 

  createBatch(info: BatchInfo): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.adminUrl + "/batch/create", info);
  }

  getOneBatch(id: number): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/batch/" + id);
  }

  updateBatch(course: Course): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.adminUrl + "/batch/update", course);
  }

  getOneBatchStatus(courseName: string): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.adminUrl + "/batch/status/scheduled/" + courseName);
  }

  //Reports 



  getAllCount(): Observable<ApiResponse> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse>(this.apiUrl + "/reports/getALL");
  }

  getByCourseCount(): Observable<ApiResponse2> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse2>(this.apiUrl + "/reports/getCourseBy");
  }

  getBySeasonCount(): Observable<ApiResponse2> {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    }
    return this.http.get<ApiResponse2>(this.apiUrl + "/reports/getSeasonBy");
  }


  updateTrainerzPic(id: number, info: String): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.adminUrl + "/batch/createTrainer/" + id, info);
  }

 getAllClients() : Observable<ApiResponse>{ this.info = {
  token: this.token.getToken(),
  username: this.token.getUsername(),
  authorities: this.token.getAuthorities()
}
return this.http.get<ApiResponse>(this.apiUrl + "/admin/client/allclients");
}

 }

