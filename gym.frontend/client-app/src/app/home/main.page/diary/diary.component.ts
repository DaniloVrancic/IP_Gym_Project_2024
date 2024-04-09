import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { UserService } from '../../../user.service';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DateAdapter, provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerControl, MatDatepickerPanel } from '@angular/material/datepicker';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-diary',
  standalone: true,
  imports: [MaterialModule, FormsModule, ReactiveFormsModule],
  templateUrl: './diary.component.html',
  styleUrl: './diary.component.css',
  providers: [UserService, provideNativeDateAdapter()]
})
export class DiaryComponent implements OnInit{
  dateControl = new FormControl();
  private _locale: string;
  controlGroup: FormGroup;
  apiUrl: string;
  constructor(private userService: UserService, private dateAdapter: DateAdapter<Date>, private _fb: FormBuilder)
  {
    this._locale = "en-EN"
    this.dateAdapter.setLocale(this._locale);
    this.apiUrl = environment.apiBaseUrl;

    this.controlGroup = this._fb.group({
      completedCategory: [``, [Validators.required]],
      completedDuration: [``, [Validators.required]]
    });
  }

  ngOnInit(): void {
      
  }

  dateInputMethod(event: any)
  {
    if (this.dateControl.value) {
      const selectedDate = new Date(this.dateControl.value);
      const year = selectedDate.getFullYear();
      const month = (selectedDate.getMonth() + 1).toString().padStart(2, '0'); // Months are zero-based
      const day = selectedDate.getDate().toString().padStart(2, '0');
      const formattedDate = `${year}-${month}-${day}`;
      console.log(formattedDate);
    }
  }
}
