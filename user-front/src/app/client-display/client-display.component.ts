import { TokenStorageService } from './../auth/token-storage.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { User } from '../model/user.model';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-client-display',
  templateUrl: './client-display.component.html',
  styleUrls: ['./client-display.component.css']
})
export class ClientDisplayComponent implements OnInit {
board : string;
errorMessage:string;
users: User[];


  constructor(private router:Router,private userService:UserService,private tokenStorageService : TokenStorageService) { }

  ngOnInit() {
    this.userService.getAllClients().subscribe(
      data=>{
        this.users = data.result;
      },
      error=>{
        this.errorMessage =`${error.status}:${JSON.parse(error.error).message}`;      }
    )
  }
  addClientUser():void{
    this.router.navigate(['adminclientignup'])
  };

}
