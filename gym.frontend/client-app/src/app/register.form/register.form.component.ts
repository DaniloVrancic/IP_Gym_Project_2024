import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [],
  templateUrl: './register.form.component.html',
  styleUrl: './register.form.component.css'
})
export class RegisterFormComponent {

  registerUserFormControl = new FormControl('');
}
