import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder} from '@angular/forms';
import {UserService} from '../services/user.service';
import {CourseInfo} from '../auth/course-info';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-course-add',
  templateUrl: './course-add.component.html',
  styleUrls: ['./course-add.component.css']
})
export class CourseAddComponent implements OnInit {
  form: any = {};
  errorMessage = '';
  courseInfo: CourseInfo;
  status = 'Active';

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
  }

  ngOnInit() {
  }

  onSubmit() {
    this.courseInfo = new CourseInfo(this.form.courseLenght, this.form.courseName, this.form.courseAmount, this.status);
    this.userService.createCourse(this.courseInfo).pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            alert('Course updated successfully.');
          } else {
            alert(data.message);
          }
          this.router.navigate(['course-list']);
        });
  }

}
