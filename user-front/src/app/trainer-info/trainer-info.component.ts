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
  selector: 'app-trainer-info',
  templateUrl: './trainer-info.component.html',
  styleUrls: ['./trainer-info.component.css']
})
export class TrainerInfoComponent implements OnInit {
  apiUrl = environment.apiUrl;
  @ViewChild('fileInput') fileInput: ElementRef;
  uploader: FileUploader;
  isDropOver: boolean;
  form: any = {};
  trainer = true;

  constructor(private authService: AuthService, private formBuilder: FormBuilder, private router: Router, private userService: UserService, private http: HttpClient) {
  }


  ngOnInit() {
    let userId = window.localStorage.getItem("editUserId");
    this.fileInput.nativeElement.click();
    const headers = [{name: 'Accept', value: 'application/json'}];
    this.uploader = new FileUploader({url: this.apiUrl + "/download/trainerPic/"+userId, autoUpload: true, headers: headers});
  }

  fileOverAnother(e: any): void {
    this.isDropOver = e;
  }

  fileClicked() {
    this.uploader.onCompleteAll = () => alert('File uploaded');
  }

  
  downloadFileSystem() {
    const fileName = "SampleTrainer.txt";
    this.http.get(this.apiUrl + "/download/trainermatch", {responseType: 'blob'})
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
    let userId = window.localStorage.getItem("editUserId");
    this.userService.updateTrainerzPic(+userId, this.form.trainerDetails).pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            alert('Created successfully.');
          } else {
            alert(data.message);
          }
          this.router.navigate(['trainer-list']);
        });
  }

}
