package com.hanker.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;

import com.hanker.model.EmailMessageBean;
import com.hanker.model.folder.EmailFolderBean;

public class ModelAccess {
	
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
}
