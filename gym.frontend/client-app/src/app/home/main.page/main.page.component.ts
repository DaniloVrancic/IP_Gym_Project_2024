import { Component } from '@angular/core';
import { MaterialModule } from '../../material/material.module';

@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './main.page.component.html',
  styleUrl: './main.page.component.css'
})
export class MainPageComponent {

}
