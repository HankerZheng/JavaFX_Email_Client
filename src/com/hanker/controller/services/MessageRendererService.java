package com.hanker.controller.services;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;

import com.hanker.model.EmailMessageBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;


/*
 * Create this service mainly because `getContent()` and `getContentType()` methods
 * are I/O operations and these methods should be called in different threads.
 * 
 * In this service, we want display the content of one e-mail. If we just call
 * `messageRendererEngine.loadContent(sb.toString())` in the `call` method, it
 * would cause a `java.lang.IllegalStateException: Not on FX application thread`.
 * It implies that a non-FXApplication thread tries to modify the GUI.
 * 
 * Note: All changes of the GUI should be done by the `FX Application Thread`
 */
public class MessageRendererService extends Service<Void>{
	
	private EmailMessageBean messageBean;
	private WebEngine messageRendererEngine;
	private StringBuffer sb;
	
	public MessageRendererService(WebEngine messageRendererEngine) {
		this.messageRendererEngine = messageRendererEngine;
		this.sb = new StringBuffer();
	}
	
	public void setMessageBean(EmailMessageBean messageBean){
		this.messageBean = messageBean;
		// currentThread is FX Application Thread
		this.setOnSucceeded(e -> {
			messageRendererEngine.loadContent(sb.toString());
		});
	}
	
	/*
	 * Store the text content in the `sb`
	 */
	private void renderMessage(){
		// clear the string buffer
		sb.setLength(0);
		messageBean.clearAttachments();
		// store the content of the message in `sb`
		Message message = messageBean.getMessageRef();
		parseDataFromMessage(message, sb);
	}
	
	private void parseDataFromMessage(Part message, StringBuffer sb){
		try {
			String contentType = message.getContentType().toLowerCase();
			System.out.println(contentType);
			if (contentType.contains("text/html")){
				if (sb.length() == 0){
					sb.append(message.getContent().toString());
				}
			} else if (contentType.contains("application") ||
					contentType.contains("name=")){
				MimeBodyPart mbp = (MimeBodyPart) message;
				messageBean.addAttachment(mbp);
			} else if (contentType.contains("multipart")){
				Multipart mp = (Multipart) message.getContent();
				for (int i = mp.getCount() - 1; i >= 0; i--){
					BodyPart bp = mp.getBodyPart(i);
					parseDataFromMessage(bp, sb);
				}
			}
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		} catch ( Exception e){
			e.printStackTrace();			
		}
		
	}


	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				// TODO Auto-generated method stub
				Thread.currentThread().setName("MessageRendererService-"+messageBean.getSubject());
				renderMessage();
				return null;
			}
			
		};
	}

}
