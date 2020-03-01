import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder} from '@angular/forms';
import {UserService} from '../services/user.service';
import {first} from 'rxjs/operators';
import {BatchInfo} from '../model/batch.model';
import {TokenStorageService} from '../auth/token-storage.service';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';


@Component({
  selector: 'app-batch-add',
  templateUrl: './batch-add.component.html',
  styleUrls: ['./batch-add.component.css']
})
export class BatchAddComponent implements OnInit {
  form: any = {};
  errorMessage = '';
  endDate = '';
  courseAmount = 0;
  netCourseAmount = 0;
  courseLenght = 0;
  batchInfo: BatchInfo;
  batchStatus = 'Scheduled';
  courses: any[];
  trainers: any[];
  locations: any[]
  trainerEmail: '';
  season:'';
  apiUrl = environment.apiUrl;
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

    this.userService.getAllLocations().subscribe(
      data => {
        this.locations = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )
    this.userService.getUsersTrainer().subscribe(
      data => {
        this.trainers = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )
  }

  downloadFileSystem() {
    const fileName = "SampleBatch.txt";
    this.http.get(this.apiUrl + "/download/batachmatch", {responseType: 'blob'})
      .subscribe((blob: Blob) => {
        console.log('report is downloaded');

        if (navigator.msSaveBlob) {
          // IE 10+
          navigator.msSaveBlob(blob, fileName);
        }
        else {
          let link = document.createElement("a");
          if (link.download !== undefined) {
            let url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", fileName);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
          }
          else {
            //html5 download not supported
          }
        }
      });
    }

  onSubmit() {
    this.batchInfo = new BatchInfo(this.form.courseName, this.form.trainerName, this.form.startDate, this.endDate, this.form.courseLocation, this.form.batchDetails, this.form.taxPercentage, this.courseAmount, this.netCourseAmount, this.courseLenght, this.batchStatus, this.trainerEmail, this.form.registrationFees);
    this.userService.createBatch(this.batchInfo).pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            alert('Batch Created successfully.');
          } else {
            alert(data.message);
          }
          this.router.navigate(['batch-list']);
        });
  }

}
