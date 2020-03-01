import {Component, OnInit} from '@angular/core';
import {User} from '../model/user.model';
import {environment} from 'src/environments/environment';
import {Router} from '@angular/router';
import {UserService} from '../services/user.service';
import {TokenStorageService} from '../auth/token-storage.service';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-mock-list',
  templateUrl: './mock-list.component.html',
  styleUrls: ['./mock-list.component.css']
})
export class MockListComponent implements OnInit {
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
    this.userService.getMockByTeam().subscribe(
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
    this.router.navigate(['edit-mock']);
  };
}

