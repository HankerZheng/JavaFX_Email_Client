package com.hanker.controller;

import java.net.URL;
import java.util.ResourceBundle;


import com.hanker.controller.services.CreateAndRegisterEmailAccountService;
import com.hanker.controller.services.FolderUpdateService;
import com.hanker.controller.services.MessageRendererService;
import com.hanker.model.EmailConstants;
import com.hanker.model.EmailMessageBean;
import com.hanker.model.SizeObject;
import com.hanker.model.folder.EmailFolderBean;
import com.hanker.model.table.BoldableRowFactory;
import com.hanker.view.ViewFactory;

import javafx.application.Platform;
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
//		EmailAccountBean emailAccountBean = new EmailAccountBean(
//				"hanker.test@gmail.com", "testzhengPassword");
//		EmailFolderBean<String> folder = getModelAccess().getSelectedFolder();
//		emailAccountBean.addEmailsToData(folder.getData(), folder.getValue());
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
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ViewFactory viewFactory = ViewFactory.defaultViewFactory;
		emailTableView.setRowFactory(e -> new BoldableRowFactory<>());
		
		// Service Initialization
		MessageRendererService messageRendererService = new MessageRendererService(emailContentView.getEngine());

		FolderUpdateService folderUpdateService = new FolderUpdateService(getModelAccess().getFolderList());
		folderUpdateService.start();
		
		// Bind the column with the field of type `EmailMessageBean`
		senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
		subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, SizeObject>("size"));
		
		// Initialize EmailTreeView with an dummy root, so that it can
		// support several email account
		EmailFolderBean<String> root = new EmailFolderBean<>("");
		emailTreeView.setRoot(root);
		emailTreeView.setShowRoot(false);
		
		// connect to email server asynchronously
		CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService = new CreateAndRegisterEmailAccountService(
				"hanker.test@gmail.com",
				"testzhengPassword",
				root,
				getModelAccess());
		createAndRegisterEmailAccountService.start();

		// Initialize EmailTableView's right clicked menu
		emailTableView.setContextMenu(new ContextMenu(showDetails));
		
		// On clicking the email folder, display all emails in that folder in emailTableView
		emailTreeView.setOnMouseClicked(e -> {
			// get the selected email folder
			EmailFolderBean<String> item = (EmailFolderBean<String>) emailTreeView.getSelectionModel().getSelectedItem();
			if (item != null && !item.isTopElement()){
				// display the e-mails in that selected folder
				emailTableView.setItems(item.getData());
				getModelAccess().setSelectedFolder(item);
				// clear the selected message
				getModelAccess().setSelectedMessage(null);
			}
		});
		// On clicking the email, display the content of that email in emailWebView
		emailTableView.setOnMouseClicked(e -> {
			EmailMessageBean messageBean = emailTableView.getSelectionModel().getSelectedItem();
			if (messageBean != null){
				messageRendererService.setMessageBean(messageBean);
				Platform.runLater(messageRendererService);
				getModelAccess().setSelectedMessage(messageBean);
			}
		});
		// On clicking the "show details" menu, create a new window and display the content of that email
		showDetails.setOnAction(e -> {
			Scene scene = viewFactory.getEmailDetailsScene();
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		});
		
		// After all the initialization check the connection
		if (createAndRegisterEmailAccountService.getValue() == EmailConstants.LOGIN_STATE_SUCCESS){
			System.out.println("Successful connection!");
		}
	}

}
