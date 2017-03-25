package com.hanker.model;

import javax.mail.Message;

import com.hanker.model.table.AbstractTableItem;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmailMessageBean extends AbstractTableItem {
	private SimpleStringProperty subject;
	private SimpleStringProperty sender;
	private SimpleObjectProperty<SizeObject> size;
	private Message messageRef;
	
	public EmailMessageBean(String subject, String sender, int size, Message message, boolean isRead){
		super(isRead);
		this.subject = new SimpleStringProperty(subject);
		this.sender = new SimpleStringProperty(sender);
		this.size = new SimpleObjectProperty<SizeObject>(new SizeObject(size));
		this.messageRef = message;
	}
	
	public String getSender(){
		return sender.get();
	}
	public String getSubject(){
		return subject.get();
	}
	public SizeObject getSize(){
		return size.get();
	}
	public Message getMessageRef(){
		return messageRef;
	}

	@Override
	public String toString() {
		return "EmailMessageBean [subject=" + subject.getValue() + ", sender=" + sender.getValue() + ", size=" + size.getValue() + "]";
	}

}
