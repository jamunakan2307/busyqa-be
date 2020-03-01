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

@Component({
  selector: 'app-intern-resume',
  templateUrl: './intern-resume.component.html',
  styleUrls: ['./intern-resume.component.css']
})
export class InternResumeComponent implements OnInit {
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
  apiUrl = environment.apiUrl + '/resumes/';
  private fieldArray: Array<any> = [];
  private fieldArray2: Array<any> = [];
  private newAttribute: any = {};

  constructor(private authService: AuthService, private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
  }


  ngOnInit(): void {
    let userId = window.localStorage.getItem("editUserId");
    const headers = [{name: 'Accept', value: 'application/json'}];
    this.uploader = new FileUploader({url: this.apiUrl + userId, autoUpload: true, headers: headers});

    this.uploader.onCompleteAll = () => this.router.navigate(['resume-list']);
  }

  fileOverAnother(e: any): void {
    this.isDropOver = e;
  }

  fileClicked() {
    this.fileInput.nativeElement.click();
  }

}
