import {Component, OnInit} from '@angular/core';
import {UserService} from '../services/user.service';
import {User} from "../model/user.model";
import {TokenStorageService} from '../auth/token-storage.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-edit-student',
  templateUrl: './edit-student.component.html',
  styleUrls: ['./edit-student.component.css']
})
export class EditStudentComponent implements OnInit {
  board: string;
  errorMessage: string;
  users: User[];

  constructor(private router: Router, private userService: UserService, private token: TokenStorageService) {
  }

  ngOnInit() {
    let userId = window.localStorage.getItem("editUserId");
    this.userService.getPaymentHistory(+userId).subscribe(
      data => {
        this.users = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )
  }

  addPayment(): void {
    this.router.navigate(['payment-student']);
  };


}
