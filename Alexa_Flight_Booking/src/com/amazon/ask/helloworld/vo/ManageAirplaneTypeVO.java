package com.amazon.ask.helloworld.vo;



public class ManageAirplaneTypeVO {


	private int id;
	
	
	private String airplaneType;
	
	
	private String economyClass;
	
	
	private String businessClass;
	
	
	private String firstClass;
	
	
	private String total;
	
	
	
	private boolean status=true;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getAirplaneType() {
		return airplaneType;
	}


	public void setAirplaneType(String airplaneType) {
		this.airplaneType = airplaneType;
	}


	public String getEconomyClass() {
		return economyClass;
	}


	public void setEconomyClass(String economyClass) {
		this.economyClass = economyClass;
	}




	public String getBusinessClass() {
		return businessClass;
	}


	public void setBusinessClass(String businessClass) {
		this.businessClass = businessClass;
	}


	public String getFirstClass() {
		return firstClass;
	}


	public void setFirstClass(String firstClass) {
		this.firstClass = firstClass;
	}


	public String getTotal() {
		return total;
	}


	public void setTotal(String total) {
		this.total = total;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}

	
}
