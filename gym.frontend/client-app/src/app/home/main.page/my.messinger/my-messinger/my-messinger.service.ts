import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';
import { ChatroomEntity } from './chatroomEntity';

@Injectable({
  providedIn: 'root'
})
export class MyMessingerService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public addChat(chatroomEntity: ChatroomEntity): Observable<ChatroomEntity> {
    return this.http.post<ChatroomEntity>(`${this.apiServerUrl}/chatroom/add`, chatroomEntity);
  }

  public markAsRead(chatroomEntity: ChatroomEntity): Observable<ChatroomEntity> {
    return this.http.put<ChatroomEntity>(`${this.apiServerUrl}/chatroom/mark_as_read`, chatroomEntity);
  }

  public getAllReceiverChats(receiverId: number): Observable<ChatroomEntity[]> {
    return this.http.get<ChatroomEntity[]>(`${this.apiServerUrl}/chatroom/receiver_chats/${receiverId}`);
  }

  public getAllSenderChats(senderId: number): Observable<ChatroomEntity[]> {
    return this.http.get<ChatroomEntity[]>(`${this.apiServerUrl}/chatroom/sender_chats/${senderId}`);
  }
}
