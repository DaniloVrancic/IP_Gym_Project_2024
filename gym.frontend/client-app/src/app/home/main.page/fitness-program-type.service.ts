import { Injectable } from '@angular/core';
import { FitnessProgramType } from './fitness-program-type'; // Make sure to import the appropriate model
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FitnessProgramTypeService {

  private apiServerUrl = environment.apiBaseUrl;
  private baseUrl = '/program_type'; // Base URL for the REST API

  constructor(private http: HttpClient) { }

  getAllFitnessProgramTypes(): Observable<FitnessProgramType[]> {
    return this.http.get<FitnessProgramType[]>(`${this.apiServerUrl + this.baseUrl}/get_all_types`);
  }

  getFitnessProgramTypesByText(text: string): Observable<FitnessProgramType[]> {
    return this.http.get<FitnessProgramType[]>(`${this.apiServerUrl + this.baseUrl}/get_types/${text}`);
  }

  getFitnessProgramTypeById(id: number): Observable<FitnessProgramType> {
    return this.http.get<FitnessProgramType>(`${this.apiServerUrl + this.baseUrl}/get_type/${id}`);
  }

  addFitnessProgramType(newEntity: FitnessProgramType): Observable<FitnessProgramType> {
    return this.http.post<FitnessProgramType>(`${this.apiServerUrl + this.baseUrl}/add_type`, newEntity);
  }

  updateFitnessProgramType(updatedEntity: FitnessProgramType): Observable<FitnessProgramType> {
    return this.http.put<FitnessProgramType>(`${this.apiServerUrl + this.baseUrl}/update_type`, updatedEntity);
  }

  deleteFitnessProgramType(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl + this.baseUrl}/delete_type/${id}`);
  }

}
