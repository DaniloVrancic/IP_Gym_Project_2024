import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { CompletedExercise } from './completed.exercise';


@Injectable({ providedIn: 'root' })
export class CompletedExerciseService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getAllCompletedExercises(): Observable<CompletedExercise[]> {
    return this.http.get<CompletedExercise[]>(`${this.apiServerUrl}/completed/find_all`);
  }

  public getCompletedExercise(id: number): Observable<CompletedExercise> {
    return this.http.get<CompletedExercise>(`${this.apiServerUrl}/completed/find/${id}`);
  }

  public getCompletedExerciseForUserId(id: number): Observable<CompletedExercise[]> {
    return this.http.get<CompletedExercise[]>(`${this.apiServerUrl}/completed/find_by_user/${id}`);
  }

  public addCompletedExercise(completedExercise: CompletedExercise): Observable<CompletedExercise> {
    return this.http.post<CompletedExercise>(`${this.apiServerUrl}/completed/add`, completedExercise);
  }

  public updateCompletedExercise(completedExercise: CompletedExercise): Observable<CompletedExercise> {
    return this.http.put<CompletedExercise>(`${this.apiServerUrl}/completed/update`, completedExercise);
  }

  public deleteCompletedExercise(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/completed/delete/${id}`);
  }
}
