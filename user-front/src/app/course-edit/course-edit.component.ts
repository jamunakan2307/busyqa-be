import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../services/user.service';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-course-edit',
  templateUrl: './course-edit.component.html',
  styleUrls: ['./course-edit.component.css']
})
export class CourseEditComponent implements OnInit {
  editForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
  }

  ngOnInit() {
    let userId = window.localStorage.getItem("editCourseId");
    if (!userId) {
      alert("Invalid action.")
      this.router.navigate(['course-list']);
      return;
    }

    this.editForm = this.formBuilder.group({
      id: [''],
      courseName: ['', Validators.required],
      courseAmount: ['', Validators.required],
      courseLenght: ['', Validators.required],
      status: ['', Validators.required],
    });

    this.userService.getOneCourse(+userId)
      .subscribe(data => {
        if (data.status === 200) {
          this.editForm.setValue(data.result);
        } else {
          alert(data.message);
        }
      });

  }


  onSubmit() {
    this.userService.updateCourse(this.editForm.value)
      .pipe(first())
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
