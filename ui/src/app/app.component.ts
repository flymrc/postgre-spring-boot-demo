import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = "Demo";
  authenticated = false;
  greeting: any = {};

  constructor(private http: HttpClient) {
    this.authenticate();
  }

  authenticate() {
    this.http.get('user').subscribe({
      next: (response: any) => {
        if (response['name']) {
          this.authenticated = true;
        } else {
          this.authenticated = false;
        }
      },
      error: () => {
        this.authenticated = false;
      }
    });
  }

  logout() {
    this.http.post('logout', {}).pipe(
      finalize(() => {
        this.authenticated = false;
      })
    ).subscribe();
  }
}
