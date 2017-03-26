package com.hanker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Configuration {
	
	private List<HashMap<String, String>> accounts = new ArrayList<>();
	private HashMap<String, String> commonProtocol = new HashMap<>();
	private HashMap<String, HashMap<String, String>> mailProtocol = new HashMap<>();
	
	private static Configuration defaultConfig = new Configuration();
	
	private Configuration(){
		HashMap<String, String> a0 = new HashMap<>();
			a0.put("username", "hanker.test@gmail.com");
			a0.put("password", "testzhengPassword");
		HashMap<String, String> a1 = new HashMap<>();
			a1.put("username", "hanker.test0@gmail.com");
			a1.put("password", "testzhengPassword");
		accounts.add(a0);
		accounts.add(a1);
		
		commonProtocol.put("mail.store.protocol",  "imaps");
		commonProtocol.put("mail.transport.protocol",  "smtp");
		commonProtocol.put("mail.smtps.auth", "true");
		
		HashMap<String, String> gmail = new HashMap<>();
			gmail.put("mail.smtps.host", "smtp.gmail.com");
			gmail.put("incomingHost", "imap.gmail.com");
			gmail.put("outgoingHost", "smtp.gmail.com");
		HashMap<String, String> m163 =  new HashMap<>();
			m163.put("mail.smtps.host", "smtp.163.com");
			m163.put("incomingHost", "imap.163.com");
			m163.put("outgoingHost", "smtp.163.com");
		mailProtocol.put("gmail", gmail);
		mailProtocol.put("163", m163);
	}
	
	public static Configuration getInstance(){
		return defaultConfig;
	}
	
	public List<HashMap<String, String>> getAccountInfo(){
		return accounts;
	}

}
