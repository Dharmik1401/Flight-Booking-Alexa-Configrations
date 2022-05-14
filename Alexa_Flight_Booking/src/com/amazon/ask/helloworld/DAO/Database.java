package com.amazon.ask.helloworld.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazon.ask.helloworld.vo.ManageAirlineVO;
import com.amazon.ask.helloworld.vo.ManageAirplaneVO;
import com.amazon.ask.helloworld.vo.ManageAirportVO;
import com.amazon.ask.helloworld.vo.ManageCityVO;
import com.amazon.ask.helloworld.vo.ManageScheduleVO;

public class Database {

	static Connection con = null;
	static final String endpoint = "jdbc:mysql://flight-booking.cnccmrgkrv0p.us-east-1.rds.amazonaws.com/project_db";
	static final String username = "admin";
	static final String userpwd = "Flight14";

	private static Statement connection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(endpoint, username, userpwd);
			Statement s = con.createStatement();
			return s;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public int getCityId(String cityName) {
		int cityid = 0;
		try {
			Statement s = connection();
			ResultSet rs = s.executeQuery("select * from managecity_table where city_name='" + cityName + "'");
			while (rs.next()) {
				cityid = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cityid;
	}

	public List getScheduleByCityId(int toCityId, int fromCityId) {
		List ls = new ArrayList();
		try {
			Statement s = connection();/*
										 * ResultSet rs = s.
										 * executeQuery("SELECT * FROM manageschedule_table INNER JOIN manageairplane_table "
										 * +
										 * "ON manageschedule_table. manageAirplaneNumber_id = manageairplane_table.id"
										 * + "WHERE manageToCityVO_id = " +
										 * toCityId +
										 * " AND manageFromCityVO_id = " +
										 * fromCityId );
										 */

			ResultSet rs = s.executeQuery("SELECT * FROM manageschedule_table INNER JOIN manageairplane_table "
					+ "ON manageschedule_table. manageAirplaneNumber_id = manageairplane_table.`id`"
					+ "WHERE manageToCityVO_id = " + toCityId + " AND manageFromCityVO_id = " + fromCityId);

			while (rs.next()) {
				int airplaneId = rs.getInt("manageAirplaneNumber_id");
				String routeName = rs.getString("route_name");
				String airplaneDescription = rs.getString("airplane_description");
				String airplaneNumber = rs.getString("airplane_number");
				int planeId = rs.getInt("manageairplane_table.id");
				int scheduleId = rs.getInt("manageschedule_table.id");
				String days = rs.getString("days");

				ManageAirplaneVO airplaneVO = new ManageAirplaneVO();
				airplaneVO.setId(planeId);
				airplaneVO.setAirplaneNumber(airplaneNumber);
				airplaneVO.setAirplaneDescription(airplaneDescription);

				ManageScheduleVO scheduleVO = new ManageScheduleVO();
				scheduleVO.setId(scheduleId);
				scheduleVO.setRouteName(routeName);
				scheduleVO.setManageAirplaneVO(airplaneVO);
				scheduleVO.setDays(days);

				ls.add(scheduleVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ls;
	}

	public List getplanByAirLineName(String airLineName) {
		List ls = new ArrayList();
		try {
			Statement s = connection();

			ResultSet rs = s.executeQuery("SELECT * FROM manageairplane_table INNER  JOIN manageairline_table "
					+ "ON manageairplane_table.manageAirline_id = manageairline_table.id "
					+ "WHERE manageairline_table.airline_name='" + airLineName + "'");

			while (rs.next()) {

				int airlineId = rs.getInt("manageairline_table.id");
				String airline_name = rs.getString("airline_name");
				String airline_description = rs.getString("airline_description");

				int planId = rs.getInt("manageairplane_table.id");
				String planNumber = rs.getString("airplane_number");
				String plandescription = rs.getString("airplane_description");

				ManageAirlineVO airlineVO = new ManageAirlineVO();
				airlineVO.setId(airlineId);
				airlineVO.setAirlineName(airline_name);
				airlineVO.setAirlineDescription(airline_description);

				ManageAirplaneVO airplaneVO = new ManageAirplaneVO();
				airplaneVO.setId(planId);
				airplaneVO.setAirplaneNumber(planNumber);
				airplaneVO.setAirplaneDescription(plandescription);
				airplaneVO.setManageAirlineVO(airlineVO);

				ls.add(airplaneVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ls;
	}

	public List getAirportByCityName(String city_name) {
		List ls = new ArrayList();
		try {
			Statement s = connection();
			/*
			 * ResultSet rs = s.executeQuery(
			 * "SELECT * FROM manageairport_table WHERE manageCityVO_id IN (SELECT id FROM managecity_table WHERE city_name='"
			 * + city_name + "')");
			 */

			ResultSet rs = s.executeQuery(
					"SELECT * FROM manageairport_table INNER  JOIN managecity_table ON manageairport_table.manageCityVO_id = managecity_table.id WHERE managecity_table.city_name='"
							+ city_name + "' ");

			while (rs.next()) {

				int airportId = rs.getInt("manageairport_table.id");
				String airport_name = rs.getString("airport_name");
				String airport_description = rs.getString("airport_description");
				String airport_type = rs.getString("airport_type");

				int cityId = rs.getInt("manageairport_table.manageCityVO_id");
				String cityName = rs.getString("city_name");

				ManageCityVO cityVO = new ManageCityVO();
				cityVO.setId(cityId);
				cityVO.setCityName(cityName);

				ManageAirportVO airportVO = new ManageAirportVO();
				airportVO.setId(airportId);
				airportVO.setAirportName(airport_name);
				airportVO.setAirportDescription(airport_description);
				airportVO.setAirportType(airport_type);
				airportVO.setManageCityVO(cityVO);

				ls.add(airportVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ls;
	}

	public List getminpricingBySchedule(int tocityid, int fromcityid) {
		List ls = new ArrayList();
		try {
			Statement s = connection();
			System.out.println("SELECT *,MIN(pricing) FROM manageairplane_table INNER JOIN manageschedule_table "
					+ "ON manageschedule_table.manageAirplaneNumber_id = manageairplane_table.id"
					+ " WHERE manageToCityVO_id = " + tocityid + " AND manageFromCityVO_id=" + fromcityid);
			ResultSet rs = s
					.executeQuery("SELECT *,MIN(pricing) FROM manageairplane_table INNER JOIN manageschedule_table "
							+ "ON manageschedule_table.manageAirplaneNumber_id = manageairplane_table.id"
							+ " WHERE manageToCityVO_id = " + tocityid + " AND manageFromCityVO_id=" + fromcityid);
			while (rs.next()) {

				int airplaneId = rs.getInt("manageairplane_table.id");
				System.out.println("airplaneId: " + airplaneId);
				int scheduleid = rs.getInt("manageschedule_table.id");

				int toCityId = rs.getInt("manageschedule_table.manageToCityVO_id");

				String airplaneNumber = rs.getString("manageairplane_table.airplane_number");
				System.out.println("airplaneNumber: " + airplaneNumber);
				String airplaneDescription = rs.getString("airplane_description");
				String pricing1 = rs.getString("manageschedule_table.pricing");
				System.out.println("pricing1: " + pricing1);

				ManageAirplaneVO airplaneVO = new ManageAirplaneVO();
				airplaneVO.setAirplaneNumber(airplaneNumber);
				airplaneVO.setAirplaneDescription(airplaneDescription);

				ManageScheduleVO scheduleVO = new ManageScheduleVO();
				scheduleVO.setId(scheduleid);
				scheduleVO.setPricing(pricing1);
				scheduleVO.setManageAirplaneVO(airplaneVO);

				ls.add(scheduleVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(ls.size());
		return ls;
	}

	public List getSchedule() {
		List ls = new ArrayList();
		try {
			Statement s = connection();
			ResultSet rs = s.executeQuery("SELECT * FROM manageschedule_table");
			while (rs.next()) {
				int scheduleid = rs.getInt("id");
				String route_name = rs.getString("route_name");
				int manageAirplaneNumber_id = rs.getInt("manageAirplaneNumber_id");
				String route_type = rs.getString("route_type");
				String pricing = rs.getString("pricing");
				String DepartureTime = rs.getString("departureTime");
				String ArrivalTime = rs.getString("arrivalTime");
				String days = rs.getString("days");

				ManageAirplaneVO airplaneVO = new ManageAirplaneVO();
				airplaneVO.setId(manageAirplaneNumber_id);

				ManageScheduleVO scheduleVO = new ManageScheduleVO();
				scheduleVO.setId(scheduleid);
				scheduleVO.setRouteName(route_name);
				scheduleVO.setManageAirplaneVO(airplaneVO);
				scheduleVO.setRouteType(route_type);
				scheduleVO.setPricing(pricing);
				scheduleVO.setDepartureTime(DepartureTime);
				scheduleVO.setArrivalTime(ArrivalTime);
				scheduleVO.setDays(days);

				ls.add(scheduleVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ls;

	}

	public String timeDiffrenceByScheduleId(int id) {
		String diffrence = "";
		try {
			Statement s = connection();
			ResultSet rs = s.executeQuery(
					"SELECT TIMEDIFF(ArrivalTime,DepartureTime) FROM manageschedule_table WHERE id=" + id);
			while (rs.next()) {
				diffrence = rs.getString(1);
				System.out.println(diffrence);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return diffrence;

	}

	public static String getToday() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("EEEE");
		String todayDay = format.format(date);

		System.out.println("today date:" + date + " day: " + todayDay);
		return todayDay;
	}

	public List getScheduleIdByDay(List ls) {
		String today = Database.getToday();
		List<Integer> scheduleId = new ArrayList<Integer>();
		for (int i = 0; i < ls.size(); i++) {
			ManageScheduleVO scheduleVO = (ManageScheduleVO) ls.get(i);
			System.out.println(scheduleVO.getDays());
			String[] days = scheduleVO.getDays().split(" ");

			for (String day : days) {
				if (day.contains(today)) {
					System.out.println(day + " id >>" + scheduleVO.getId());
					scheduleId.add(scheduleVO.getId());

				} else {
					System.out.println(day);
				}
			}

		}
		return scheduleId;
	}

	public static int differenceToFloat(String difference) {
		int differenceInFloat = 0;

		String difference2 = difference.trim();
		try {
			if (difference2.contains(":")) {

				String[] time = difference2.split(":");
				int hours = Integer.parseInt(time[0]);
				int minutes = Integer.parseInt(time[1]);
				System.out.println(hours + " minute " + minutes);
				differenceInFloat = hours + minutes;

			}
		} catch (Exception nfe) {

			nfe.printStackTrace();

		}

		return differenceInFloat;

	}

	public List getfastestFlight(int FASTESTFligtId) {
		List ls = new ArrayList();
		try {
			Statement s = connection();
			ResultSet rs = s.executeQuery("SELECT * FROM manageschedule_table INNER JOIN manageairplane_table "
					+ "ON manageschedule_table.manageAirplaneNumber_id = manageairplane_table.id "
					+ "WHERE manageschedule_table.id=" + FASTESTFligtId);
			while (rs.next()) {
				int scheduleid = rs.getInt("id");
				String route_name = rs.getString("route_name");
				int manageAirplaneNumber_id = rs.getInt("manageAirplaneNumber_id");
				String airplaneNumber = rs.getString("airplane_number");
				String airplaneDescription = rs.getString("airplane_description");
				String route_type = rs.getString("route_type");
				String pricing = rs.getString("pricing");
				String DepartureTime = rs.getString("departureTime");
				String ArrivalTime = rs.getString("arrivalTime");
				String days = rs.getString("days");

				System.out.println(" days:: " + days);

				ManageAirplaneVO airplaneVO = new ManageAirplaneVO();
				airplaneVO.setId(manageAirplaneNumber_id);
				airplaneVO.setAirplaneNumber(airplaneNumber);
				airplaneVO.setAirplaneDescription(airplaneDescription);

				ManageScheduleVO scheduleVO = new ManageScheduleVO();
				scheduleVO.setId(scheduleid);
				scheduleVO.setRouteName(route_name);
				scheduleVO.setManageAirplaneVO(airplaneVO);
				scheduleVO.setRouteType(route_type);
				scheduleVO.setPricing(pricing);
				scheduleVO.setDepartureTime(DepartureTime);
				scheduleVO.setArrivalTime(ArrivalTime);
				scheduleVO.setDays(days);

				ls.add(scheduleVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ls;
	}

	public List bookingByAirplaneNumber(String airplane_number) {

		List ls = new ArrayList();
		try {
			Statement s = connection();
			ResultSet rs = s.executeQuery(
					"SELECT * FROM manageairplane_table INNER JOIN manageairline_table ON manageairplane_table.manageAirline_id=manageairline_table.id WHERE airplane_number='"
							+ airplane_number + "' ");
			while (rs.next()) {

				int AirplaneNumber_id = rs.getInt("manageairplane_table.id");
				String airplaneNumber = rs.getString("airplane_number");
				String airplaneDescription = rs.getString("airplane_description");

				int airline_id = rs.getInt("manageairplane_table.manageAirline_id");
				String airlineName = rs.getString("airline_name");

				ManageAirlineVO airlineVO = new ManageAirlineVO();
				airlineVO.setId(airline_id);
				airlineVO.setAirlineName(airlineName);

				ManageAirplaneVO airplaneVO = new ManageAirplaneVO();
				airplaneVO.setId(AirplaneNumber_id);
				airplaneVO.setAirplaneNumber(airplaneNumber);
				airplaneVO.setAirplaneDescription(airplaneDescription);
				airplaneVO.setManageAirlineVO(airlineVO);

				ls.add(airplaneVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ls;

	}
	
	
	public int checkEmail(String replacepemail) {
		int id=0;
		try {
			Statement s = connection();
			String q = "SELECT loginId FROM login WHERE username='"+ replacepemail+"'";
			System.out.println(q);
			ResultSet rs = s.executeQuery(q);
			while (rs.next()) {
				id = rs.getInt("loginId");
				System.out.println("Query loginId :"+id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	
	
	
	
	
	public void saveBooking(int loginId, String date, int planId) {
		try {
			Statement s = connection();
			String q = "INSERT INTO booking_table(flightBookingDate,login_id,airplane_id,status) "
					+ "VALUES('"+date+"','"+loginId+"','"+planId+"','true')";
			System.out.println("Query :: "+q);
			s.executeUpdate(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void registerUser(String replacepemail,String password) {
		try {
			Statement s = connection();
			String q = "INSERT INTO login(enabled,PASSWORD,role,username) VALUES('1','"+password+"','ROLE_USER','"+replacepemail+"')";
			System.out.println("Query :: "+q);
			s.executeUpdate(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	


	/* MAIN START */

	public static void main(String[] args) {
		Database d = new Database();

		
		int id=d.checkEmail("toparedharmik04@gmail.com");
		System.out.println(id);
		
		/*List ls = d.bookingByAirplaneNumber("SG4496");

		for (int i = 0; i < ls.size(); i++) {
			if (i == 0) {
				ManageAirplaneVO airplaneVO = (ManageAirplaneVO) ls.get(0);

				System.out.println((i + 1) + ". Flight no" + " " + airplaneVO.getAirplaneNumber() + " " + "is succesfully booked "
						+ "and your flight details are as follows:" + "Flight no" + " " +  airplaneVO.getAirplaneNumber() + " "
						+ "is for" + " " + airplaneVO.getAirplaneDescription() + " " + "and it is of" + " "
						+ airplaneVO.getManageAirlineVO().getAirlineName()+ " " + "airline");
			} else {
				ManageAirplaneVO airplaneVO = (ManageAirplaneVO) ls.get(i);

				System.out.println((i + 1) + ". Flight no" + " " + airplaneVO.getAirplaneNumber() + " " + "is succesfully booked "
						+ "and your flight details are as follows:" + "Flight no" + " " +  airplaneVO.getAirplaneNumber() + " "
						+ "is for" + " " + airplaneVO.getAirplaneDescription() + " " + "and it is of" + " "
						+ airplaneVO.getManageAirlineVO().getAirlineName()+ " " + "airline");
			}
		}*/
				
		/*
		 * List ls = d.getSchedule(); List scheduleId =
		 * d.getScheduleIdByDay(ls);
		 * 
		 * System.out.println(scheduleId.size()); String
		 * speech="Available Flights for today"; for (int i = 0; i <
		 * scheduleId.size(); i++) { int schedule= (Integer)scheduleId.get(i);
		 * List fastestFlightList = d.getfastestFlight(schedule);
		 * 
		 * for (int j = 0; j < fastestFlightList.size(); j++) { if(j==0){
		 * ManageScheduleVO scheduleVO2 =
		 * (ManageScheduleVO)fastestFlightList.get(0); speech =speech+" "+(i+1)+
		 * ". flight between "+scheduleVO2.getManageAirplaneVO().
		 * getAirplaneDescription()
		 * +" has Flight Number "+scheduleVO2.getManageAirplaneVO().
		 * getAirplaneNumber() +"" +
		 * ". it's Departure time "+scheduleVO2.getDepartureTime()
		 * +" and it reach to the destination at "+scheduleVO2.getArrivalTime();
		 * }else{ ManageScheduleVO scheduleVO2 =
		 * (ManageScheduleVO)fastestFlightList.get(0); speech
		 * =speech+", <br> "+(i+1)+
		 * ". flight between "+scheduleVO2.getManageAirplaneVO().
		 * getAirplaneDescription()
		 * +" has Flight Number "+scheduleVO2.getManageAirplaneVO().
		 * getAirplaneNumber() +"" +
		 * ". it's Departure time "+scheduleVO2.getDepartureTime()
		 * +" and it reach to the destination at "+scheduleVO2.getArrivalTime();
		 * } } } System.out.println(speech);
		 */

		/* Fastest Flight Start */

		/*
		 * int toCityId = d.getCityId("Mumbai");
		 * System.out.println("toCityId: "+toCityId); int fromCityId =
		 * d.getCityId("Ahmedabad");
		 * System.out.println("fromCityId: "+fromCityId);
		 * 
		 * List ls = d.getScheduleByCityId(toCityId,fromCityId);
		 * 
		 * 
		 * List scheduleId = d.getScheduleIdByDay(ls);
		 * System.out.println("00000"+scheduleId);
		 * 
		 * 
		 * int[] differenceInFloat = new int[scheduleId.size()];
		 * System.out.println("array length: "+differenceInFloat.length);
		 * 
		 * for (int i = 0; i < scheduleId.size(); i++) { int id =
		 * (Integer)scheduleId.get(i); String
		 * difference=d.timeDiffrenceByScheduleId(id);
		 * 
		 * for (int j = i; j <scheduleId.size(); j++) { differenceInFloat[j] =
		 * d.differenceToFloat(difference);
		 * 
		 * } } int lesserCount= 0; for (int j = 0; j <
		 * differenceInFloat.length-1; j++) {
		 * if(differenceInFloat[j]<differenceInFloat[j+1]){ lesserCount =
		 * differenceInFloat[j]; System.out.println(j+"greter :: "
		 * +differenceInFloat[j]); } else{ lesserCount = differenceInFloat[j];
		 * System.out.println(j+"lesser :: " +differenceInFloat[j]); }
		 * 
		 * }
		 * 
		 * System.out.println("lesserCount "+lesserCount); ManageScheduleVO
		 * scheduleVO=(ManageScheduleVO)ls.get(lesserCount);
		 * System.out.println("lessersList "+scheduleVO.getId());
		 * 
		 * int fastesttFligtId = scheduleVO.getId();
		 * 
		 * List fastestFlightList = d.getfastestFlight(fastesttFligtId);
		 * 
		 * ManageScheduleVO scheduleVO2 =
		 * (ManageScheduleVO)fastestFlightList.get(0);
		 * System.out.println("Fastest flight between "+scheduleVO2.
		 * getManageAirplaneVO().getAirplaneDescription()
		 * +" has Flight Number "+scheduleVO2.getManageAirplaneVO().
		 * getAirplaneNumber() +"" +
		 * ". it's Departure time "+scheduleVO2.getDepartureTime()
		 * +" and it reach to the destination at "+scheduleVO2.getArrivalTime())
		 * ;
		 */

		/*
		 * int toCityId = d.getCityId("Mumbai"); System.out.println("toCityId: "
		 * + toCityId); int fromCityId = d.getCityId("Ahmedabad");
		 * System.out.println("fromCityId: " + fromCityId);
		 * 
		 * List ls = d.getminpricingBySchedule(toCityId, fromCityId);
		 * 
		 * ManageScheduleVO scheduleVO = (ManageScheduleVO) ls.get(0);
		 * System.out.println("Cheapest Flight From " +
		 * scheduleVO.getManageAirplaneVO().getAirplaneDescription() +
		 * " having Flight Number " +
		 * scheduleVO.getManageAirplaneVO().getAirplaneNumber() +
		 * " and price for the trip is " + scheduleVO.getPricing() +
		 * " rupees ");
		 */

		/*
		 * List ls = d.getplanByAirLineName("IndiGo");
		 * 
		 * for (int i = 0; i < ls.size(); i++) {
		 * 
		 * if (i == 0) { ManageAirplaneVO airplaneVO = (ManageAirplaneVO)
		 * ls.get(0); System.out.println((i + 1) + ". Flight Number " +
		 * airplaneVO.getAirplaneNumber() + " available from " +
		 * airplaneVO.getAirplaneDescription()); } else { ManageAirplaneVO
		 * airplaneVO = (ManageAirplaneVO) ls.get(i); System.out.println((i + 1)
		 * + ". Flight Number " + airplaneVO.getAirplaneNumber() +
		 * " available from " + airplaneVO.getAirplaneDescription()); }
		 * 
		 * }
		 */

		/*List ls = d.getAirportByCityName("Mumbai");

		for (int i = 0; i < ls.size(); i++) {

			if (i == 0) {
				ManageAirportVO airportVO = (ManageAirportVO) ls.get(0);
				System.out
						.println((i + 1) + ". Available Nearest Airport in " + airportVO.getManageCityVO().getCityName()
								+ " " + "is" + " " + airportVO.getAirportDescription() + " " + "& Type of Airport is"
								+ " " + airportVO.getAirportType());
			} else {
				ManageAirportVO airportVO = (ManageAirportVO) ls.get(i);
				System.out
						.println((i + 1) + ". Available Nearest Airport in " + airportVO.getManageCityVO().getCityName()
								+ " " + "is" + " " + airportVO.getAirportDescription() + " " + "& Type of Airport is"
								+ " " + airportVO.getAirportType());
			}

		}*/
		

		/*
		 * int toCityId = d.getCityId("Mumbai"); int fromCityId =
		 * d.getCityId("Ahmedabad");
		 * 
		 * List ls = d.getScheduleByCityId(toCityId, fromCityId);
		 * 
		 * for (int i = 0; i < ls.size(); i++) { if (i == 0) { ManageScheduleVO
		 * scheduleVO = (ManageScheduleVO) ls.get(0); System.out.println((i + 1)
		 * + ". Flight Number " +
		 * scheduleVO.getManageAirplaneVO().getAirplaneNumber() +
		 * " available from " +
		 * scheduleVO.getManageAirplaneVO().getAirplaneDescription() +
		 * " and rout Name is " + scheduleVO.getRouteName()); } else {
		 * ManageScheduleVO scheduleVO = (ManageScheduleVO) ls.get(i);
		 * System.out.println((i + 1) + ". Flight Number " +
		 * scheduleVO.getManageAirplaneVO().getAirplaneNumber() +
		 * " available from " +
		 * scheduleVO.getManageAirplaneVO().getAirplaneDescription() +
		 * " and rout Name is " + scheduleVO.getRouteName()); }
		 * 
		 * }
		 */

	}

}