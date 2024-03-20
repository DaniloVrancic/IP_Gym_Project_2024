import { Component } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { Router, RouterLink, RouterLinkActive, RouterLinkWithHref } from '@angular/router';

import { UserService } from '../../user.service';
import { User } from '../../user';

@Component({
  selector: 'app-start-page',
  standalone: true,
  imports: [MaterialModule, RouterLink, RouterLinkActive],
  templateUrl: './start-page.component.html',
  styleUrl: './start-page.component.css',
  providers: [UserService]
})
export class StartPageComponent {

  constructor(public userService: UserService, private router: Router)
  {}

  routeAsGuest() {
    let guestUser: User | null = {
      id: null,
      username: null,
      password: null,
      firstName: null,
      lastName: null,
      city: null,
      avatar: null,
      email: null,
      activated: null,
      type: null
    } ;
    guestUser.username = "guest";
    this.userService.setCurrentUser(guestUser)

    console.log("In start page:");
    console.log(this.userService.getCurrentUser());
    this.router.navigate(["/main-page"]);
    
    
}
  
}
