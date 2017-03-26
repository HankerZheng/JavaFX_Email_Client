package com.hanker.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

import com.hanker.model.table.AbstractTableItem;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmailMessageBean extends AbstractTableItem {
	
	private SimpleStringProperty subject;
	private SimpleStringProperty sender;
	private SimpleObjectProperty<SizeObject> size;
	private SimpleObjectProperty<Date> date;
	
	private Message messageRef;
	
	// Attachment Handling
	private List<MimeBodyPart> attachmentList = new ArrayList<>();
	private StringBuffer attachmentNames = new StringBuffer();
	
	public EmailMessageBean(String subject, String sender, int size, Date date, Message message, boolean isRead){
		super(isRead);
		this.subject = new SimpleStringProperty(subject);
		this.sender = new SimpleStringProperty(sender);
		this.size = new SimpleObjectProperty<SizeObject>(new SizeObject(size));
		this.date = new SimpleObjectProperty<Date>(date);
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
	
	public Date getDate(){
		return date.get();
	}


	public List<MimeBodyPart> getAttachmentList() {
		return attachmentList;
	}
	public String getAttachmentNames() {
		return attachmentNames.toString();
	}
	
	public void addAttachment(MimeBodyPart mbp){
		attachmentList.add(mbp);
		try {
			attachmentNames.append(mbp.getFileName() + ";");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	public boolean hasAttachments(){
		return attachmentList.size() > 0;
	}
	
	public void clearAttachments(){
		this.attachmentList.clear();
		this.attachmentNames.setLength(0);
	}

	@Override
	public String toString() {
		return "EmailMessageBean [subject=" + subject.getValue() + ", sender=" + sender.getValue() + ", size=" + size.getValue() + "]";
	}
}
