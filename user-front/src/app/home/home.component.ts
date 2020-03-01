import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {TokenStorageService} from '../auth/token-storage.service';
import {environment} from '../../environments/environment';
import {MatTreeFlatDataSource} from '@angular/material';
import Chatkit from '@pusher/chatkit-client';
import axios from 'axios';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
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
  title = 'BusyQa Chatting';
  messages = [];
  users = [];
  currentUser: any;
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

  _username: string = '';
  get username(): string {
    return this._username;
  }

  set username(value: string) {
    this._username = value;
  }

  _message: string = '';
  get message(): string {
    return this._message;
  }

  set message(value: string) {
    this._message = value;
  }

  ngOnInit() {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    };

    this.userService.getAllCount()
    .subscribe(data => {
      if (data.status === 200) {

        this.leadsCount = data.result.leadsCount;
        this.aluminiCount = data.result.aluminiCount;
        this.studentCount = data.result.studentCount;
        this.internCount = data.result.internCount;
        this.resumeCount = data.result.resumeCount;
        this.mockCount = data.result.mockCount;
      } else {
        alert(data.message);
      }
    });

    this.roles = this.token.getAuthorities();
    this.roles.every(role => {
      if (role === 'ROLE_ADMIN') {
        if (this.token.getUsername() == 'TEAM_SALES') {
          this.isAdmin = true;
          this.showLeads = true;
          this.showAlumini = true;
          this.isLoggedIn = true;
          return false;
        }
        else if (this.token.getUsername() == 'TEAM_ACCOUNTS') {
          this.showStudent = true;
          this.isAdmin = true;
          this.showIntern = true;
          this.showAlumini = true;
          this.isLoggedIn = true;
          return false;
        }
        else if (this.token.getUsername() == 'TEAM_TECH') {
          this.isAdmin = true;
          this.showIntern = true;
          this.showMock = true;
          this.showResume = true;
          this.showAlumini = true;
          this.isLoggedIn = true;
          return false;
        }
        else if (this.token.getUsername() == 'TEAM_ADMIN') {
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

  sendMessage() {
    const {message, currentUser} = this;
    currentUser.sendMessage({
      text: message,
      roomId: '19442079',
    });
    this.message = '';
  }

  addUser() {
    const {username} = this;
    axios.post('http://localhost:5200/users', {username})
      .then(() => {
        const tokenProvider = new Chatkit.TokenProvider({
          url: 'http://localhost:5200/authenticate'
        });

        const chatManager = new Chatkit.ChatManager({
          instanceLocator: 'v1:us1:c869aa48-36d3-4191-87b9-836895205c10',
          userId: username,
          tokenProvider
        });

        return chatManager
          .connect()
          .then(currentUser => {
            currentUser.subscribeToRoom({
              roomId: '19442079',
              messageLimit: 100,
              hooks: {
                onMessage: message => {
                  this.messages.push(message);
                },
                onPresenceChanged: (state, user) => {
                  this.users = currentUser.users.sort((a) => {
                    if (a.presence.state === 'online') return -1;

                    return 1;
                  });
                },
              },
            });

            this.currentUser = currentUser;
            this.users = currentUser.users;

          });
      })
      .catch(error => console.error(error))
  }


}
