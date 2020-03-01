import {Component, Inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../model/user.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {first} from "rxjs/operators";
import {UserService} from '../services/user.service';
import {BrowserModule} from '@angular/platform-browser';

@Component({
  selector: 'app-pay-client',
  templateUrl: './pay-client.component.html',
  styleUrls: ['./pay-client.component.css']
})
export class PayClientComponent implements OnInit {

  user: User;
  username: string;
  teams: string;
  roles: string;
  editForm: FormGroup;
  paySetupCheck = true
  selectedItems = [];
  dropdownSettings = {};
  dropdownList = [];
  showPayment = false;
  //Team
  teamSelectedItems = [];
  teamDropdownSettings = {};
  teamDropdownList = [];
  clients: any;
  payments: any[];
  private fieldArray: Array<any> = [];
  private fieldArray2: Array<any> = [];
  private newAttribute: any = {};

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
        } else {
          alert(data.message);
        }
      });
  }

  onItemSelect(item: any) {
    console.log(item);
  }

  onSelectAll(items: any) {
    console.log(items);
  }

  onPopulatePayment() {
    let userId = window.localStorage.getItem("editUserId");
    this.userService.updatePopulatePayment(+userId, this.editForm.value).pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            this.payments = data.result;
            this.showPayment = true;
          } else {
          }
        });
        
  }

  onPayment(): void {
    let userId = window.localStorage.getItem("editUserId");
    window.localStorage.removeItem("editCourseId");
    window.localStorage.setItem("editUserId", userId);
    this.router.navigate(['payment-student']);
  };

  onSubmit() {
    let userId = window.localStorage.getItem("editUserId");
    this.userService.updatePayment(+userId, this.editForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {

            alert('Client Payment Setup Done');
            this.router.navigate(['home']);

          } else {
            alert(data.message);
          }
        });
  }
}

