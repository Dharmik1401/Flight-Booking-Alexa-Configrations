package com.amazon.ask.helloworld.vo;



public class ManageAirlineVO {

	
	private int id;
	
	
	private String airlineName;
	
	
	private String airlineDescription;
	
	
	private boolean status=true;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getAirlineDescription() {
		return airlineDescription;
	}

	public void setAirlineDescription(String airlineDescription) {
		this.airlineDescription = airlineDescription;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
