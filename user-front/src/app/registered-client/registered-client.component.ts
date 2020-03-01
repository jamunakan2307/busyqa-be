import {Component, Inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../model/user.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {first} from "rxjs/operators";
import {UserService} from '../services/user.service';
import {BrowserModule} from '@angular/platform-browser';
import {SignUpInfo} from '../auth/signup-info';
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'app-registered-client',
  templateUrl: './registered-client.component.html',
  styleUrls: ['./registered-client.component.css']
})
export class RegisteredClientComponent implements OnInit {
  user: User;
  username: string;
  teams: string;
  roles: string;
  editForm: FormGroup;
  selectedItems = [];
  dropdownSettings = {};
  dropdownList = [];
  //Team
  teamSelectedItems = [];
  teamDropdownSettings = {};
  teamDropdownList = [];
  form: any = {};
  signupInfo: SignUpInfo;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';
  countryList: Array<any> = [
    {
      name: 'Canada',
      states: ['Ontario', 'Quebec', 'Nova Scotia'],
      cities: ['Missisuaga', 'Markham', 'Toronto', 'Waterloo']
    },
    {name: 'USA', states: ['Newyork', 'Michigan', 'california'], cities: ['Newyork', 'Orlando']},
  ];
  cities: Array<any>;
  states: Array<any>;
  private fieldArray: Array<any> = [];
  private fieldArray2: Array<any> = [];
  private newAttribute: any = {};

  constructor(private authService: AuthService, private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
  }

  ngOnInit() {
    var str = window.location.pathname;
    this.editForm = this.formBuilder.group({
      id: ['', Validators.required],
      firstName: [''],
      lastName: [''],
      username: [''],
      email: [''],
      password: ['', Validators.required],
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
      desriredJob:[''],
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
      anchorDate: ['']
    });
    this.userService.getregisteredClient(str)
      .subscribe(data => {
        if (data.status === 200) {
          this.editForm.setValue(data.result);
        } else {
          alert(data.message);
          this.router.navigate(['auth/login']);
        }
      });
  }

  onItemSelect(item: any) {
    console.log(item);
  }

  onSelectAll(items: any) {
    console.log(items);
  }

  changeCountry(count) {
    this.cities = this.countryList.find(con => con.name == count).cities;
    this.states = this.countryList.find(con => con.name == count).states;
  }

  onSubmit() {
    this.userService.updateregistredClient(this.editForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            alert(data.message);
            this.router.navigate(['auth/login']);
          } else {
            alert(data.message);
          }
        });
  }

}

