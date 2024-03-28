import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../user.service';
import { PurchaseService } from '../purchase.service';
import { FitnessProgramService } from '../fitness-program.service';
import { FitnessProgram } from '../fitness-program';
import { environment } from '../../../../environments/environment';
import { User } from '../../../user';

@Component({
  selector: 'app-previous-purchases',
  standalone: true,
  imports: [],
  templateUrl: './previous.purchases.component.html',
  styleUrl: './previous.purchases.component.css',
  providers: [UserService, PurchaseService, FitnessProgramService]
})
export class PreviousPurchasesComponent implements OnInit{


  private purchases: any[];
  public purchasedPrograms: FitnessProgram[];
  public apiUrl: string;
  public currentUser: User;

  constructor(private userService: UserService, private purchaseService: PurchaseService, private fitnessProgramService: FitnessProgramService)
  {
    this.purchases = [];
    this.purchasedPrograms = [];
    this.apiUrl = environment.apiBaseUrl;
    this.currentUser = userService.getCurrentUser() as User;
  }

  ngOnInit(): void {
      this.purchaseService.findAll().subscribe(response => console.log(response));

      let allRows: any[] = [];
      this.purchaseService.findByUserId(this.userService.getCurrentUser()?.id as number).subscribe(response => {
        this.purchases = response;
        for(let purchase of this.purchases) { 
          this.fitnessProgramService.findFitnessProgramById(purchase.fitnessProgramId as number)
                                    .subscribe(response => {this.purchasedPrograms.push(response); console.log(response);})
                                  }});
  }
}
