import { Component, Input } from '@angular/core';
import { LoginService } from '../login.service';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  @Input() header = '';
  public user = 'User';
  public isLoging:boolean = false;

  constructor(public loginService: LoginService) {}

  ngOnInit() {
    this.isLoging = this.loginService.isLogging;
  }


  public logOut() {
    this.isLoging
  }
}
