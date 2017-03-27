package com.hanker.controller.services;

import java.util.List;

import com.hanker.controller.ModelAccess;
import com.hanker.model.EmailAccountBean;
import com.hanker.model.folder.EmailFolderBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;

public class AccountUpdateService extends Service<Void>{
	
	ModelAccess modelAccess;
	TreeItem<String> root;
	int loadedCount = 0;
	
	public AccountUpdateService(ModelAccess ma, TreeItem<String> root){
		this.modelAccess = ma;
		this.root = root;			
	}

	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				for(;;){
					List<EmailAccountBean> accounts = modelAccess.getActiveAccounts();
					System.out.println("New Account Detected!");
					synchronized(accounts){
						for (int i = loadedCount; i < accounts.size(); i ++){
							EmailAccountBean thisAccount = modelAccess.getActiveAccounts().get(i);
							EmailFolderBean<String> accountFolder = new EmailFolderBean<>(thisAccount.getEmailAddress());
							root.getChildren().add(accountFolder);
							FetchFolderService FetchFolderService = new FetchFolderService(
									accountFolder, 
									thisAccount, 
									modelAccess
							);
							FetchFolderService.start();
							loadedCount ++;
						}
						accounts.wait();
					}
				}
			}
			
		};
	}

}
