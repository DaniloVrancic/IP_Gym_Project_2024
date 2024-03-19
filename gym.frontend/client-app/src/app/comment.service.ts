import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private mappingUrl: string;

  constructor(private http: HttpClient) {
    this.mappingUrl= "/comment";
   }

  private handleError(error: any) {
    console.error('Error:', error);
    throw (error);
  }

  getAllComments(): Observable<any> {
    return this.http.get<any>(`${environment.apiBaseUrl + this.mappingUrl}/find_all`)
    ;
  }

  getCommentsForProgram(programId: number): Observable<any> {
    return this.http.get<any>(`${environment.apiBaseUrl + this.mappingUrl}/find_for_program/${programId}`);
  }

  getCommentsFromUser(userId: number): Observable<any> {
    return this.http.get<any>(`${environment.apiBaseUrl + this.mappingUrl}/find_for_user/${userId}`);
  }

  deleteComment(commentId: number): Observable<any> {
    return this.http.delete<any>(`${environment.apiBaseUrl + this.mappingUrl}/delete/${commentId}`);
  }

  addComment(userId: number, programId: number, comment: string): Observable<any> {
    const body = { user_id: userId, program_id: programId, comment: comment };
    return this.http.post<any>(`${environment.apiBaseUrl + this.mappingUrl}/add`, body);
  }

  updateComment(comment: any): Observable<any> {
    return this.http.put<any>(`${environment.apiBaseUrl + this.mappingUrl}/update`, comment);
  }
}