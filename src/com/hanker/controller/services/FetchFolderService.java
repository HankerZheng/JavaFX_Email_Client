package com.hanker.controller.services;

import javax.mail.Folder;
import javax.mail.MessagingException;

import com.hanker.model.EmailAccountBean;
import com.hanker.model.folder.EmailFolderBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/*
 * This task do the several things asynchronously for you:
 * 		1. Fetch all the folders in the given e-mail account
 * 		2. For each folder, also search its sub-folder
 * 		3. For each folder, create and start FetchMessageOnFolderService
 * 
 * That is to bind the email folder with the emailFolderBean
 */
public class FetchFolderService extends Service<Void> {
	
	private EmailFolderBean<String> foldersRoot;
	private EmailAccountBean emailAccount;
	

	public FetchFolderService(EmailFolderBean<String> foldersRoot, EmailAccountBean emailAccount) {
		this.foldersRoot = foldersRoot;
		this.emailAccount = emailAccount;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>(){
			@Override
			protected Void call() throws Exception {
				if (emailAccount != null){
					Folder[] folders = emailAccount.getStore().getDefaultFolder().list();
					for (Folder folder: folders){
						fetchFolderData(folder, foldersRoot);
					}
				}
				return null;
			}
			
		};
	}
	
	private void fetchFolderData(Folder folder, EmailFolderBean<String> thisRoot) throws MessagingException{
		EmailFolderBean<String> thisFolderBean = new EmailFolderBean<String>(folder.getName(), folder.getFullName());
		thisRoot.getChildren().add(thisFolderBean);
//		System.out.println("Added: " + folder.getFullName() + " under " + thisRoot.getValue());
		thisFolderBean.setExpanded(true);
		FetchMessageOnFolderService fs = new FetchMessageOnFolderService(folder, thisFolderBean);
		fs.start();
		Folder[] subFolders = folder.list();
		for (Folder subFolder: subFolders){
			fetchFolderData(subFolder, thisFolderBean);
		}		
	}
	

}
