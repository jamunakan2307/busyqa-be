import {Component, OnInit} from '@angular/core';
import {UserService} from '../services/user.service';
import {User} from "../model/user.model";
import {TokenStorageService} from '../auth/token-storage.service';
import {Router} from "@angular/router";
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Component({
  selector: 'app-intern-list',
  templateUrl: './intern-list.component.html',
  styleUrls: ['./intern-list.component.css']
})
export class InternListComponent implements OnInit {
  board: string;
  errorMessage: string;
  users: User[];
  apiUrl = environment.apiUrl;
  fileSystemName: string;
  classpathFileName: string;
  searchText;

  constructor(private router: Router, private userService: UserService, private token: TokenStorageService, private http: HttpClient) {
  }

  ngOnInit() {
    this.userService.getInternssByTeam().subscribe(
      data => {
        this.users = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )
  }

  editUser(user: User): void {
    window.localStorage.removeItem("editUserId");
    window.localStorage.setItem("editUserId", user.id.toString());
    this.router.navigate(['edit-intern']);
  };


}
