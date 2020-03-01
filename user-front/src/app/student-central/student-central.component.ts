import {Component, Inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../model/user.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {first} from "rxjs/operators";
import {UserService} from '../services/user.service';
import {BrowserModule} from '@angular/platform-browser';

@Component({
  selector: 'app-student-central',
  templateUrl: './student-central.component.html',
  styleUrls: ['./student-central.component.css']
})
export class StudentCentralComponent implements OnInit {
  user: User;
  username: string;
  teams: string;
  roles: string;
  editForm: FormGroup;
  editForm2: FormGroup;
  editForm3: FormGroup;

  paySetupCheck = true
  selectedItems = [];
  dropdownSettings = {};
  dropdownList = [];
  showPayment = false;
  showPayment1 = false;

  //Team
  teamSelectedItems = [];
  teamDropdownSettings = {};
  teamDropdownList = [];
  clients: any;
  payments: any[];
  payments1: any[];

  private fieldArray: Array<any> = [];
  private fieldArray2: Array<any> = [];
  private newAttribute: any = {};
  users: User[];
  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
  }

  ngOnInit() {
    let userId = window.localStorage.getItem("editUserId");
    if (!userId) {
      alert("Invalid action.")
      this.router.navigate(['admin']);
      return;
    }
    this.editForm = this.formBuilder.group({
      id: [''],
      firstName: [''],
      lastName: [''],
      username: [''],
      email: [''],
      password: [''],
      statusAsOfDay: [''],
      status: [''],
      roles: [''],
      teams: [''],
      phoneNumber: [''],
      address: [''],
      city: [''],
      state: [''],
      zipCode: [''],
      country: [''],
      emergencyPhoneNumber: [''],
      aboutUs: [''],
      clientPassLock: [''],
      amountPaid: [''],
      totalCourseAmount: [''],
      totalCourseAmountAfterDiscount: [''],
      totaDiscountAmount: [''],
      currentlyEmployed: [''],
      currentlyTechIndustry: [''],
      desriredJob: [''],
      currentJob: [''],
      leadSource: [''],
      studentComments: [''],
      createdTimeStamp: [''],
      registerationTimeStamp: [''],
      lastModifiedTimeStamp: [''],
      lastPaymentTimeStamp: [''],
      isRegisteredStudent: [''],
      studentLock: [''],
      isCourse: [''],
      internDate: [''],
      clientStatus: [''],
      paymentPlan: [''],
      paymentStatus: [''],
      courseLocation: [''],
      courseName: [''],
      courseLenght: [''],
      startDate: [''],
      endDate: [''],
      paySetupDone: [''],
      anchorDate: [''],
      registrationFees:['']
    });

    this.editForm3 = this.formBuilder.group({
      id: [''],
      firstName: [''],
      lastName: [''],
      username: [''],
      email: [''],
      password: [''],
      statusAsOfDay: [''],
      status: [''],
      roles: [''],
      teams: [''],
      phoneNumber: [''],
      address: [''],
      city: [''],
      state: [''],
      zipCode: [''],
      country: [''],
      emergencyPhoneNumber: [''],
      aboutUs: [''],
      clientPassLock: [''],
      amountPaid: [''],
      totalCourseAmount: [''],
      totalCourseAmountAfterDiscount: [''],
      totaDiscountAmount: [''],
      currentlyEmployed: [''],
      currentlyTechIndustry: [''],
      desriredJob: [''],
      currentJob: [''],
      leadSource: [''],
      studentComments: [''],
      createdTimeStamp: [''],
      registerationTimeStamp: [''],
      lastModifiedTimeStamp: [''],
      lastPaymentTimeStamp: [''],
      isRegisteredStudent: [''],
      studentLock: [''],
      isCourse: [''],
      internDate: [''],
      clientStatus: [''],
      paymentPlan: [''],
      paymentStatus: [''],
      courseLocation: [''],
      courseName: [''],
      courseLenght: [''],
      startDate: [''],
      endDate: [''],
      paySetupDone: [''],
      anchorDate: [''],
      registrationFees:['']
    });
    this.userService.getClientById2(+userId)
      .subscribe(data => {
        this.clients = data.result;
        if (data.result.paySetupDone === 'Yes') {
          this.paySetupCheck = false;
        } else {
          this.paySetupCheck = true;
        }
        if (data.status === 200) {
          this.editForm.setValue(data.result);
          this.editForm3.setValue(data.result);
        } else {
          alert(data.message);
        }
      });

      this.editForm2 = this.formBuilder.group({
        id: [''],
        amountPaid: ['', Validators.required],
        weeklyAmountPaid: [''],
        scheduledPaymentDate: [''],
        paymentNotes: ['', Validators.required],
        paymentMode: ['', Validators.required],
        totalAmount: [''],
        differenceAmount: [''],
        paymentDate: [''],
        recordStatus: [''],
        user: [''],
        payLock:[''],
        emailReminder:['']
      });
      this.userService.getPaymentHistory(+userId)
        .subscribe(data2 => {
          if (data2.status === 200) {
            this.payments = data2.result;
            this.editForm2.setValue(data2.result);
          } else {
            alert(data2.message);
          }
        });
        
        this.userService.getPaymentHistoryBy(+userId).pipe(first())
          .subscribe(
            data3 => {
              if (data3.status === 200) {
                this.payments1 = data3.result;
                this.showPayment = true;
              } else {
              }
            });
  }

  back():void{
    this.router.navigate(['student-list']);

  }

  

  onSubmit2() {
    let userId = window.localStorage.getItem("editUserId");
    this.userService.updatePaymentHistory(+userId, this.editForm2.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            alert(data.message);
            this.payments = data.result;
            window.location.reload();
          } else {
            alert(data.message);
          }
        });
  }

  onPopulatePayment() {
    let userId = window.localStorage.getItem("editUserId");
    this.userService.updatePopulatePayment(+userId, this.editForm3.value).pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            this.payments = data.result;
            this.showPayment1 = true;
          } else {
          }
        });
        
  }

  onSubmit3() {
    let userId = window.localStorage.getItem("editUserId");
    this.userService.updatePayment(+userId, this.editForm3.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {

            alert('Client Payment Setup Done');
            window.location.reload();

          } else {
            alert(data.message);
          }
        });
  }

  changeStatus(user: User): void {
    let userId = window.localStorage.getItem("editUserId");

  };

  onSubmit4() {
    let userId = window.localStorage.getItem("editUserId");
    this.userService.updatePaymentStatus(+userId, this.editForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {

            alert('Client Status Update');
            window.location.reload();

          } else {
            alert(data.message);
          }
        });
  }
}

