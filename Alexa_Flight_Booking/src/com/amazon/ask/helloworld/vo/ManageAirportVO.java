package com.amazon.ask.helloworld.vo;



public class ManageAirportVO {
	
	private int id;

	
	private String airportName;

	
	
	private ManageCityVO manageCityVO;
	

	
	private String airportType;
	
	
	private String airportDescription;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public ManageCityVO getManageCityVO() {
		return manageCityVO;
	}

	public void setManageCityVO(ManageCityVO manageCityVO) {
		this.manageCityVO = manageCityVO;
	}

	public String getAirportType() {
		return airportType;
	}

	public void setAirportType(String airportType) {
		this.airportType = airportType;
	}

	public String getAirportDescription() {
		return airportDescription;
	}

	public void setAirportDescription(String airportDescription) {
		this.airportDescription = airportDescription;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
	private boolean status=true;


}
