package com.hanker.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SampleData {
	
    public static final ObservableList<EmailMessageBean> Inbox = FXCollections.observableArrayList(
    		new EmailMessageBean("Hello from Sefu!!!","aaa@yahoo.com", 5500000, "<html>What is Lorem Ipsum?Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</html>", false),
    		new EmailMessageBean("Hello from Barosanu","bbb@yahoo.com", 200, "<html>Hello from Barosanu<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("Hello from Sefu!!!asdas","ccc@yahoo.com", 10, "<html>Hello from Sefu!!!asdas<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("Hello from Barosanuasdasas","ddd@yahoo.com", 6300, "<html>Hello from Barosanuasdasas<h3>lorem ipsum + <br> </h3></html>", true)    		
    		);
    public static final ObservableList<EmailMessageBean> Sent = FXCollections.observableArrayList(
    		new EmailMessageBean("Hi! long time, no see","example@yahoo.com", 22, "<html>Hi! long time, no see<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("Hi! Check this!!","example@yahoo.com", 200, "<html>You are the best<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("Where are my money???","example@yahoo.com", 10, "<html>You ask me???<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("Escape plans","example@yahoo.com",6300, "<html>Basement and then tun to the left<h3>lorem ipsum + <br> </h3></html>", true)    		
    		);
    public static final ObservableList<EmailMessageBean> Spam = FXCollections.observableArrayList(
    		new EmailMessageBean("You won't belive this: click and see!!!","serious@company.com", 22000000, "<html>You are smart<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("Like and share if you care!!","book@face.com", 200, "<html>Your likes make a huge difference!!!<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("You just won 2 cents! Click to claim!","ss@bail.com", 10, "<html>Give us first 3 cents!<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("You got a virus!","safe@brousing.com",6300, "<html>Formatting computer.....<h3>lorem ipsum + <br> </h3></html>", true),   
    		new EmailMessageBean("You won't belive this: click and see!!!","serious@company.com", 22000000, "<html>You are smart<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("Like and share if you care!!","book@face.com", 200, "<html>Your likes make a huge difference!!!<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("You just won 2 cents! Click to claim!","ss@bail.com", 10, "<html>Give us first 3 cents!<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("You got a virus!","safe@brousing.com",6300, "<html>Formatting computer.....<h3>lorem ipsum + <br> </h3></html>", true),     
    		new EmailMessageBean("You won't belive this: click and see!!!","serious@company.com", 22000000, "<html>You are smart<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("Like and share if you care!!","book@face.com", 200, "<html>Your likes make a huge difference!!!<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("You just won 2 cents! Click to claim!","ss@bail.com", 10, "<html>Give us first 3 cents!<h3>lorem ipsum + <br> </h3></html>", true),
    		new EmailMessageBean("You got a virus!","safe@brousing.com",6300, "<html>Formatting computer.....<h3>lorem ipsum + <br> </h3></html>", true)   
    		);
}
