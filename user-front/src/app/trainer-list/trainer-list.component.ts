import {Component, OnInit} from '@angular/core';
import {UserService} from '../services/user.service';
import {User} from "../model/user.model";
import {TokenStorageService} from '../auth/token-storage.service';
import {Router} from "@angular/router";
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-trainer-list',
  templateUrl: './trainer-list.component.html',
  styleUrls: ['./trainer-list.component.css']
})
export class TrainerListComponent implements OnInit {
  board: string;
  errorMessage: string;
  users: User[];
  searchText;

  constructor(private router: Router, private userService: UserService, private token: TokenStorageService) {
  }

  ngOnInit() {
    this.userService.getUsersTrainer().subscribe(
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
    this.router.navigate(['trainer-info']);
  };

  addUser(): void {
    this.router.navigate(['trainer-add']);
  };

}
