import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { Router } from '@angular/router';
import { MatRadioButton, MatRadioGroup, MatRadioModule } from '@angular/material/radio';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MyErrorStateMatcher } from '../../register.form/register/register.form.component';
import { CreditCardFormatDirective } from './credit.card.format.directive';
import { FitnessProgramService } from '../main.page/fitness-program.service';

@Component({
  selector: 'app-purchase-page',
  standalone: true,
  imports: [MaterialModule, MatRadioGroup, FormsModule, ReactiveFormsModule],
  templateUrl: './purchase.page.component.html',
  styleUrl: './purchase.page.component.css',
  providers: [FitnessProgramService]
})
export class PurchasePageComponent implements OnInit{



  paymentMethod: string; //Will be set by the radio group
  payMethods: string[] = ['Credit Card', 'PayPal', 'In person'];


  creditCardNumberControl = new FormControl('', [Validators.required]);
  paypalInputControl = new FormControl('', [Validators.required, Validators.email]);
 

  matcher = new MyErrorStateMatcher();

  constructor(private router: Router, public fitnessProgramService: FitnessProgramService)
  {
    this.paymentMethod = "";
  }

  ngOnInit(): void {
    
  }

  isButtonEnabled(): boolean {
    switch(this.paymentMethod)
    {
      case this.payMethods[0]:
        if(this.creditCardNumberControl.valid)
        {
          return true;
        }
        break;
      case this.payMethods[1]:
        if(this.paypalInputControl.valid)
        {
          return true;
        }
        break;
      case this.payMethods[2]:
        return true;
      default:
        return false;
    }
    return false;
    }

  purchaseClick() {
    console.log("PURCHASE CLICKED!");
    }
}
