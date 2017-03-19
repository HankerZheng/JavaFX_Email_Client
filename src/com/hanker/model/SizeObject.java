package com.hanker.model;

public class SizeObject implements Comparable<SizeObject>{
	private int intSize;
	private String strSize;
	
	public SizeObject(int size){
		this.intSize = size;
		this.strSize = getFormattedSize(size);
	}
	
	public int getIntSize(){
		return intSize;
	}
	
	public String getStrSize(){
		return strSize;
	}
	
	public String toString(){
		return strSize;
	}
	
	private String getFormattedSize(int size){
		String returnValue;
		if(size<= 0){
			returnValue =  "0";}
		
		else if(size<1024){
			returnValue = size + " B";
		}
		else if(size < 1048576){
			returnValue = size/1024 + " kB";
		}else{
			returnValue = size/1048576 + " MB";
		}
		return returnValue;
	}

	@Override
	public int compareTo(SizeObject o) {
		return this.getIntSize() - o.getIntSize();
	}

}
