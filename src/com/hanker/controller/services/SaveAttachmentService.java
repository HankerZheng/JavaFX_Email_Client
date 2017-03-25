package com.hanker.controller.services;

import javax.mail.internet.MimeBodyPart;

import com.hanker.model.EmailMessageBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class SaveAttachmentService extends Service<Void>{
	
	private static String DOWNLOAD_DEST = System.getProperty("user.home") + "/Downloads/Email_Client/";
	
	private Label downloadLabel;
	private ProgressBar downloadProgress;
	private EmailMessageBean messageBean;

	public SaveAttachmentService(ProgressBar downloadProgress, Label downloadLabel) {
		this.downloadLabel = downloadLabel;
		this.downloadProgress = downloadProgress;
		downloadProgress.progressProperty().bind(this.progressProperty());
		this.setOnRunning(e -> {
			showVisuals(true);
		});
		this.setOnSucceeded(e -> {
			showVisuals(false);
		});
	}
	
	public void setMessageBean(EmailMessageBean m){
		this.messageBean = m;
	}
	
	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				if (messageBean != null && messageBean.hasAttachments()){
					int totalProgress = messageBean.getAttachmentList().size();
					int curProgress = 0;
					updateProgress(curProgress, totalProgress);
					for(MimeBodyPart mbp:messageBean.getAttachmentList()){
						System.out.println("Start downloading file " + mbp.getFileName());
						mbp.saveFile(DOWNLOAD_DEST + mbp.getFileName());
						updateProgress(curProgress, totalProgress);
						curProgress ++;
					}
				}
				return null;
			}
			
		};
	}
	
	public void showVisuals(boolean show){
		downloadLabel.setVisible(show);
		downloadProgress.setVisible(show);
	}

}
