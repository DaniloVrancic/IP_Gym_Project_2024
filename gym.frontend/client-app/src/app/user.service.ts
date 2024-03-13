import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './user';
import { environment } from '../environments/environment';
import { LoginCredentials } from './register.form/login.form/LoginCredentials';

@Injectable({providedIn: 'root'})
export class UserService {

  private apiServerUrl =  environment.apiBaseUrl;
  public currentUser: User | null = null;

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

  public loginUser(loginCredentials: LoginCredentials) : Observable<User | string>
  {
    return this.http.post<User>(`${this.apiServerUrl}/user/login`,loginCredentials);
  }

  public setCurrentUser(user: User | null)
  {
    this.currentUser = user;
  }

  public getCurrentUser() : User | null
  {
    return this.currentUser;
  }
}
