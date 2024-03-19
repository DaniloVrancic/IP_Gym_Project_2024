package org.unibl.etf.onlinefitnessmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.model.entities.ChatroomEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.ChatroomRepository;

import java.time.LocalDateTime;

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

}
