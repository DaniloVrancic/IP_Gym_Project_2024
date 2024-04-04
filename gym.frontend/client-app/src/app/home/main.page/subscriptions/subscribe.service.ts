import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscribeService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public addSubscription(userId: number, programId: number): Observable<any> {
    return this.http.post<any>(`${this.apiServerUrl}/subscribe/add?userId=${userId}&programId=${programId}`, null);
  }

  public getAllSubscriptions(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiServerUrl}/subscribe/find_all`);
  }

  public getSubscriptionsByUserId(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiServerUrl}/subscribe/find_by_user?id=${userId}`);
  }

  public getSubscriptionsByProgramId(programId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiServerUrl}/subscribe/find_by_program?id=${programId}`);
  }


  public userHasSubscription(userId: number, programId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiServerUrl}/subscribe/user_has_subscription?userId=${userId}&programId=${programId}`);
  }

  public removeSubscriptionByUserAndType(userId: number, programTypeId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/subscribe/delete_user_type?userId=${userId}&programTypeId=${programTypeId}`);
  }
  

  public removeSubscriptionById(subscriptionId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/subscribe/delete?id=${subscriptionId}`);
  }
}
