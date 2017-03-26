package com.hanker.model;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;


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
	
	public String getPassword(){
		return password;
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
	
	public String toString(){
		return emailAddress;
	}

}
