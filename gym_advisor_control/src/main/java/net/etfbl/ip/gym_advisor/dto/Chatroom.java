package net.etfbl.ip.gym_advisor.dto;

import java.io.Serializable;

public class Chatroom implements Serializable{
	
	private int id;
	private String timeOfSend;
	private String text;
	private Boolean readMsg;
	private Integer receiverId;
	private Integer senderId;


	public Chatroom() {}
	
	public Chatroom(int id, String timeOfSend, String text, Boolean readMsg, Integer receiverId, Integer senderId) {
		super();
		this.id = id;
		this.timeOfSend = timeOfSend;
		this.text = text;
		this.readMsg = readMsg;
		this.receiverId = receiverId;
		this.senderId = senderId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTimeOfSend() {
		return timeOfSend;
	}
	public void setTimeOfSend(String timeOfSend) {
		this.timeOfSend = timeOfSend;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getReadMsg() {
		return readMsg;
	}
	public void setReadMsg(Boolean readMsg) {
		this.readMsg = readMsg;
	}
	public Integer getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}
	public Integer getSenderId() {
		return senderId;
	}
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}
	
	@Override
	public String toString() {
		return "Chatroom [id=" + id + ", timeOfSend=" + timeOfSend + ", text=" + text + ", readMsg=" + readMsg
				+ ", receiverId=" + receiverId + ", senderId=" + senderId + "]";
	}

}
