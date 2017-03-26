package com.hanker.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.hanker.controller.services.EmailSendService;
import com.hanker.model.EmailAccountBean;
import com.hanker.model.EmailConstants;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

public class ComposeController extends AbstractController implements Initializable{
	
	private List<File> attachments = new ArrayList<File>();

	@FXML
    private Button addAttachBtn;

    @FXML
    private Label attachmentLabel;

    @FXML
    private ChoiceBox<EmailAccountBean> fromEmail;

    @FXML
    private TextField toEmail;

    @FXML
    private TextField subject;

    @FXML
    private HTMLEditor contentEditor;

    @FXML
    private Label errorLabel;

    @FXML
    private Button sendBtn;

    @FXML
    void addAttachAction(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	File selectedFile = fileChooser.showOpenDialog(null);
    	if (selectedFile != null){
    		attachments.add(selectedFile);
    		attachmentLabel.setText(attachmentLabel.getText() + selectedFile.getName() + "; ");    		
    	}
    }

    @FXML
    void sendAction(ActionEvent event) {
    	errorLabel.setText("");
    	EmailSendService emailSendService = new EmailSendService(
    			fromEmail.getValue(),
    			subject.getText(),
    			toEmail.getText(),
    			contentEditor.getHtmlText(),
    			attachments);
    	emailSendService.restart();
    	emailSendService.setOnSucceeded(e -> {
    		if (emailSendService.getValue() == EmailConstants.MESSAGE_SENT_OK){
    			errorLabel.setText("Messsage sent successfully!!");
    		}else{
    			errorLabel.setText("Message Sending Error!!");
    		}
    	});
    }
    
    

    public ComposeController(ModelAccess modelAccess) {
		super(modelAccess);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Add possible item from active account
		fromEmail.setItems(getModelAccess().getActiveAccounts());
		fromEmail.setValue(getModelAccess().getActiveAccounts().get(0));
	}
}
