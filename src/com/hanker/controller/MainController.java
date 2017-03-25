package com.hanker.controller;

import java.net.URL;
import java.util.ResourceBundle;


import com.hanker.controller.services.CreateAndRegisterEmailAccountService;
import com.hanker.controller.services.FolderUpdateService;
import com.hanker.controller.services.MessageRendererService;
import com.hanker.controller.services.SaveAttachmentService;
import com.hanker.model.EmailConstants;
import com.hanker.model.EmailMessageBean;
import com.hanker.model.SizeObject;
import com.hanker.model.folder.EmailFolderBean;
import com.hanker.model.table.BoldableRowFactory;
import com.hanker.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
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

    @FXML
    private Button downloadAttachmentBtn;

    @FXML
    private Label downloadLabel;
    
    @FXML
    private ProgressBar downloadProgress;
    
    private MenuItem showDetails = new MenuItem("show details");
    
    
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
		downloadLabel.setVisible(false);
		downloadProgress.setVisible(false);

		// EmailTableView bold functionality binding
		emailTableView.setRowFactory(e -> new BoldableRowFactory<>());

		// Bind the column with the field of type `EmailMessageBean`
		senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
		subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, SizeObject>("size"));
		
		// Initialize EmailTreeView with an dummy root, so that it can
		// support several email account
		EmailFolderBean<String> root = new EmailFolderBean<>("");
		emailTreeView.setRoot(root);
		emailTreeView.setShowRoot(false);

		// Service Initialization
		CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService = new CreateAndRegisterEmailAccountService(
				"hanker.test@gmail.com",
				"testzhengPassword",
				root,
				getModelAccess());
		MessageRendererService messageRendererService = new MessageRendererService(emailContentView.getEngine());
		FolderUpdateService folderUpdateService = new FolderUpdateService(getModelAccess().getFolderList());
		SaveAttachmentService saveAttachmentService = new SaveAttachmentService(downloadProgress, downloadLabel);
		createAndRegisterEmailAccountService.start();
		folderUpdateService.start();

		// Initialize EmailTableView's right clicked menu
		emailTableView.setContextMenu(new ContextMenu(showDetails));
		
		// On clicking the email folder, display all e-mails in that folder in emailTableView
		emailTreeView.setOnMouseClicked(e -> {
			// get the selected email folder
			EmailFolderBean<String> folderBean = (EmailFolderBean<String>) emailTreeView.getSelectionModel().getSelectedItem();
			if (folderBean != null && !folderBean.isTopElement() && folderBean != getModelAccess().getSelectedFolder()){
				// display the e-mails in that selected folder
				emailTableView.setItems(folderBean.getData());
				getModelAccess().setSelectedFolder(folderBean);
				// clear the selected message
				getModelAccess().setSelectedMessage(null);
			}
		});
		// On clicking the email, display the content of that email in emailWebView
		emailTableView.setOnMouseClicked(e -> {
			EmailMessageBean messageBean = emailTableView.getSelectionModel().getSelectedItem();
			if (messageBean != null && messageBean != getModelAccess().getSelectedMessage()){
				messageRendererService.setMessageBean(messageBean);
				messageRendererService.restart();
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
		
		// On clicking the "Download Attachment" button
		downloadAttachmentBtn.setOnAction( e -> {
	    	EmailMessageBean messageBean = getModelAccess().getSelectedMessage();
	    	if (messageBean != null && messageBean.hasAttachments()){
	        	saveAttachmentService.setMessageBean(messageBean);
	        	saveAttachmentService.restart();
	    	}
		});
		
		// After all the initialization check the connection
		if (createAndRegisterEmailAccountService.getValue() == EmailConstants.LOGIN_STATE_SUCCESS){
			System.out.println("Successful connection!");
		}
	}

}
