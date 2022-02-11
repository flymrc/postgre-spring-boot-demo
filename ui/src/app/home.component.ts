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
    http.get('token').subscribe({
      next: (data: any) => {
        const token = data['token'];
        http.get('http://localhost:9000', { headers: new HttpHeaders().set('X-Auth-Token', token) })
          .subscribe(response => this.greeting = response);
      },
      error: () => { }
    });
  }

  authenticated() { return this.app.authenticated; }

}
