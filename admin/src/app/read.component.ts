import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Greeting } from './app.service';

@Component({
  templateUrl: './read.component.html'
})
export class ReadComponent {

  greeting: Greeting = { id: "", content: "" };

  constructor(private http: HttpClient) {
    http.get('/resource').subscribe((data: any) => this.greeting = data);
  }
}
