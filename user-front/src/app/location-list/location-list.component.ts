import {Component, OnInit} from '@angular/core';
import {UserService} from '../services/user.service';
import {TokenStorageService} from '../auth/token-storage.service';
import {Router} from "@angular/router";
import {HttpClient} from '@angular/common/http';
import {Course} from '../model/course.model';

@Component({
  selector: 'app-location-list',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']
})
export class LocationListComponent implements OnInit {
  errorMessage: string;
  locations: any[];
  searchText;


  constructor(private router: Router, private userService: UserService, private token: TokenStorageService, private http: HttpClient) {
  }

  ngOnInit() {
    this.userService.getAllLocations().subscribe(
      data => {
        this.locations = data.result;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    )
  }

  addLocation(): void {
    this.router.navigate(['location-add']);
  };

  editLocation(course: Course): void {
    window.localStorage.removeItem("editLocationId");
    window.localStorage.setItem("editLocationId", course.id.toString());
    this.router.navigate(['location-edit']);
  };

}
