import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {TokenStorageService} from './auth/token-storage.service';
import {environment} from '../environments/environment';
import {MatTreeFlatDataSource} from '@angular/material';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  info: any;
  showIntern: boolean = false;
  showLeads: boolean = false;
  showStudent: boolean = false;
  showAlumini: boolean = false;
  showMock: boolean = false;
  showResume: boolean = false;
  isLoggedIn = false;
  uiUrl = environment.uiUrl;
  isInternalUser = true;
  isClientUser = false;
  isAdmin = false;
  isPm = false;
  isBatchView = false;
  private roles: string[];
  private authority: string;
  leadsCount: number;
  studentCount:number;
  internCount:number;
  resumeCount:number;
  mockCount:number;
  aluminiCount:number;
  constructor(private router: Router, private token: TokenStorageService, private userService: UserService) {
  }

  ngOnInit() {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    };


    this.roles = this.token.getAuthorities();
    this.roles.every(role => {
      if (role === 'ROLE_ADMIN') {

        if (this.token.getUsername() == 'TEAM_SALES') {
          this.isBatchView = true;
          this.isAdmin = true;
          this.showLeads = true;
          this.showAlumini = true;
          this.isLoggedIn = true;
          return false;
        }
        else if (this.token.getUsername() == 'TEAM_ACCOUNTS') {
          this.isBatchView = true;
          this.showStudent = true;
          this.isAdmin = true;
          this.showIntern = true;
          this.showAlumini = true;
          this.isLoggedIn = true;
          return false;
        }
        else if (this.token.getUsername() == 'TEAM_TECH') {
          this.isBatchView = true;
          this.isAdmin = true;
          this.showIntern = true;
          this.showMock = true;
          this.showResume = true;
          this.showAlumini = true;
          this.isLoggedIn = true;
          return false;
        }
        else if (this.token.getUsername() == 'TEAM_ADMIN') {
          this.isBatchView = true;
          this.isAdmin = true;
          this.showIntern = true;
          this.showMock = true;
          this.showStudent = true;
          this.showLeads = true;
          this.showResume = true;
          this.showAlumini = true;
          this.isLoggedIn = true;
          return false;
        }
      } else if (role === 'ROLE_PM') {
        if (this.token.getUsername() == 'TEAM_SALES') {
          this.isBatchView = true;
          this.showLeads = true;
          this.showAlumini = true;
          this.isLoggedIn = true;
          this.isPm = true;
          return false;
        }
        else if (this.token.getUsername() == 'TEAM_ACCOUNTS') {
          this.showStudent = true;
          this.showAlumini = true;
          this.showIntern = true;
          this.isLoggedIn = true;
          this.isPm = true;
          return false;
        }
        else if (this.token.getUsername() == 'TEAM_TECH') {
          this.showIntern = true;
          this.showMock = true;
          this.showResume = true;
          this.showAlumini = true;
          this.isLoggedIn = true;
          this.isPm = true;
          return false;
        }
        else if (this.token.getUsername() == 'TEAM_ADMIN') {
          this.showIntern = true;
          this.showMock = true;
          this.showStudent = true;
          this.showLeads = true;
          this.showResume = true;
          this.showAlumini = true;
          this.isLoggedIn = true;
          this.isPm = true;
          return false;
        }
      }
      else if (role === 'ROLE_CLIENT') {
        this.isLoggedIn = true;
        this.isInternalUser = false;
        this.isClientUser = true;
      }
    });


  }

  logout() {
    this.isLoggedIn = false;
    this.token.signOut();
    this.router.navigate(['auth/login']);
    window.open(this.uiUrl + "/auth/login", '_self');


  }
}
