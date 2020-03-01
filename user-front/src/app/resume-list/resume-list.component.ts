import {User} from './../model/user.model.1';
import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {first} from "rxjs/operators";
import {UserService} from '../services/user.service';
import {BrowserModule} from '@angular/platform-browser';
import {FileUploader} from 'ng2-file-upload';
import {environment} from '../../environments/environment';
import {TokenStorageService} from '../auth/token-storage.service';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-resume-list',
  templateUrl: './resume-list.component.html',
  styleUrls: ['./resume-list.component.css']
})
export class ResumeListComponent implements OnInit {
  user: User;
  errorMessage: string;
  users: User[];
  fileSystemName: string;
  classpathFileName: string;

  fileInput: ElementRef;
  uploader: FileUploader;
  isDropOver: boolean;
  apiUrl = environment.apiUrl + '/download/resume/';
  searchText;


  constructor(private router: Router, private userService: UserService, private token: TokenStorageService, private http: HttpClient) {
  }

  ngOnInit() {
    this.userService.getResumeByTeam().subscribe(
      data => {
        this.users = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )
  }

  uploadResume(user: User): void {
    window.localStorage.removeItem("editUserId");
    window.localStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['intern-resume']);
  };

  downlaodResume(user: User) {
    const fileName = user.id + "_resume.pdf";
    this.http.get(this.apiUrl + user.id, {responseType: 'blob'})
      .subscribe((blob: Blob) => {
        console.log('resume is downloaded');

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

  editUser(user: User): void {
    window.localStorage.removeItem("editUserId");
    window.localStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['edit-resume']);
  };

}
