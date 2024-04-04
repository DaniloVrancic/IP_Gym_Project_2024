import { Component, OnInit } from '@angular/core';
import { SubscribeService } from './subscribe.service';
import { UserService } from '../../../user.service';
import { FitnessProgramTypeService } from '../fitness-program-type.service';
import { FitnessProgramType } from '../fitness-program-type';

@Component({
  selector: 'app-subscriptions',
  standalone: true,
  imports: [],
  templateUrl: './subscriptions.component.html',
  styleUrl: './subscriptions.component.css',
  providers: [SubscribeService, UserService, FitnessProgramTypeService]
})
export class SubscriptionsComponent implements OnInit {


  public subscriptions: any[];
  fitnessTypes: FitnessProgramType[];
  public fitnessTypeMap: Map<number,string>; //ID to name mapping, (alias mapping)


  constructor(subscribeService: SubscribeService, 
              userService: UserService, fitnessTypeService: FitnessProgramTypeService)
  {
    this.subscriptions = [];
    this.fitnessTypes = [];
    this.fitnessTypeMap = new Map<number,string>();
    subscribeService.getSubscriptionsByUserId(userService.getCurrentUser()?.id as number)
                                      .subscribe(allSubs => {this.subscriptions = allSubs; console.log(this.subscriptions);});
    fitnessTypeService.getAllFitnessProgramTypes().subscribe(allTypes => {this.fitnessTypes = allTypes; this.fitnessTypes.forEach(x => this.fitnessTypeMap.set(x.id as number, x.name)); console.log(this.fitnessTypeMap)});


  }
  ngOnInit(): void {
    
  }

  onClick() {
    console.log(this.subscriptions);
    }
}
