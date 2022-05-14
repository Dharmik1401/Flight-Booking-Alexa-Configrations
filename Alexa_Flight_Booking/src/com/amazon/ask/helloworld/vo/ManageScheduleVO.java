package com.amazon.ask.helloworld.vo;



public class ManageScheduleVO {
	
	
	private int id;
	
	private String routeName;
	

	private ManageAirplaneVO manageAirplaneVO;
	
	
	private String routeType;
	
	private String pricing;
	
	

	private ManageCityVO manageFromCityVO;
	
	
	private ManageCityVO manageToCityVO;
	
	
	private ManageAirportVO manageFromAirportVO;
	
	private ManageAirportVO manageToAirportVO;
	
	private String departureTime;
	
	private String arrivalTime;
	
	
	private ManageAirplaneVO manageReturnAirplaneVO;
	
	
	
	private ManageCityVO manageReturnFromCityVO;
	
	private ManageCityVO manageReturnToCityVO;
	
	
	private ManageAirportVO manageReturnFromAirportVO;
	
	
	private ManageAirportVO manageReturnToAirportVO;
	
	private String returnDepartureTime;
	
	private String returnArrivalTime;
	
	
	
	private String days;
	
	
	
	
	private boolean status=true;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public ManageAirplaneVO getManageAirplaneVO() {
		return manageAirplaneVO;
	}

	public void setManageAirplaneVO(ManageAirplaneVO manageAirplaneVO) {
		this.manageAirplaneVO = manageAirplaneVO;
	}

	public ManageCityVO getManageFromCityVO() {
		return manageFromCityVO;
	}

	public void setManageFromCityVO(ManageCityVO manageFromCityVO) {
		this.manageFromCityVO = manageFromCityVO;
	}

	public String getPricing() {
		return pricing;
	}

	public void setPricing(String pricing) {
		this.pricing = pricing;
	}

	public ManageCityVO getManageToCityVO() {
		return manageToCityVO;
	}

	public void setManageToCityVO(ManageCityVO manageToCityVO) {
		this.manageToCityVO = manageToCityVO;
	}

	public ManageAirportVO getManageFromAirportVO() {
		return manageFromAirportVO;
	}

	public void setManageFromAirportVO(ManageAirportVO manageFromAirportVO) {
		this.manageFromAirportVO = manageFromAirportVO;
	}

	public ManageAirportVO getManageToAirportVO() {
		return manageToAirportVO;
	}

	public void setManageToAirportVO(ManageAirportVO manageToAirportVO) {
		this.manageToAirportVO = manageToAirportVO;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	
	
	public ManageAirplaneVO getManageReturnAirplaneVO() {
		return manageReturnAirplaneVO;
	}

	public void setManageReturnAirplaneVO(ManageAirplaneVO manageReturnAirplaneVO) {
		this.manageReturnAirplaneVO = manageReturnAirplaneVO;
	}

	public ManageCityVO getManageReturnFromCityVO() {
		return manageReturnFromCityVO;
	}

	public void setManageReturnFromCityVO(ManageCityVO manageReturnFromCityVO) {
		this.manageReturnFromCityVO = manageReturnFromCityVO;
	}

	public ManageCityVO getManageReturnToCityVO() {
		return manageReturnToCityVO;
	}

	public void setManageReturnToCityVO(ManageCityVO manageReturnToCityVO) {
		this.manageReturnToCityVO = manageReturnToCityVO;
	}

	public ManageAirportVO getManageReturnFromAirportVO() {
		return manageReturnFromAirportVO;
	}

	public void setManageReturnFromAirportVO(ManageAirportVO manageReturnFromAirportVO) {
		this.manageReturnFromAirportVO = manageReturnFromAirportVO;
	}

	public ManageAirportVO getManageReturnToAirportVO() {
		return manageReturnToAirportVO;
	}

	public void setManageReturnToAirportVO(ManageAirportVO manageReturnToAirportVO) {
		this.manageReturnToAirportVO = manageReturnToAirportVO;
	}

	public String getReturnDepartureTime() {
		return returnDepartureTime;
	}

	public void setReturnDepartureTime(String returnDepartureTime) {
		this.returnDepartureTime = returnDepartureTime;
	}

	public String getReturnArrivalTime() {
		return returnArrivalTime;
	}

	public void setReturnArrivalTime(String returnArrivalTime) {
		this.returnArrivalTime = returnArrivalTime;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}


	
	
}
