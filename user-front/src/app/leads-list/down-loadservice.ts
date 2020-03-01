import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DownloadService {
  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {
  }

  downloadFileSystem(id: number): Observable<HttpResponse<string>> {
    let headers = new HttpHeaders();
    headers = headers.append('Accept', 'text/csv; charset=utf-8');

    return this.http.get(this.apiUrl + '/download/agree/' + id, {
      headers: headers,
      observe: 'response',
      responseType: 'text'
    });
  }
}
