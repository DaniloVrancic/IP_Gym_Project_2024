import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { Router } from '@angular/router';
import { MatRadioButton, MatRadioGroup, MatRadioModule } from '@angular/material/radio';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MyErrorStateMatcher } from '../../register.form/register/register.form.component';
import { CreditCardFormatDirective } from './credit.card.format.directive';
import { FitnessProgramService } from '../main.page/fitness-program.service';
import { PurchaseService } from '../main.page/purchase.service';
import { UserService } from '../../user.service';

@Component({
  selector: 'app-purchase-page',
  standalone: true,
  imports: [MaterialModule, MatRadioGroup, FormsModule, ReactiveFormsModule],
  templateUrl: './purchase.page.component.html',
  styleUrl: './purchase.page.component.css',
  providers: [FitnessProgramService, PurchaseService, UserService]
})
export class PurchasePageComponent implements OnInit{

  paymentMethod: string; //Will be set by the radio group
  payMethods: string[] = ['Credit-Card', 'PayPal', 'In-person'];


  creditCardNumberControl = new FormControl('', [Validators.required]);
  paypalInputControl = new FormControl('', [Validators.required, Validators.email]);

  matcher = new MyErrorStateMatcher(); //Error matcher, got it from another class

  constructor(private router: Router, public fitnessProgramService: FitnessProgramService, private purchaseService: PurchaseService, private userService: UserService)
  {
    this.paymentMethod = ""; // selected payment method.
  }

  ngOnInit(): void {
   // let inPersonRadioButton : any = document.getElementById('radio-button-In-person-input');
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

    this.purchaseService.addPurchase((this.userService.getCurrentUser()?.id as number), (this.fitnessProgramService.getCurrentFitnessProgram()?.id as number)).subscribe(() => {
                      alert("Successful purchase!");
                       if(this.fitnessProgramService.getCurrentFitnessProgram()?.locationOfEvent?.toLowerCase() === "online") //case if the program is online
                       {
                       const randomExcerciseVideoLink = 
                       [
                         'https://www.youtube.com/watch?v=XxuRSjER3Qk',
                         'https://www.youtube.com/watch?v=c1mBu4tK90k',
                         'https://www.youtube.com/watch?v=J212vz33gU4',
                         'https://www.youtube.com/watch?v=kZDvg92tTMc'
                       ];
                       //Generate random index in the array range
                       const randomIndex = Math.floor(Math.random() * randomExcerciseVideoLink.length);

                       //Choose the link with the randomly generated index:
                       const randomLink: string = randomExcerciseVideoLink[randomIndex];

                       this.router.navigate(['/main-page']);
                       window.open(randomLink, '_blank');
                       }
                     else //case if the program is anything else but 'online'
                     {
                     this.router.navigate(['/main-page']);
                     }
                   });
    }
}
