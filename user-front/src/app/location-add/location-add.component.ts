import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder} from '@angular/forms';
import {UserService} from '../services/user.service';
import {CourseInfo} from '../auth/course-info';
import {first} from 'rxjs/operators';
import { LocationInfo } from '../model/location.model';

@Component({
  selector: 'app-location-add',
  templateUrl: './location-add.component.html',
  styleUrls: ['./location-add.component.css']
})
export class LocationAddComponent implements OnInit {
  form: any = {};
  errorMessage = '';
  locationInfo : LocationInfo;
  status = 'Active';

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
  }

  ngOnInit() {
  }

  onSubmit() {
    this.locationInfo = new LocationInfo(this.form.locationName, this.form.description, this.form.address, this.form.city, this.form.state, this.form.country, this.form.zipCode);
    this.userService.createLocation(this.locationInfo).pipe(first())
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
