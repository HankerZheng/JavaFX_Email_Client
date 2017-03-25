package com.hanker;

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
			Scene scene = viewFactory.getMainScene();
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch (Exception e){
			System.out.println(e.getCause());
			e.printStackTrace();
		}
	}	
}
