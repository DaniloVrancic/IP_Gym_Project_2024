import { Component } from '@angular/core';
import { DailyChallengeService } from './daily.challenge.service';
import { environment } from '../../../../environments/environment';
import { DailyChallenge } from './daily.challenge';
import { MaterialModule } from '../../../material/material.module';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-daily-challenges',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './daily.challenges.component.html',
  styleUrl: './daily.challenges.component.css',
  providers: [DailyChallengeService]
})
export class DailyChallengesComponent {
 


  dailyChallenges: DailyChallenge[] | null = null;
  displayedDailyChallenges: DailyChallenge[] | null = null;
  constructor(private dailyChallengeService: DailyChallengeService)
  {
    this.dailyChallenges = JSON.parse(sessionStorage.getItem(environment.dailyChallengesKeyString) as string);

    if(this.dailyChallenges) //If already in storage
    {
      this.displayedDailyChallenges = this.dailyChallenges.slice(0,1);
    }
    else
    {
      this.dailyChallenges = [];
      dailyChallengeService.getTodaysDailyChallenges().subscribe(response => {
                                                                              response.forEach(element => {this.dailyChallenges?.push(element)});
                                                                              sessionStorage.setItem(environment.dailyChallengesKeyString, JSON.stringify(this.dailyChallenges));
                                                                              this.displayedDailyChallenges = this.dailyChallenges?.slice(0,1) as DailyChallenge[] | null;
                                                                             })                                                       
    }
  }

  pageChanged(event: any) {
    const startIndex = event.pageIndex * event.pageSize;
    const endIndex = startIndex + event.pageSize;
    this.displayedDailyChallenges = this.dailyChallenges?.slice(startIndex, endIndex) as DailyChallenge[];
  }
}
