package com.amazon.ask.helloworld.vo;



public class BookingVO {

	
	private int id;
	

/*	private String bookingTime;*/
	
	
	private String flightBookingDate;
	
	
	private LoginVO loginVO;

	
	private ManageAirplaneVO manageAirplaneVO;
	
	private boolean status=true;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
/*
	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

*/	public String getFlightBookingDate() {
		return flightBookingDate;
	}

	public void setFlightBookingDate(String flightBookingDate) {
		this.flightBookingDate = flightBookingDate;
	}

	public LoginVO getLoginVO() {
		return loginVO;
	}

	public void setLoginVO(LoginVO loginVO) {
		this.loginVO = loginVO;
	}

	public ManageAirplaneVO getManageAirplaneVO() {
		return manageAirplaneVO;
	}

	public void setManageAirplaneVO(ManageAirplaneVO manageAirplaneVO) {
		this.manageAirplaneVO = manageAirplaneVO;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
