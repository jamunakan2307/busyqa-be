import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../services/user.service';
import {first} from 'rxjs/operators';
import {BatchInfo} from '../model/batch.model';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-batch-edit',
  templateUrl: './batch-edit.component.html',
  styleUrls: ['./batch-edit.component.css']
})
export class BatchEditComponent implements OnInit {
  editForm: FormGroup;
  errorMessage = '';
  batchInfo: BatchInfo;
  courses: any[];
  trainers: any[];
  trainerEmail: '';
  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
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
    this.userService.getAllTrainer().subscribe(
      data => {
        this.trainers = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )

    let userId = window.localStorage.getItem("editBatchId");
    if (!userId) {
      alert("Invalid action.")
      this.router.navigate(['batch-list']);
      return;
    }

    this.editForm = this.formBuilder.group({
      id: [''],
      courseName: ['', Validators.required],
      trainerName: ['', Validators.required],
      startDate: ['', Validators.required],
      courseLocation: ['', Validators.required],
      batchDetails: ['', Validators.required],
      taxPercentage: ['', Validators.required],
      batchStatus: ['', Validators.required],
      registrationFees:['', Validators.required],
      
      endDate: [''],
      courseAmount: [''],
      netCourseAmount: [''],
      courseLenght: [''],

    });

    this.userService.getOneBatch(+userId)
      .subscribe(data => {
        if (data.status === 200) {
          this.editForm.setValue(data.result);
        } else {
          alert(data.message);
        }
      });


  }

  onSubmit() {
    this.userService.updateBatch(this.editForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            alert('Course updated successfully.');
          } else {
            alert(data.message);
          }
          this.router.navigate(['batch-list']);
        });

  }


}
