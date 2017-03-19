package com.hanker;

import com.hanker.model.EmailAccountBean;
import com.hanker.model.EmailMessageBean;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Test {
	
	public static void main(String[] args) {		
		EmailAccountBean emailAccountBean = new EmailAccountBean(
				"hanker.test@gmail.com", "testzhengPassword");
		ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();
		emailAccountBean.addEmailsToData(data);
	}

}
