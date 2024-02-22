import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({providedIn: 'root'})
export class UserService {

  private apiServerUrl = '';

  constructor(private http: HttpClient) { }

  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiServerUrl}/user/find_all`);
  }

  public getUser(id: number) : Observable<User>
  {
    return this.http.get<User>(`${this.apiServerUrl}/user/find/${id}`);
  }

  public addUser(user: User) : Observable<User>
  {
    return this.http.post<User>(`${this.apiServerUrl}/user/add`, user);
  }

  public updateUser(user: User) : Observable<User>
  {
    return this.http.put<User>(`${this.apiServerUrl}/user/update`, user);
  }

  public deleteUser(userId: number) : Observable<void>
  {
    return this.http.delete<void>(`${this.apiServerUrl}/user/delete/${userId}`);
  }
}
