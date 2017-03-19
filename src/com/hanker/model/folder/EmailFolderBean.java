package com.hanker.model.folder;

import com.hanker.model.EmailMessageBean;
import com.hanker.view.ViewFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class EmailFolderBean<T> extends TreeItem<String> {
	
	private boolean topElement = false;
	private int unreadMessageCount;
	private String name;
	@SuppressWarnings("unused")
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
	
	public void addEmail(EmailMessageBean message){
		data.add(message);
		if (!message.isRead()){
			incrementUnreadMessageCount(1);
		}
	}
	
	public boolean isTopElement(){
		return topElement;
	}
	
	public ObservableList<EmailMessageBean> getData(){
		return data;
	}
	
	
}

