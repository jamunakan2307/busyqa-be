import {Component, OnInit} from '@angular/core';
import {UserService} from '../services/user.service';
import {TokenStorageService} from '../auth/token-storage.service';
import {Router} from "@angular/router";
import {HttpClient} from '@angular/common/http';
import {Course} from '../model/course.model';

@Component({
  selector: 'app-batch-list',
  templateUrl: './batch-list.component.html',
  styleUrls: ['./batch-list.component.css']
})
export class BatchListComponent implements OnInit {
  errorMessage: string;
  scheduleds: any[];
  ongoings: any[];
  completeds: any[];
  isScheduledView = false;
  isOngoingView = false;
  iscompletedView = false;
  searchText;

  constructor(private router: Router, private userService: UserService, private token: TokenStorageService, private http: HttpClient) {
  }

  ngOnInit() {
    this.userService.getScheduledBatch().subscribe(
      data => {
        this.scheduleds = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )

    this.userService.getOngoingBatch().subscribe(
      data => {
        this.ongoings = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )

    this.userService.getOngoingBatch().subscribe(
      data => {
        this.ongoings = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )

    this.userService.getCompletedBatch().subscribe(
      data => {
        this.completeds = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )
  }

  addBatch(): void {
    this.router.navigate(['batch-add']);
  };

  editBatch(course: Course): void {
    window.localStorage.removeItem("editBatchId");
    window.localStorage.setItem("editBatchId", course.id.toString());
    this.router.navigate(['batch-edit']);
  };

}
