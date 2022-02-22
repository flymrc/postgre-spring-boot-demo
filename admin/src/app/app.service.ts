import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

export interface Greeting {
  id: string;
  content: string;
}

export interface User {
  name: string;
  roles: string;
}

@Injectable()
export class AppService {

  error?: string;
  writer?: boolean;
  authenticated = false;

  constructor(private http: HttpClient) {
  }

  authenticate(callback: { (response: any): void; (arg0: Object): void; }) {

    this.http.get('/user').subscribe({
      next: (user: any) => {
        if (user['name']) {
          this.authenticated = true;
          this.writer = user['roles'] && user['roles'].indexOf('ROLE_WRITER') > 0;
        } else {
          this.authenticated = false;
          this.writer = false;
        }
        if (callback) { callback(user); }
      },
      error: response => {
        if (response.status === 0) {
          this.error = 'No connection. Verify application is running.';
        } else if (response.status === 401) {
          this.error = 'Unauthorized.';
        } else if (response.status === 403) {
          this.error = 'Forbidden.';
        } else {
          this.error = 'Unknown.';
        }
        this.authenticated = false;
        this.writer = false;
      }
    });
  }
}
