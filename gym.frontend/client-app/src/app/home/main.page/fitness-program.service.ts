import { Injectable } from '@angular/core';
import { FitnessProgram } from './fitness-program';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FitnessProgramService {

  private apiServerUrl =  environment.apiBaseUrl
  private baseUrl = '/program'; // Base URL for the REST API

  constructor(private http: HttpClient) { }

  findAllFitnessPrograms(): Observable<FitnessProgram[]> {
    return this.http.get<FitnessProgram[]>(`${this.apiServerUrl + this.baseUrl}/find_all`);
  }

  findFitnessProgramsByName(name: string): Observable<FitnessProgram[]> {
    return this.http.get<FitnessProgram[]>(`${this.apiServerUrl + this.baseUrl}/find_by_name/${name}`);
  }

  findFitnessProgramById(id: number): Observable<FitnessProgram> {
    return this.http.get<FitnessProgram>(`${this.apiServerUrl + this.baseUrl}/find/${id}`);
  }

  addFitnessProgram(newEntity: FitnessProgram): Observable<FitnessProgram> {
    return this.http.post<FitnessProgram>(`${this.apiServerUrl + this.baseUrl}/add`, newEntity);
  }

  updateFitnessProgram(updatedEntity: FitnessProgram): Observable<FitnessProgram> {
    return this.http.put<FitnessProgram>(`${this.apiServerUrl + this.baseUrl}/update`, updatedEntity);
  }

  deleteFitnessProgram(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl + this.baseUrl}/delete/${id}`);
  }

   public getCurrentFitnessProgram() : FitnessProgram | null
   {
     return JSON.parse(sessionStorage.getItem(environment.fitnessProgramKeyString) as string);
   }

   public setCurrentFitnessProgram(fitnessProgram: FitnessProgram | null)
  {
    //UserService.currentUser = user;
    if(fitnessProgram == null)
    {
      if(sessionStorage.getItem(environment.fitnessProgramKeyString) != null)
      {
        sessionStorage.removeItem(environment.fitnessProgramKeyString);
      }
    }
    else
    {
      sessionStorage.setItem(environment.fitnessProgramKeyString, JSON.stringify(fitnessProgram));
    }
  }
}
