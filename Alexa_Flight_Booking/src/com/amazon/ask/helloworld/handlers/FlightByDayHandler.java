package com.amazon.ask.helloworld.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.helloworld.DAO.Database;
import com.amazon.ask.helloworld.vo.ManageScheduleVO;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

public class FlightByDayHandler implements RequestHandler {


	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("FlightByDayHandler"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		System.out.println(":::::::::::FlightByDayHandler start ::::::::::: ");

		
		String speechText = "", repromptText = "";
		boolean noAnswerProvided = false;

		Database d = new Database();

		List ls = d.getSchedule();
		List scheduleId = d.getScheduleIdByDay(ls);

		System.out.println(scheduleId.size());
		String speech = "";
		for (int i = 0; i < scheduleId.size(); i++) {
			int schedule = (Integer) scheduleId.get(i);
			List fastestFlightList = d.getfastestFlight(schedule);

			for (int j = 0; j < fastestFlightList.size(); j++) {
				if (j == 0) {
					ManageScheduleVO scheduleVO2 = (ManageScheduleVO) fastestFlightList.get(0);
					speech = speech + " " + (i + 1) + ". flight between "
							+ scheduleVO2.getManageAirplaneVO().getAirplaneDescription() + " has Flight Number "
							+ scheduleVO2.getManageAirplaneVO().getAirplaneNumber() + "" + ". it's Departure time "
							+ scheduleVO2.getDepartureTime() + " and it reach to the destination at "
							+ scheduleVO2.getArrivalTime();
				} else {
					ManageScheduleVO scheduleVO2 = (ManageScheduleVO) fastestFlightList.get(0);
					speech = speech + ", <br> " + (i + 1) + ". flight between "
							+ scheduleVO2.getManageAirplaneVO().getAirplaneDescription() + " has Flight Number "
							+ scheduleVO2.getManageAirplaneVO().getAirplaneNumber() + "" + ". it's Departure time "
							+ scheduleVO2.getDepartureTime() + " and it reach to the destination at "
							+ scheduleVO2.getArrivalTime();
				}
			}
		}

		speechText = String.format(speech
				+ ",. You can also find airport near you by saying,  Give me airport nearest to me ");
		
		
		  System.out.println(":::::::::::FlightByDayHandler end ::::::::::: ");
	        return input.getResponseBuilder()
	                .withSimpleCard("ColorSession", speechText)
	                .withSpeech(speechText)
	                .withReprompt(repromptText)
	                .withShouldEndSession(!noAnswerProvided)
	                .withShouldEndSession(false)
	                .build();

	}

}
