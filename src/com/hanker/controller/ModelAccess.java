package com.hanker.controller;

import com.hanker.model.EmailMessageBean;
import com.hanker.model.folder.EmailFolderBean;

public class ModelAccess {
	
	private EmailMessageBean selectedMessage;
	
	private EmailFolderBean<String> selectedFolder;
	
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
}
