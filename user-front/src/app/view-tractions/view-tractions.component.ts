import {Component, Inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {first} from "rxjs/operators";
import {UserService} from '../services/user.service';
import {BrowserModule} from '@angular/platform-browser';

@Component({
  selector: 'app-view-tractions',
  templateUrl: './view-tractions.component.html',
  styleUrls: ['./view-tractions.component.css']
})
export class ViewTractionsComponent implements OnInit {
  editForm: any;
  payments: any;
  showPayment: boolean;

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
  }

  ngOnInit() {
    let userId = window.localStorage.getItem("editUserId");
    this.userService.getPaymentHistoryBy(+userId).pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            this.payments = data.result;
            this.showPayment = true;
          } else {
          }
        });
  }

}
