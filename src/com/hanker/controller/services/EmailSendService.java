package com.hanker.controller.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.hanker.model.EmailAccountBean;
import com.hanker.model.EmailConstants;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/*
 * An email consists of several parts
 * 		1. Subject
 * 		2. From-address
 * 		3. Several to-addresses
 * 		4. The content
 * 			1) main content "text/html"
 * 			2) attachments
 */
public class EmailSendService extends Service<EmailConstants>{
	
	private EmailConstants result;
	private EmailAccountBean emailAccountBean;
	private String subject;
	private String[] recipiens;
	private String content;
	private List<File> attachments = new ArrayList<File>();

	public EmailSendService(EmailAccountBean emailAccountBean, String subject, String recipien,
			String content, List<File> attachments) {
		this.emailAccountBean = emailAccountBean;
		this.subject = subject;
		this.recipiens = recipien.split(";");
		this.content = content;
		this.attachments = attachments;
	}

	@Override
	protected Task<EmailConstants> createTask() {
		return new Task<EmailConstants>(){

			@Override
			protected EmailConstants call() throws Exception {
				try {
					// Setup:
					Session session = emailAccountBean.getSession();
					MimeMessage message = new MimeMessage(session);
					message.setFrom(emailAccountBean.getEmailAddress());
					message.setSubject(subject);
					for(String recipien: recipiens){
						message.addRecipients(Message.RecipientType.TO, recipien.trim());						
					}
					// Setting the content
					Multipart mp = new MimeMultipart();
					BodyPart messageBP = new MimeBodyPart();
					messageBP.setContent(content, "text/html");
					mp.addBodyPart(messageBP);
					for (File file: attachments){
						BodyPart thisBP = new MimeBodyPart();
						DataSource source = new FileDataSource(file.getAbsolutePath());
						thisBP.setDataHandler(new DataHandler(source));
						thisBP.setFileName(file.getName());
						mp.addBodyPart(thisBP);
					}
					message.setContent(mp);
					// Sending the message
					Transport transport = session.getTransport();
					transport.connect(
							emailAccountBean.getProperties().getProperty("outgoinghost"),
							emailAccountBean.getEmailAddress(), 
							emailAccountBean.getPassword()
					);
					transport.sendMessage(message, message.getAllRecipients());
					transport.close();
					
					result = EmailConstants.MESSAGE_SENT_OK;
					
					
				} catch (Exception e) {
					result = EmailConstants.MESSAGE_SENT_ERROR;
				}
				return result;
			}
			
		};
	}
	
	public void addAttachments(List<File> attachments){
		this.attachments.addAll(attachments);
	}

}
