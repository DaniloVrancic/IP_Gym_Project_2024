package org.unibl.etf.onlinefitnessmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.model.entities.ChatroomEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.ChatroomRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatroomService {

    private ChatroomRepository chatroomRepository;

    @Autowired
    public ChatroomService(ChatroomRepository chatroomRepository){this.chatroomRepository = chatroomRepository;}

    public ChatroomEntity addChat(ChatroomEntity chatroomEntity) {
        ChatroomEntity newChat = null;

        try {

            chatroomEntity.setTimeOfSend(LocalDateTime.now());

            newChat = chatroomRepository.save(chatroomEntity);
        } catch (Exception ex)
        {
            System.out.println(ex.getLocalizedMessage());
        }
        return newChat;
    }

    public ChatroomEntity markAsRead(ChatroomEntity chatroomEntity)
    {
        ChatroomEntity readChat = chatroomEntity;
        readChat.setReadMsg(true);

        return chatroomRepository.save(readChat);
    }

    public List<ChatroomEntity> getAllReceiverChats(Integer receiverId)
    {
        return chatroomRepository.findAllByReceiverId(receiverId);
    }

    public List<ChatroomEntity> getAllSenderChats(Integer senderId)
    {
        return chatroomRepository.findAllBySenderId(senderId);
    }

}
