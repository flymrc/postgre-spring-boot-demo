import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface Greeting {
  id: string,
  content: string,
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'Demo';
  greeting?: Greeting;
  authenticated = false;
  user = '';
  constructor(private http: HttpClient) {
    http.get('/user').subscribe({
      next: (data: any) => {
        if (data['name']) {
          this.authenticated = true;
          http.get('/resource').subscribe((response: any) => this.greeting = response);
        } else {
          this.authenticated = false;
        }
      },
      error: () => { this.authenticated = false; }
    });
  }
}
