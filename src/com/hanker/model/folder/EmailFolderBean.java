package com.hanker.model.folder;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Flags.Flag;

import com.hanker.model.EmailMessageBean;
import com.hanker.view.ViewFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
/*
 * Mapping from TreeItem to EmailMessageBean
 */

public class EmailFolderBean<T> extends TreeItem<String> {
	
	private boolean topElement = false;
	private int unreadMessageCount;
	private String name;
	private String completeName;
	private ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();

	/**
	 * Constructor for the top elements
	 * @param name
	 */
	public EmailFolderBean(String name){
		super(name, ViewFactory.defaultViewFactory.resolveIcon(name));
		this.name = name;
		this.completeName = name;
		topElement = true;
		data = null;
		this.setExpanded(true);
	}

	public EmailFolderBean(String name, String completeName){
		super(name, ViewFactory.defaultViewFactory.resolveIcon(name));
		this.name = name;
		this.completeName = completeName;
	}
	
	private void updateValue(){
		if (unreadMessageCount > 0){
			this.setValue((String) (name + "(" + unreadMessageCount + ")"));
		}else{
			this.setValue(name);
		}
	}
	
	public void incrementUnreadMessageCount(int newMessages){
		unreadMessageCount += newMessages;
		updateValue();
	}
	
	public void decrementUnreadMessageCount(){
		unreadMessageCount -= 1;
		updateValue();
	}
	
	public void addEmail(int pos, Message message) throws MessagingException{
		boolean isRead = message.getFlags().contains(Flag.SEEN);
		EmailMessageBean emailMessageBean = new EmailMessageBean(
				message.getSubject(), 
				message.getFrom()[0].toString(), 
				message.getSize(),
				message.getSentDate(),
				message, 
				message.getFlags().contains(Flag.SEEN));
		if (pos < 0){
			data.add(emailMessageBean);
		}else{
			data.add(pos, emailMessageBean);
		}
		if (!isRead){
			incrementUnreadMessageCount(1);
		}
	}
	
	public String getCompleteName(){
		return completeName;
	}
	
	public boolean isTopElement(){
		return topElement;
	}
	
	public ObservableList<EmailMessageBean> getData(){
		return data;
	}
	
	
}

