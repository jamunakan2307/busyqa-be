import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../model/user.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {first} from "rxjs/operators";
import {UserService} from '../services/user.service';
import {BrowserModule} from '@angular/platform-browser';
import {SignUpInfo} from '../auth/signup-info';
import {AuthService} from '../auth/auth.service';
import {FileUploader} from 'ng2-file-upload';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-unregistered-client',
  templateUrl: './unregistered-client.component.html',
  styleUrls: ['./unregistered-client.component.css']
})
export class UnregisteredClientComponent implements OnInit {
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
  @ViewChild('fileInput') fileInput: ElementRef;
  uploader: FileUploader;
  isDropOver: boolean;
  apiUrl = environment.apiUrl;
  individual: boolean;
  techjob: boolean;
  intern = false;
  loco: any;
  countryList: Array<any> = [
    {name: 'Select A Country ', states: [], cities: []},
    {
      name: 'Canada',
      states: ['Select A State', 'Ontario', 'Quebec', 'Nova Scotia'],
      cities: ['Select A City', 'Missisuaga', 'Markham', 'Toronto', 'Waterloo', 'Online']
    },
    {
      name: 'USA',
      states: ['Select A State', 'Newyork', 'Michigan', 'california'],
      cities: ['Select A City', 'Newyork', 'Orlando', 'Online']
    },
  ];
  cities: Array<any>;
  states: Array<any>;
  private fieldArray: Array<any> = [];
  private fieldArray2: Array<any> = [];
  private newAttribute: any = {};

  constructor(private authService: AuthService, private formBuilder: FormBuilder, private router: Router, private userService: UserService, private http: HttpClient) {
  }

  ngOnInit() {
    var str = window.location.pathname;
    this.loco = str;
    this.editForm = this.formBuilder.group({
      id: ['', Validators.required],
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
      address: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      zipCode: ['', Validators.required],
      country: ['', Validators.required],
      emergencyPhoneNumber: ['', Validators.required],
      aboutUs: [''],
      clientPassLock: [''],
      amountPaid: [''],
      totalCourseAmount: [''],
      totalCourseAmountAfterDiscount: [''],
      totaDiscountAmount: [''],
      currentlyEmployed: ['', Validators.required],
      currentlyTechIndustry: ['', Validators.required],
      desriredJob: ['', Validators.required],
      currentJob: ['', Validators.required],
      leadSource: [''],
      studentComments: [''],
      createdTimeStamp: [''],
      registerationTimeStamp: [''],
      lastModifiedTimeStamp: [''],
      lastPaymentTimeStamp: [''],
      isRegisteredStudent: [''],
      studentLock: [''],
      isCourse: [''],
      internDate: ['', Validators.required],
      clientStatus: [''],
      paymentPlan: ['', Validators.required],
      paymentStatus: [''],
      courseLocation: [''],
      courseName: [''],
      courseLenght: [''],
      startDate: [''],
      endDate: [''],
      paySetupDone: [''],
      anchorDate: ['']

    });
    this.userService.getunregisteredClient(str)
      .subscribe(data => {
        if (data.result.isCourse === 'No') {
          this.intern = true;
        } else {
          this.intern = false;
        }

        if (data.status === 200) {
          this.editForm.setValue(data.result);

        } else {
          alert(data.message);
          this.router.navigate(['thank-you']);
        }

      });

    this.fileInput.nativeElement.click();
    const headers = [{name: 'Accept', value: 'application/json'}];
    this.uploader = new FileUploader({url: this.apiUrl + str, autoUpload: true, headers: headers});
  }

  fileOverAnother(e: any): void {
    this.isDropOver = e;
  }

  fileClicked() {
    this.uploader.onCompleteAll = () => alert('File uploaded');
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

  downloadFileSystem() {
    const fileName = "Registration _ Payment plan agreement.pdf";
    this.http.get(this.apiUrl + "/download/agreement", {responseType: 'blob'})
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

  onSelecetionChange(value: string) {
    if (value === 'YES') {
      // You can add some service call or customize your data from here
      this.individual = true;
    }
    else {
      this.individual = false;
    }
  }

  onSelecetionChange2(value: string) {
    if (value === 'YES') {
      // You can add some service call or customize your data from here
      this.techjob = true;
    }
    else {
      this.techjob = false;
    }
  }

  onSubmit() {
    this.userService.updateUnregistredClient(this.apiUrl + this.loco, this.editForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            alert('You have been registered succesfully....');
            this.router.navigate(['thank-you']);
          } else {
            alert(data.message);
          }
        });
  }

}
