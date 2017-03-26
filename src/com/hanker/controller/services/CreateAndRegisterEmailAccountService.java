package com.hanker.controller.services;

import com.hanker.controller.ModelAccess;
import com.hanker.model.EmailAccountBean;
import com.hanker.model.EmailConstants;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;

/*
 * This task do the several things asynchronously for you:
 * 		1. connecting to the server to authenticate your email account
 * 		2. If authenticated successfully, create a new email-address root node
 * 		   in the emailTreeView
 * 		3. Create and start FetchFolderService.
 * 
 * That is, to bind the e-mail account with the root folder of each email account
 */
public class CreateAndRegisterEmailAccountService extends Service<EmailAccountBean>{
	
	private String emailAddress;
	private String password;
	TreeItem<String> root;
	private ModelAccess modelAccess;
	
	
	public CreateAndRegisterEmailAccountService(
			String emailAddress, String password, ModelAccess ma) {
		this.emailAddress = emailAddress;
		this.password = password;
		this.modelAccess = ma;
		
		this.setOnSucceeded(e -> {
			EmailAccountBean thisAccount = this.getValue();
			if (thisAccount.getLoginState().equals(EmailConstants.LOGIN_STATE_SUCCESS)){
				modelAccess.addActiveAccount(thisAccount);
			}
		});
	}


	@Override
	protected Task<EmailAccountBean> createTask() {
		return new Task<EmailAccountBean>(){
			@Override
			protected EmailAccountBean call() throws Exception {
				Thread.currentThread().setName("CreateAndRegisterEmailAccountService");
				// connecting to server and authenticate the account
				EmailAccountBean emailAccount = new EmailAccountBean(emailAddress, password);
				return emailAccount;
			}
			
		};
	}

}
