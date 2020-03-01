import {Component, OnInit} from '@angular/core';
import {UserService} from '../services/user.service';
import {User} from "../model/user.model";
import {TokenStorageService} from '../auth/token-storage.service';
import {Router} from "@angular/router";
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {
  board: string;
  errorMessage: string;
  users: User[];
  apiUrl = environment.apiUrl;
  fileSystemName: string;
  classpathFileName: string;

  constructor(private router: Router, private userService: UserService, private token: TokenStorageService, private http: HttpClient) {
  }

  ngOnInit() {
    this.userService.getStudentsClientsByTeam().subscribe(
      data => {
        this.users = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )
  }

  addClientUser(): void {
    this.router.navigate(['adminclientignup']);
  };

  editUser(user: User): void {
    window.localStorage.removeItem("editUserId");
    window.localStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['pay-client']);
  };

  editUser2(user: User): void {
    window.localStorage.removeItem("editUserId");
    window.localStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['student-central']);
  };

  reCalculationALl(): void {
    if (confirm("Confirm execution of Mass Recalculation Job ?")) {
      this.userService.reCalculationALl()
        .subscribe(data => {
          if (data.status === 200) {
            window.location.reload();
          } else {
            alert(data.message);
          }
        })
    }

  };

  viewTractions(user: User): void {
    window.localStorage.removeItem("editUserId");
    window.localStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['view-tractions']);
  };

  approveStudent(user: User): void {
    this.userService.approveStudent2(user)
      .subscribe(data => {
        if (data.status === 200) {
          window.location.reload();
        } else {
          alert(data.message);
        }
      })

  };

  onPayment(user: User): void {
    window.localStorage.removeItem("editCourseId");
    window.localStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['payment-student']);
  };

  studentPayments(user: User): void {
    window.localStorage.removeItem("editUserId");
    window.localStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['edit-student']);
  };

  changeStatus(user: User): void {
    window.localStorage.removeItem("editUserId");
    window.localStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['change-status']);
  };

}
