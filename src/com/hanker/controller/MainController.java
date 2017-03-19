package com.hanker.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hanker.model.EmailMessageBean;
import com.hanker.model.SampleData;
import com.hanker.model.SizeObject;
import com.hanker.model.folder.EmailFolderBean;
import com.hanker.model.table.BoldableRowFactory;
import com.hanker.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MainController extends AbstractController{

	@FXML
    private TreeView<String> emailTreeView;

    @FXML
    private TableView<EmailMessageBean> emailTableView;

    @FXML
    private TableColumn<EmailMessageBean, String> senderCol;

    @FXML
    private TableColumn<EmailMessageBean, String> subjectCol;

    @FXML
    private TableColumn<EmailMessageBean, SizeObject> sizeCol;
    
    @FXML
    private WebView emailContentView;
    
    private MenuItem showDetails = new MenuItem("show details");
    
    
    @FXML
    void btnClicked(ActionEvent event) {
    	System.out.println("Button Clicked!!");
    }
    
    @FXML
    void changeReadProperty(ActionEvent event) {
    	EmailMessageBean message =  getModelAccess().getSelectedMessage();
    	if (message != null){
	    	boolean isRead = message.isRead();
	    	message.setRead(!isRead);
	    	System.out.println(message.isRead());
	    	EmailFolderBean<String> selectedFolder = getModelAccess().getSelectedFolder();
	    	if (selectedFolder != null){
	    		if (!message.isRead()){
	    			selectedFolder.incrementUnreadMessageCount(1);
	    		}else{
	    			selectedFolder.decrementUnreadMessageCount();
	    		}
	    	}
    	}
    }
    
    public MainController(ModelAccess modelAccess) {
		super(modelAccess);
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ViewFactory viewFactory = ViewFactory.defaultViewFactory;
		emailTableView.setRowFactory(e -> new BoldableRowFactory<>());
		
		// Bind the column with the field of type `EmailMessageBean`
		senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
		subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, SizeObject>("size"));
		
		// Initialize EmailTreeView
		EmailFolderBean<String> root = new EmailFolderBean<>("");
		emailTreeView.setRoot(root);
		emailTreeView.setShowRoot(false);
		
		EmailFolderBean<String> account1 = new EmailFolderBean<>("example@gmail.com");
		root.getChildren().add(account1);
		EmailFolderBean<String> inbox = new EmailFolderBean<>("Inbox", "CompleteInbox");
		EmailFolderBean<String> sent = new EmailFolderBean<>("Sent", "CompleteSend");
			EmailFolderBean<String> sub1 = new EmailFolderBean<>("Subfolder1", "CompleteSubfolder1");
			EmailFolderBean<String> sub2 = new EmailFolderBean<>("Subfolder2", "CompleteSubfolder2");
			sent.getChildren().addAll(sub1, sub2);
		EmailFolderBean<String> spam = new EmailFolderBean<>("Spam", "CompleteSpam");
		account1.getChildren().addAll(inbox, sent, spam);
		inbox.getData().addAll(SampleData.Inbox);
		sent.getData().addAll(SampleData.Sent);
		spam.getData().addAll(SampleData.Spam);		
		

		// Initialize EmailTableView's right clicked menu
		emailTableView.setContextMenu(new ContextMenu(showDetails));
		
		// Set Mouse Clicked Event
		emailTreeView.setOnMouseClicked(e -> {
			EmailFolderBean<String> item = (EmailFolderBean<String>) emailTreeView.getSelectionModel().getSelectedItem();
			if (item != null && !item.isTopElement()){
				emailTableView.setItems(item.getData());
				getModelAccess().setSelectedFolder(item);
				// clear the selected message
				getModelAccess().setSelectedMessage(null);
			}
		});
		emailTableView.setOnMouseClicked(e -> {
			EmailMessageBean message = emailTableView.getSelectionModel().getSelectedItem();
			if (message != null){
				emailContentView.getEngine().loadContent(message.getContent());
				getModelAccess().setSelectedMessage(message);
			}
		});
		showDetails.setOnAction(e -> {
			Scene scene = viewFactory.getEmailDetailsScene();
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		});
	}

}
