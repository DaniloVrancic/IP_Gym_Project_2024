import { Component } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { UserService } from '../../../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  constructor(private userService: UserService, private router: Router)
  {

  }
logoutClick() {
 this.userService.currentUser = null;
 this.router.navigate(["/start-page"]);
}

}
