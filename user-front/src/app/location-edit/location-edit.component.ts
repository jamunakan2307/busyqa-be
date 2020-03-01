import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../services/user.service';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-location-edit',
  templateUrl: './location-edit.component.html',
  styleUrls: ['./location-edit.component.css']
})
export class LocationEditComponent implements OnInit {
  editForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
  }
  ngOnInit() {
    let userId = window.localStorage.getItem("editLocationId");
    if (!userId) {
      alert("Invalid action.")
      this.router.navigate(['location-list']);
      return;
    }

    this.editForm = this.formBuilder.group({
      id: [''],
      locationName: ['', Validators.required],
      description: ['', Validators.required],
      address: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      country: ['', Validators.required],
      zipCode: ['', Validators.required],
    });

    this.userService.getOneLocation(+userId)
      .subscribe(data => {
        if (data.status === 200) {
          this.editForm.setValue(data.result);
        } else {
          alert(data.message);
        }
      });

  }

  onSubmit() {
    this.userService.updateLocation(this.editForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            alert('Course updated successfully.');
          } else {
            alert(data.message);
          }
          this.router.navigate(['location-list']);
        });

  }

}
