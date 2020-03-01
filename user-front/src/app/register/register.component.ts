import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

import {AuthService} from '../auth/auth.service';
import {SignUpInfo} from '../auth/signup-info';
import {UserService} from '../services/user.service';
import {HttpClient} from '@angular/common/http';
import {Course} from '../model/course.model';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: any = {};
  signupInfo: SignUpInfo;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';
  batches: Course[];
  batch2: any;
  courses: any[];
  courseLocation: any[];
  startDate: any[];
  isCourseChecked = false;
  isBatchChecked = true;
  password = "password12";
  selectedBatchItem: any;
  constructor(private authService: AuthService, private userService: UserService, private router: Router) {
  }

  ngOnInit() {
    this.userService.getAllCourses().subscribe(
      data => {
        this.courses = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )


  }

  getBatch() {
    this.isCourseChecked = true;
    this.isBatchChecked = true;

    this.userService.getOneBatchStatus(this.form.courseName).subscribe(
      data => {
        this.batches = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )

  }

  selectedBatch(batch: Course) {
    this.isCourseChecked = false;
    this.selectedBatchItem = batch.id;
    this.isBatchChecked = false;
    this.userService.getOneBatch(+this.selectedBatchItem)
    .subscribe(data => {
      if (data.status === 200) {
        this.batch2 = data.result;
      } else {
        alert(data.message);
      }
    });
  }

  onSubmit() {
    console.log(this.form);

    this.signupInfo = new SignUpInfo(
      this.form.firstName,
      this.form.lastName,
      this.form.username,
      this.form.email,
      this.password,
      this.form.phoneNumber,
      this.form.studentComments,
      this.form.leadSource,
      'NO',
      this.selectedBatchItem);

    this.authService.signUp(this.signupInfo).subscribe(
      data => {
        console.log(data);
        this.isSignedUp = true;
        this.isSignUpFailed = false;
        this.router.navigate(['thank-you']);
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isSignUpFailed = true;
        this.router.navigate(['signup']);
      }
    );

  }

  gotoLoginPage(): void {
    this.router.navigate(['auth/login']);
  };
}
