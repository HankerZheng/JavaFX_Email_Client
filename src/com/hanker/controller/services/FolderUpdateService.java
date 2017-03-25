package com.hanker.controller.services;

import java.util.List;

import javax.mail.Folder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FolderUpdateService extends Service<Void> {

	private List<Folder> folderList;
	
	public FolderUpdateService(List<Folder> folderList){
		this.folderList = folderList;
	}
	
	@Override
	protected Task<Void> createTask() {
		return new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				for(;;){
					try{
						Thread.sleep(10000);
						if (!FetchFolderService.noServicesActive()){
							System.out.println("Active Folder Fetch Service detected!");
							continue;
						}
						System.out.println("Check all the folders!");
						for (Folder folder: folderList){
							if(folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen()){
								folder.getMessageCount();
							}
						}
					} catch (Exception e){
						e.printStackTrace();
					}
				}	
			}
			
		};
	}
	

}
