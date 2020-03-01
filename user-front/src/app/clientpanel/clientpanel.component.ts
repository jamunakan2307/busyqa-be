import {Component, Inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../model/user.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {first} from "rxjs/operators";
import {UserService} from '../services/user.service';
import {BrowserModule} from '@angular/platform-browser';
import {TokenStorageService} from '../auth/token-storage.service';

@Component({
  selector: 'app-clientpanel',
  templateUrl: './clientpanel.component.html',
  styleUrls: ['./clientpanel.component.css']
})
export class ClientpanelComponent implements OnInit {
  board: string;
  errorMessage: string;
  users: User[];
  payments: any;

  constructor(private router: Router, private userService: UserService, private token: TokenStorageService) {
  }

  ngOnInit() {
    this.userService.getClientsByUsername().subscribe(
      data => {
        this.users = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )
    this.userService.getPaymentHistoryBy2().pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            this.payments = data.result;
          } else {
          }
        });
  }

}
