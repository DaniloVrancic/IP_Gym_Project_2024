import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {

  private apiUrl = environment.apiBaseUrl;
  private endpointDir = '/purchase';

  constructor(private http: HttpClient) { }

  addPurchase(userId: number, programId: number): Observable<any> {
    const body = {};
    return this.http.post(`${this.apiUrl + this.endpointDir}/add?userId=${userId}&programId=${programId}`, body);
  }

  findAll(): Observable<any> {
    return this.http.get(`${this.apiUrl + this.endpointDir}/find_all`);
  }

  findByUserId(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl + this.endpointDir}/find_by_user?id=${id}`);
  }

  findByProgramId(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl + this.endpointDir}/find_by_program?id=${id}`);
  }

  removePurchase(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl + this.endpointDir}/delete?id=${id}`);
  }

}
