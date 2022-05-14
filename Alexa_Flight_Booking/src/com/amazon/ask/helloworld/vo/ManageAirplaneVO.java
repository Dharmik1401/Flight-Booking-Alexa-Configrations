package com.amazon.ask.helloworld.vo;


public class ManageAirplaneVO {

	
	private int id;
	
	
	private ManageAirlineVO manageAirlineVO; 
	
	
	private ManageAirplaneTypeVO manageAirplaneTypeVO;

	
	
	private String airplaneNumber;
	
	
	private String airplaneDescription;
	
	
	private String airplaneFileName;
	
	
	private String airplaneFilePath;
	
	
	private boolean status=true;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ManageAirlineVO getManageAirlineVO() {
		return manageAirlineVO;
	}

	public void setManageAirlineVO(ManageAirlineVO manageAirlineVO) {
		this.manageAirlineVO = manageAirlineVO;
	}

	public ManageAirplaneTypeVO getManageAirplaneTypeVO() {
		return manageAirplaneTypeVO;
	}

	public void setManageAirplaneTypeVO(ManageAirplaneTypeVO manageAirplaneTypeVO) {
		this.manageAirplaneTypeVO = manageAirplaneTypeVO;
	}

	public String getAirplaneNumber() {
		return airplaneNumber;
	}

	public void setAirplaneNumber(String airplaneNumber) {
		this.airplaneNumber = airplaneNumber;
	}

	public String getAirplaneDescription() {
		return airplaneDescription;
	}

	public void setAirplaneDescription(String airplaneDescription) {
		this.airplaneDescription = airplaneDescription;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getAirplaneFileName() {
		return airplaneFileName;
	}

	public void setAirplaneFileName(String airplaneFileName) {
		this.airplaneFileName = airplaneFileName;
	}

	public String getAirplaneFilePath() {
		return airplaneFilePath;
	}

	public void setAirplaneFilePath(String airplaneFilePath) {
		this.airplaneFilePath = airplaneFilePath;
	}

		
}
