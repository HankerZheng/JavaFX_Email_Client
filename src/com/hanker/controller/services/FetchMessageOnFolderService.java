package com.hanker.controller.services;

import javax.mail.Folder;
import javax.mail.Message;

import com.hanker.model.folder.EmailFolderBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;


/*
 * This task do the several things asynchronously for you:
 * 		Given the folder, add all the e-mails in this folder into
 * 		the data field of this emailFolderBean.
 * 
 * That is, to bind the emailMessageBean with the emailFolderBean.
 */
public class FetchMessageOnFolderService extends Service<Void>{
	
	private Folder folder;
	private EmailFolderBean<String> emailFolderBean;
	
	public FetchMessageOnFolderService(Folder folder, EmailFolderBean<String> emailFolderBean) {
		this.folder = folder;
		this.emailFolderBean = emailFolderBean;
	}

	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				Thread.currentThread().setName("FetchMessageOnFolderService-"+ folder.getName());
				if (folder.getType() != Folder.HOLDS_FOLDERS){
					folder.open(Folder.READ_WRITE);
				}
				int folderSize = folder.getMessageCount();
				for (int i = folderSize; i > 0; i--){
					Message currentMessage = folder.getMessage(i);
					emailFolderBean.addEmail(-1, currentMessage);
				}
				return null;
			}
			
		};
	}
	
	

}
