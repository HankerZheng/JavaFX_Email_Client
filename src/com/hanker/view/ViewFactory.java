package com.hanker.view;

import java.io.IOException;

import com.hanker.controller.AbstractController;
import com.hanker.controller.EmailDetailsController;
import com.hanker.controller.MainController;
import com.hanker.controller.ModelAccess;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewFactory {
	
	public static ViewFactory defaultViewFactory = new ViewFactory();
	
	private final String DEFAULT_CSS = "css/style.css";
	private final String EMAIL_DETAILS_FXML = "fxmls/EmailDetailsLayout.fxml";
	private final String MAIN_FXML = "fxmls/MainLayout.fxml";
	
	private ModelAccess modelAccess = new ModelAccess();
	
	private MainController mainController;
	private EmailDetailsController emailDetailsController;
	
	public Scene getMainScene(){
		if (mainController == null){
			mainController = new MainController(modelAccess);
		}
		return initializeScene(MAIN_FXML, mainController);		
	}
	
	public Scene getEmailDetailsScene(){
		if (emailDetailsController == null){
			emailDetailsController = new EmailDetailsController(modelAccess);
		}
		return initializeScene(EMAIL_DETAILS_FXML, emailDetailsController);
	}
	
	private Scene initializeScene(String fxmlPath, AbstractController controller){
		FXMLLoader loader;
		Parent parent;
		Scene scene;
		try {
			loader = new FXMLLoader(getClass().getResource(fxmlPath));
			loader.setController(controller);
			parent = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		scene = new Scene(parent);
		scene.getStylesheets().add(getClass().getResource(DEFAULT_CSS).toExternalForm());
		return scene;
	}
	
	public Node resolveIcon(String treeItemValue){
		String lowerCaseTreeItemValue = treeItemValue.toLowerCase();
		ImageView returnIcon;
			try {
				if(lowerCaseTreeItemValue.contains("inbox")){
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/inbox.png")));
				} else if(lowerCaseTreeItemValue.contains("sent")){
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/sent2.png")));
				} else if(lowerCaseTreeItemValue.contains("spam")){
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/spam.png")));
				} else if(lowerCaseTreeItemValue.contains("@")){
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/email.png")));
				} else{
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/folder.png")));
				}
			} catch (NullPointerException e) {
				System.out.println("Invalid image location!!!");
				returnIcon = new ImageView();
			} catch (Exception e){
				e.printStackTrace();
				returnIcon = new ImageView();
			}
			
			returnIcon.setFitHeight(16);
			returnIcon.setFitWidth(16);

		return returnIcon;
	}
}
