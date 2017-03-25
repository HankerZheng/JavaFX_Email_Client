package com.hanker.controller.services;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;

import com.hanker.controller.ModelAccess;
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
 * That is to bind the email folder with the emailFolderBean.
 * In this Service, we only add EmailFolderBean instances into the List.
 * That is, we do not modify the GUI content. The change of the ItemList 
 * of the `TreeView` elements would deliver an event that cause the `FXThread`
 * to update its VIEW.
 */
public class FetchFolderService extends Service<Void> {
	
	private static int FETCH_FOLDER_SERVICE_COUNT = 0;
	private EmailFolderBean<String> foldersRoot;
	private EmailAccountBean emailAccount;
	private ModelAccess modelAccess;

	public FetchFolderService(EmailFolderBean<String> foldersRoot, EmailAccountBean emailAccount, ModelAccess ma) {
		this.foldersRoot = foldersRoot;
		this.emailAccount = emailAccount;
		this.modelAccess = ma;
		
		this.setOnSucceeded(e -> {
			FETCH_FOLDER_SERVICE_COUNT --;
		});
	}

	@Override
	protected Task<Void> createTask() {
		FETCH_FOLDER_SERVICE_COUNT ++;
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
		// Add this folder to ModelAccess
		modelAccess.addFolder(folder);
		// Create corresponding emailFolderBean and append it into the treeView
		EmailFolderBean<String> thisFolderBean = new EmailFolderBean<String>(folder.getName(), folder.getFullName());
		thisRoot.getChildren().add(thisFolderBean);
		thisFolderBean.setExpanded(true);
//		System.out.println("Added: " + folder.getFullName() + " under " + thisRoot.getValue());
		addMessageListenerToFolder(folder, thisFolderBean);
		// Fetch the message in this folder
		FetchMessageOnFolderService fs = new FetchMessageOnFolderService(folder, thisFolderBean);
		fs.start();
		Folder[] subFolders = folder.list();
		for (Folder subFolder: subFolders){
			fetchFolderData(subFolder, thisFolderBean);
		}		
	}
	
	/*
	 * addMessageCountListener to folder
	 * Any time when messageCount of that folder increases, the method `messagesAdded`
	 * would be called and add new emailMessageBean to current folder.
	 * 
	 * However, the change of the messageCount would be called explicitly.
	 * That is, we need to set up a service to check the message count periodically.
	 */
	private void addMessageListenerToFolder(Folder folder, EmailFolderBean<String> item){
		folder.addMessageCountListener(new MessageCountAdapter(){
			@Override
			public void messagesAdded(MessageCountEvent e){
				for(int i = 0; i < e.getMessages().length; i++){
					try {
						Message currentMessage = folder.getMessage(folder.getMessageCount() - i);
						item.addEmail(0, currentMessage);
					} catch (MessagingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	public static boolean noServicesActive(){
		return FETCH_FOLDER_SERVICE_COUNT == 0;
	}

}
