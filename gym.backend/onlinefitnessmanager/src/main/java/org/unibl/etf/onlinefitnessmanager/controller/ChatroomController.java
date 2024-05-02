package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.onlinefitnessmanager.model.entities.ChatroomEntity;
import org.unibl.etf.onlinefitnessmanager.service.ChatroomService;

import java.util.List;

@Controller
@RequestMapping("/chatroom")
public class ChatroomController {

    private final ChatroomService chatroomService;

    @Autowired
    public ChatroomController(ChatroomService chatroomService) {
        this.chatroomService = chatroomService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addChat(@RequestBody ChatroomEntity chatroomEntity) {
        ChatroomEntity newChat = chatroomService.addChat(chatroomEntity);
        if (newChat != null) {
            return new ResponseEntity<>(newChat, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to add chat", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/mark_as_read")
    public ResponseEntity<?> markAsRead(@RequestBody ChatroomEntity chatroomEntity) {
        ChatroomEntity updatedChat = chatroomService.markAsRead(chatroomEntity);
        if (updatedChat != null) {
            return new ResponseEntity<>(updatedChat, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to mark chat as read", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add_to_users/{typeId}")
    public ResponseEntity<List<ChatroomEntity>> addChatToAllUsersWithType(@RequestBody ChatroomEntity chatroomEntity, @PathVariable("typeId") Integer typeId) {
        List<ChatroomEntity> newChats = null;
        newChats = chatroomService.addChatToAllUsersWithType(chatroomEntity, typeId);
        if (newChats != null) {
            return ResponseEntity.ok(newChats);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/receiver_chats/{receiverId}")
    public ResponseEntity<List<ChatroomEntity>> getAllReceiverChats(@PathVariable("receiverId") Integer receiverId) {
        List<ChatroomEntity> receiverChats = chatroomService.getAllReceiverChats(receiverId);
        if (!receiverChats.isEmpty()) {
            return new ResponseEntity<>(receiverChats, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/sender_chats/{senderId}")
    public ResponseEntity<List<ChatroomEntity>> getAllSenderChats(@PathVariable("senderId") Integer senderId) {
        List<ChatroomEntity> senderChats = chatroomService.getAllSenderChats(senderId);
        if (!senderChats.isEmpty()) {
            return new ResponseEntity<>(senderChats, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
