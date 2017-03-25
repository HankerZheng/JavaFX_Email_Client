package com.hanker.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hanker.controller.services.MessageRendererService;
import com.hanker.model.EmailMessageBean;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

public class EmailDetailsController extends AbstractController{

    public EmailDetailsController(ModelAccess modelAccess) {
		super(modelAccess);
	}

	@FXML
    private Label sender;

    @FXML
    private Label subject;

    @FXML
    private WebView emailDetails;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("EmailDetailsController initialized!");
		
		EmailMessageBean messageBean = getModelAccess().getSelectedMessage();
		subject.setText("Subject: " + messageBean.getSubject());
		sender.setText("Sender: " + messageBean.getSender());
		MessageRendererService msr = new MessageRendererService(emailDetails.getEngine());
		msr.setMessageBean(messageBean);
		msr.restart();
	}

}
