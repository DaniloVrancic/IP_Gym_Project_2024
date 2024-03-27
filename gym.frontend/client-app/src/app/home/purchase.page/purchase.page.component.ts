import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { Router } from '@angular/router';
import { MatRadioButton, MatRadioGroup, MatRadioModule } from '@angular/material/radio';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MyErrorStateMatcher } from '../../register.form/register/register.form.component';
import { CreditCardFormatDirective } from './credit.card.format.directive';

@Component({
  selector: 'app-purchase-page',
  standalone: true,
  imports: [MaterialModule, MatRadioGroup, FormsModule, ReactiveFormsModule],
  templateUrl: './purchase.page.component.html',
  styleUrl: './purchase.page.component.css'
})
export class PurchasePageComponent implements OnInit{


  paymentMethod: string; //Will be set by the radio group
  payMethods: string[] = ['Credit Card', 'PayPal', 'In person'];


  creditCardNumberControl = new FormControl('', [Validators.required]);
  paypalInputControl = new FormControl('', [Validators.required, Validators.email]);
 

  matcher = new MyErrorStateMatcher();

  constructor(private router: Router)
  {
    this.paymentMethod = "";
  }

  ngOnInit(): void {
    const greenButton = document.getElementById("green-purchase-button") as HTMLButtonElement;
    greenButton.disabled = true;
    console.log(greenButton);
  }

  purchaseClick() {
    console.log("PURCHASE CLICKED!");
    }
}
