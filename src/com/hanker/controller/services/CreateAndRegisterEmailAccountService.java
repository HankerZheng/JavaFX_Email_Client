package com.hanker.controller.services;

import com.hanker.model.EmailAccountBean;
import com.hanker.model.EmailConstants;
import com.hanker.model.folder.EmailFolderBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/*
 * This task do the several things asynchronously for you:
 * 		1. connecting to the server to authenticate your email account
 * 		2. If authenticated successfully, create a new email-address root node
 * 		   in the emailTreeView
 * 		3. Create and start FetchFolderService.
 * 
 * That is, to bind the e-mail account with the root folder of each email account
 */
public class CreateAndRegisterEmailAccountService extends Service<EmailConstants>{
	
	private String emailAddress;
	private String password;
	private EmailFolderBean<String> folderRoot;
	
	
	public CreateAndRegisterEmailAccountService(
			String emailAddress, String password, EmailFolderBean<String> folderRoot) {
		this.emailAddress = emailAddress;
		this.password = password;
		this.folderRoot = folderRoot;
	}


	@Override
	protected Task<EmailConstants> createTask() {
		return new Task<EmailConstants>(){
			@Override
			protected EmailConstants call() throws Exception {
				// connecting to server and authenticate the account
				EmailAccountBean emailAccount = new EmailAccountBean(emailAddress, password);
				if (emailAccount.getLoginState() == EmailConstants.LOGIN_STATE_SUCCESS){
					// Create new EmailFolderBean and put it into folderRoot
					EmailFolderBean<String> emailFolderBean = new EmailFolderBean<>(emailAddress);
					folderRoot.getChildren().add(emailFolderBean);
					// Create and start FetchFolderService
					FetchFolderService fetchFolderService = new FetchFolderService(emailFolderBean, emailAccount);
					fetchFolderService.start();
				}
				return emailAccount.getLoginState();
			}
			
		};
	}

}
