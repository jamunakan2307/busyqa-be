import {PaymentInfo} from './../auth/payment-info';
import {Component, Inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {first} from "rxjs/operators";
import {UserService} from '../services/user.service';
import {BrowserModule} from '@angular/platform-browser';

@Component({
  selector: 'app-payment-student',
  templateUrl: './payment-student.component.html',
  styleUrls: ['./payment-student.component.css']
})
export class PaymentStudentComponent implements OnInit {
  form: any = {};
  paymentInfo: PaymentInfo;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';
  editForm: FormGroup;
  payments: any;
  hideme = false;

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
  }

  ngOnInit() {
    let userId = window.localStorage.getItem("editUserId");
    if (!userId) {
      alert("Invalid action.")
      this.router.navigate(['admin']);
      return;
    }
    this.editForm = this.formBuilder.group({
      id: [''],
      amountPaid: ['', Validators.required],
      weeklyAmountPaid: [''],
      scheduledPaymentDate: [''],
      paymentNotes: ['', Validators.required],
      totalAmount: [''],
      differenceAmount: [''],
      paymentDate: [''],
      recordStatus: [''],
      user: [''],
      payLock:['']
    });
    this.userService.getPaymentHistory(+userId)
      .subscribe(data => {
        if (data.status === 200) {
          this.payments = data.result;
          this.editForm.setValue(data.result);
        } else {
          alert(data.message);
        }
      });
  }

  onSubmit() {
    let userId = window.localStorage.getItem("editUserId");
    this.userService.updatePaymentHistory(+userId, this.editForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            alert(data.message);
            this.payments = data.result;
            this.router.navigate(['home']);
          } else {
            alert(data.message);
          }
        });
  }
}
