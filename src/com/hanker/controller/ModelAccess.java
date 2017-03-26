package com.hanker.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;

import com.hanker.model.EmailAccountBean;
import com.hanker.model.EmailMessageBean;
import com.hanker.model.folder.EmailFolderBean;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelAccess {
	
	private ObservableList<EmailAccountBean> activeAccounts = FXCollections.observableArrayList();
	private EmailMessageBean selectedMessage;	
	private EmailFolderBean<String> selectedFolder;
	
	private List<Folder> folderList;
	
	public ModelAccess(){
		folderList = new ArrayList<Folder>();
	}
	
	public EmailMessageBean getSelectedMessage(){
		return selectedMessage;
	}
	
	public void setSelectedMessage(EmailMessageBean message){
		selectedMessage = message;
	}

	public EmailFolderBean<String> getSelectedFolder() {
		return selectedFolder;
	}

	public void setSelectedFolder(EmailFolderBean<String> selectedFolder) {
		this.selectedFolder = selectedFolder;
	}
	
	public void addFolder(Folder folder){
		folderList.add(folder);
	}
	
	public List<Folder> getFolderList(){
		return folderList;
	}
	
	public ObservableList<EmailAccountBean> getActiveAccounts(){
		return activeAccounts;
	}
	
	public void addActiveAccount(EmailAccountBean account){
		synchronized(activeAccounts){
			activeAccounts.add(account);
			activeAccounts.notifyAll();
		}
	}
}
