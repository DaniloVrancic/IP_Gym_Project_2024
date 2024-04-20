package org.unibl.etf.onlinefitnessmanager.model.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "chatroom", schema = "db_online_fitness", catalog = "")
public class ChatroomEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "time_of_send", nullable = false)
    private LocalDateTime timeOfSend;
    @Basic
    @Column(name = "text", nullable = true, length = -1)
    private String text;

    @Basic
    @Column(name = "read_msg", nullable = false)
    private Boolean read_msg;
    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false)
    private UserEntity user_receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    private UserEntity user_sender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTimeOfSend() {
        return timeOfSend;
    }

    public void setTimeOfSend(LocalDateTime timeOfSend) {
        this.timeOfSend = timeOfSend;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getReadMsg()
    {
        return this.read_msg;
    }

    public void setReadMsg(Boolean read)
    {
        this.read_msg = read;
    }

    public UserEntity getUser_receiver() {
        return user_receiver;
    }

    public void setUser_receiver(UserEntity user_friend) {
        this.user_receiver = user_friend;
    }

    public UserEntity getUser_sender() {
        return user_sender;
    }

    public void setUser_sender(UserEntity user_sender) {
        this.user_sender = user_sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatroomEntity that = (ChatroomEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(timeOfSend, that.timeOfSend) && Objects.equals(text, that.text) && Objects.equals(read_msg, that.read_msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeOfSend, text, read_msg);
    }


}
