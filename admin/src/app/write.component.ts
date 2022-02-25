import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Greeting } from './app.service';

@Component({
  templateUrl: './write.component.html'
})
export class WriteComponent {

  greeting: Greeting = { id: "", content: "" };

  constructor(private http: HttpClient) {
    this.http.get('/resource').subscribe((data: any) => this.greeting = data);
  }

  update() {
    this.http.post('/resource/changes', { content: this.greeting['content'] }).subscribe((response: any) => {
      this.greeting = response;
    });
  }
}
