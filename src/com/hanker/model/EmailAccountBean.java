package com.hanker.model;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import javafx.collections.ObservableList;

public class EmailAccountBean {
	
	private String emailAddress;
	private String password;
	private Properties properties;
	
	private Store store;
	private Session session;
	private EmailConstants loginState = EmailConstants.LOGIN_STATE_NOT_READY;
	
	public String getEmailAddress() {
		return emailAddress;
	}

	public Properties getProperties() {
		return properties;
	}

	public Store getStore() {
		return store;
	}

	public Session getSession() {
		return session;
	}

	public EmailConstants getLoginState() {
		return loginState;
	}

	public EmailAccountBean(String emailAddress, String password){
		this.emailAddress = emailAddress;
		this.password = password;
		// Setting the protocol of the email connection
		properties = new Properties();
		properties.put("mail.store.protocol", "imaps");
		properties.put("mail.transport.protocol" , "smtps");
		properties.put("mail.smtps.host", "smtp.gmail.com");
		properties.put("mail.smtps.auth", "true");
		properties.put("incomingHost", "imap.gmail.com");
		properties.put("outgoingHost", "smtp.gmail.com");
		// Setting account information 
		Authenticator auth = new Authenticator(){
			@Override
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(emailAddress, password);
			}
		};
		// Connecting to the sever, connected information is in `store`
		session = Session.getInstance(properties, auth);
		try{
			this.store = session.getStore();
			System.out.println("Preparation Done!");
			store.connect(properties.getProperty("incomingHost"), emailAddress, password);
			System.out.println("EmailAccountBean constructed successfully!!!");
			loginState = EmailConstants.LOGIN_STATE_SUCCESS;
		} catch (Exception e){
			e.printStackTrace();
			loginState = EmailConstants.LOGIN_STATE_FAILED_BY_CREDENTIALS;
		}
	}
//	
//	/**
//	 * Construct {@code EmailMessageBean} instances and add the instances
//	 * into the data, that is where the message should be presented.
//	 * @param data: the place to display the message
//	 */
//	public void addEmailsToData(ObservableList<EmailMessageBean> data, String folderName){
//		try {
//			Folder folder = store.getFolder(folderName);
//			folder.open(Folder.READ_ONLY);
//			for(int i = 1 ; i < folder.getMessageCount(); i ++){
//				Message message = folder.getMessage(i);
//				EmailMessageBean messageBean = new EmailMessageBean(
//						message.getSubject(),
//						message.getFrom()[0].toString(), 
//						message.getSize(),
//						"",
//						message.getFlags().contains(Flag.SEEN));
//				data.add(messageBean);
//				System.out.println("Got: " + messageBean);
//			}
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	

}
