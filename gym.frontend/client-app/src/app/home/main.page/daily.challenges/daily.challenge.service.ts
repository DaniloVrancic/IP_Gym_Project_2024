import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { DailyChallenge } from './daily.challenge';

@Injectable({providedIn: 'root'})
export class DailyChallengeService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient){}

    public getTodaysDailyChallenges(): Observable<any[]>
    {
        return this.http.get<DailyChallenge[]>(`${this.apiServerUrl}/recommended/get`);
    }
}