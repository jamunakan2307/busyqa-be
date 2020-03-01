import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {from, Observable} from 'rxjs';

import {JwtResponse} from './jwt-response';
import {AuthLoginInfo} from './login-info';
import {SignUpInfo} from './signup-info';
import {environment} from '../../environments/environment';
import {AdminSignUpInfo} from "./adminsingup-info";
import {ApiResponse} from "../services/api.response";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiUrl = environment.apiUrl;
  private loginUrl = this.apiUrl + '/api/auth/signin';
  private signupUrl = this.apiUrl + '/api/auth/signup';
  private adminSignUpUrl = this.apiUrl + '/api/auth/adminsignup';

  constructor(private http: HttpClient) {
  }

  attemptAuth(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }

  signUp(info: SignUpInfo): Observable<string> {
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }

  adminsignUp(info: AdminSignUpInfo): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.adminSignUpUrl, info, httpOptions);
  }
}
