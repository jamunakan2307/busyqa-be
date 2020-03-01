import {Component, OnInit} from '@angular/core';
import {UserService} from '../services/user.service';
import {TokenStorageService} from '../auth/token-storage.service';
import {Router} from "@angular/router";
import {HttpClient} from '@angular/common/http';
import {Course} from '../model/course.model';


@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent implements OnInit {
  errorMessage: string;
  courses: any[];
  searchText;


  constructor(private router: Router, private userService: UserService, private token: TokenStorageService, private http: HttpClient) {
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

  addCourse(): void {
    this.router.navigate(['course-add']);
  };

  editCourse(course: Course): void {
    window.localStorage.removeItem("editCourseId");
    window.localStorage.setItem("editCourseId", course.id.toString());
    this.router.navigate(['course-edit']);
  };
}
