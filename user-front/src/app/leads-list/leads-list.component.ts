import {Component, OnInit} from '@angular/core';
import {UserService} from '../services/user.service';
import {User} from "../model/user.model";
import {TokenStorageService} from '../auth/token-storage.service';
import {Router} from "@angular/router";
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Ng2SearchPipeModule } from 'ng2-search-filter';

@Component({
  selector: 'app-leads-list',
  templateUrl: './leads-list.component.html',
  styleUrls: ['./leads-list.component.css']
})
export class LeadsListComponent implements OnInit {
  board: string;
  errorMessage: string;
  clients: any;
  apiUrl = environment.apiUrl;
  fileSystemName: string;
  classpathFileName: string;
  searchText;
  users: User[];

  constructor(private router: Router, private userService: UserService, private token: TokenStorageService, private http: HttpClient) {
  }

  ngOnInit() {
    this.userService.getLeadsClientsByTeam().subscribe(
      data => {
        this.users = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )
  }

  deleteClient(user: User): void {
    this.userService.deleteClient(user.id)
      .subscribe(data => {
        this.users = this.users.filter(u => u !== user);
        alert("User Deleted Successfully!!")
        this.router.navigate(['leads-list']);
        window.location.reload();
      })
  };

  approveUser(user: User): void {
    if (confirm("Client will be moved to next stage based on course , is it fine ?")) {
      this.userService.approveClient(user)
        .subscribe(data => {
          if (data.status === 200) {
            window.location.reload();
          } else {
            alert(data.message);
          }
        })
    }

  };

  reCalculationALl(): void {
    if (confirm("Confirm execution of Mass Recalculation Jon ?")) {
      this.userService.reCalculationALl()
        .subscribe(data => {
          if (data.status === 200) {
            window.location.reload();
          } else {
            alert(data.message);
          }
        })
    }

  };


  addClientUser(): void {
    this.router.navigate(['adminclientignup']);
  };


  downloadFileSystem(user: User) {
    const fileName = user.username + "_payment_plan.pdf";
    this.http.get(this.apiUrl + "/download/agree/" + user.username, {responseType: 'blob'})
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

  editUser(user: User): void {
    window.localStorage.removeItem("editUserId");
    window.localStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['edit-client']);
  };

  viewTractions(user: User): void {
    window.localStorage.removeItem("editUserId");
    window.localStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['view-tractions']);
  };

  viewClient(user: User): void {
    window.localStorage.removeItem("editUserId");
    window.localStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['view-component']);
  };

  leadsCentral(user: User): void {
    window.localStorage.removeItem("editUserId");
    window.localStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['leads-central']);
  };
}



