import { Component, OnInit } from '@angular/core';
import { AppService } from './app.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent {

  title = 'Demo';
  greeting: any = {};

  constructor(private app: AppService, private http: HttpClient) {
    http.get('resource', { headers: new HttpHeaders() })
    .subscribe(response => this.greeting = response);
  }

  authenticated() { return this.app.authenticated; }

}
