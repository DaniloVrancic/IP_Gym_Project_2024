import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }

  private handleError(error: any) {
    console.error('Error:', error);
    return throwError(error);
  }

  getAllComments(): Observable<any> {
    return this.http.get<any>(`${environment.apiBaseUrl}/find_all`).pipe(
      catchError(this.handleError)
    );
  }

  getCommentsForProgram(programId: number): Observable<any> {
    return this.http.get<any>(`${environment.apiBaseUrl}/find_for_program/${programId}`).pipe(
      catchError(this.handleError)
    );
  }

  getCommentsFromUser(userId: number): Observable<any> {
    return this.http.get<any>(`${environment.apiBaseUrl}/find_for_user/${userId}`).pipe(
      catchError(this.handleError)
    );
  }

  deleteComment(commentId: number): Observable<any> {
    return this.http.delete<any>(`${environment.apiBaseUrl}/delete/${commentId}`).pipe(
      catchError(this.handleError)
    );
  }

  addComment(userId: number, programId: number, comment: string): Observable<any> {
    const body = { user_id: userId, program_id: programId, comment: comment };
    return this.http.post<any>(`${environment.apiBaseUrl}/add`, body).pipe(
      catchError(this.handleError)
    );
  }

  updateComment(comment: any): Observable<any> {
    return this.http.put<any>(`${environment.apiBaseUrl}/update`, comment).pipe(
      catchError(this.handleError)
    );
  }
}