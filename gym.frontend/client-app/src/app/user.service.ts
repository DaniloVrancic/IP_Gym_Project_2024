import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './user';
import { environment } from '../environments/environment';
import { LoginCredentials } from './register.form/login.form/LoginCredentials';

@Injectable({providedIn: 'root'})
export class UserService {
  

  private apiServerUrl =  environment.apiBaseUrl;
  //static currentUser: User | null = null;

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
    //UserService.currentUser = user;
    if(user == null)
    {
      if(sessionStorage.getItem(environment.userKeyString) != null)
      {
        sessionStorage.removeItem(environment.userKeyString);
      }
    }
    else
    {
      sessionStorage.setItem(environment.userKeyString, JSON.stringify(user));
    }
  }

  public getCurrentUser() : User | null
  {
    return JSON.parse(sessionStorage.getItem(environment.userKeyString) as string);
  }

  public findUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(`${this.apiServerUrl}/user/find_by_username/${username}`);
  }
}
