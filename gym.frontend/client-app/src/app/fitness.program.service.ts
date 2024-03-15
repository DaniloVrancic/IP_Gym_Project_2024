import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, ObservableInput } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FitnessProgramService {

  private baseUrl = '/program'; // Assuming your backend API endpoint

  caught: Observable<any> | undefined;

  constructor(private http: HttpClient) {}

  findAllFitnessPrograms(): Observable<any[]> {
    return this.http.get<any[]>(`${environment.apiBaseUrl + this.baseUrl}/find_all`)
      .pipe(
        catchError(this.handleError)
      );
  }

  findFitnessProgramsByName(name: string): Observable<any[]> {
    return this.http.get<any[]>(`${environment.apiBaseUrl + this.baseUrl}/find_by_name/${name}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  findFitnessProgramById(id: number): Observable<any> {
    return this.http.get<any>(`${environment.apiBaseUrl + this.baseUrl}/find/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  addFitnessProgram(newEntity: any): Observable<any> {
    return this.http.post<any>(`${environment.apiBaseUrl + this.baseUrl}/add`, newEntity)
      .pipe(
        catchError(this.handleError)
      );
  }

  updateFitnessProgram(newEntity: any): Observable<any> {
    return this.http.put<any>(`${environment.apiBaseUrl + this.baseUrl}/update`, newEntity)
      .pipe(
        catchError(this.handleError)
      );
  }

  deleteFitnessProgram(id: number): Observable<any> {
    return this.http.delete<any>(`${environment.apiBaseUrl + this.baseUrl}/delete/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  public handleError(error: HttpErrorResponse): ObservableInput<any> {
    console.error('An error occurred:', error);
    throw error;
  }
}
