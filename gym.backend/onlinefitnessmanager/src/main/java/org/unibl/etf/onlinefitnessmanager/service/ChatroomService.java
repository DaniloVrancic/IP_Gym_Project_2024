package org.unibl.etf.onlinefitnessmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.model.entities.ChatroomEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.UserEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.ChatroomRepository;
import org.unibl.etf.onlinefitnessmanager.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatroomService {

    private ChatroomRepository chatroomRepository;
    private UserRepository userRepository;

    @Autowired
    public ChatroomService(ChatroomRepository chatroomRepository, UserRepository userRepository)
    {this.chatroomRepository = chatroomRepository;
        this.userRepository = userRepository;}

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

    public List<ChatroomEntity> addChatToAllUsersWithType(ChatroomEntity chatroomEntity, Integer type)
    {
        List<ChatroomEntity> newChats = new ArrayList<>();

        try {
            List<UserEntity> allUsersOfType = userRepository.findByTypeIs(type);

            for(UserEntity userOfType : allUsersOfType) {
                ChatroomEntity newEntityToAdd = new ChatroomEntity();
                newEntityToAdd.setTimeOfSend(LocalDateTime.now());
                newEntityToAdd.setUser_receiver(userOfType);
                newEntityToAdd.setUser_sender(chatroomEntity.getUser_sender());
                newEntityToAdd.setText(chatroomEntity.getText());
                newEntityToAdd.setReadMsg(chatroomEntity.getReadMsg());
            newChats.add(chatroomRepository.save(newEntityToAdd));
            }
        } catch (Exception ex)
        {
            System.out.println(ex.getLocalizedMessage());
        }
        return newChats;
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
