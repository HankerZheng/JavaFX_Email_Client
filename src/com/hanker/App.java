package com.hanker;

import java.util.HashMap;

import com.hanker.controller.services.CreateAndRegisterEmailAccountService;
import com.hanker.view.ViewFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage){
		try{
			ViewFactory viewFactory = ViewFactory.defaultViewFactory;
			Configuration config = Configuration.getInstance();
			Scene scene = viewFactory.getMainScene();
			primaryStage.setScene(scene);
			primaryStage.show();
			for (HashMap<String, String> accountInfo: config.getAccountInfo()){
				CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService = new CreateAndRegisterEmailAccountService(
						accountInfo.get("username"), 
						accountInfo.get("password"), 
						viewFactory.getModelAccess());
				createAndRegisterEmailAccountService.start();
			}
		}catch (Exception e){
			System.out.println(e.getCause());
			e.printStackTrace();
		}
	}
}
