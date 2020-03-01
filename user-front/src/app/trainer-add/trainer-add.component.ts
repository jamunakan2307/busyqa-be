import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import {AdminSignUpInfo} from "../auth/adminsingup-info";
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-trainer-add',
  templateUrl: './trainer-add.component.html',
  styleUrls: ['./trainer-add.component.css']
})
export class TrainerAddComponent implements OnInit {

  selectedItems2 = [];
  dropdownSettings2 = {};
  dropdownList2 = [];

  //Team
  teamSelectedItems2 = [];
  teamDropdownSettings2 = {};
  teamDropdownList2 = [];


  form: any = {};
  asignupInfo: AdminSignUpInfo;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
    this.dropdownList2 = [
      {id: 1, name: 'ROLE_USER'}
    ];
    this.dropdownSettings2 = {
      singleSelection: true,
      idField: 'id',
      textField: 'name',
      itemsShowLimit: 1,
      scrollableHeight: '300px',
      scrollable: true,
      allowSearchFilter: false
    };
    this.teamDropdownList2 = [
      {id: 1, name: 'TEAM_TRAINER'}
    ];

    this.teamDropdownSettings2 = {
      singleSelection: true,
      idField: 'id',
      textField: 'name',
      itemsShowLimit: 1,
      scrollableHeight: '300px',
      scrollable: true,
      allowSearchFilter: false
    };
  }

  onItemSelect2(item: any) {
    console.log(item);
  }

  onSelectAll2(items: any) {
    console.log(items);
  }

  onSubmit() {
    console.log(this.form);

    this.asignupInfo = new AdminSignUpInfo(
      this.form.firstName,
      this.form.lastName,
      this.form.username,
      this.form.email,
      this.form.password,
      this.form.roles,
      this.form.teams);

    this.authService.adminsignUp(this.asignupInfo).pipe(first()).subscribe(
      data => {
        if (data.status === 200) {
          alert('Course updated successfully.');
        } else {
          alert(data.message);
        }
        this.router.navigate(['trainer-list']);
      });
  }
}

