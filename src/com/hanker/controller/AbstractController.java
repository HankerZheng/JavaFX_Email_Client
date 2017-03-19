package com.hanker.controller;

import javafx.fxml.Initializable;

public abstract class AbstractController implements Initializable{
	
	private ModelAccess modelAccess;
	
	public AbstractController(ModelAccess modelAccess){
		this.modelAccess = modelAccess;
	}
	
	public ModelAccess getModelAccess(){
		return modelAccess;
	}
}
