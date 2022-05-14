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

public class FastestFlightHandler implements RequestHandler {

	public static final String TOCITY_SLOT = "toCity";
	public static final String FROMCITY_SLOT = "fromCity";

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("FastestFlightHandler"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		System.out.println(":::::::::::FastestFlightHandler start ::::::::::: ");
		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();

		// Get the color slot from user input.
		Slot tocity = slots.get(TOCITY_SLOT);
		Slot fromcity = slots.get(FROMCITY_SLOT);
		String speechText = "", repromptText = "";
		boolean noAnswerProvided = false;
		// Check for favorite color and create output to user.

		// Store the user's favorite color in the Session and create response.
		String toCity = tocity.getValue();
		String fromCity = fromcity.getValue();

		System.out.println("toCity :::::" + toCity);
		System.out.println("fromCity :::::" + fromCity);

		Database d = new Database();
		int toCityId = d.getCityId(toCity);
		System.out.println("toCityId: " + toCityId);
		int fromCityId = d.getCityId(fromCity);
		System.out.println("fromCityId: " + fromCityId);

		List ls = d.getScheduleByCityId(toCityId, fromCityId);

		List scheduleId = d.getScheduleIdByDay(ls);
		int[] differenceInFloat = new int[scheduleId.size()];
		System.out.println("array length: " + differenceInFloat.length);

		for (int i = 0; i < scheduleId.size(); i++) {
			int id = (Integer) scheduleId.get(i);
			String difference = d.timeDiffrenceByScheduleId(id);

			for (int j = i; j < scheduleId.size(); j++) {
				differenceInFloat[j] = d.differenceToFloat(difference);

			}
		}
		int lesserCount = 0;
		for (int j = 0; j < differenceInFloat.length - 1; j++) {
			if (differenceInFloat[j] < differenceInFloat[j + 1]) {
				lesserCount = differenceInFloat[j];
				System.out.println(j + "greter :: " + differenceInFloat[j]);
			} else {
				lesserCount = differenceInFloat[j];
				System.out.println(j + "lesser :: " + differenceInFloat[j]);
			}

		}

		System.out.println("lesserCount " + lesserCount);
		ManageScheduleVO scheduleVO = (ManageScheduleVO) ls.get(lesserCount);
		System.out.println("lessersList " + scheduleVO.getId());

		int fastesttFligtId = scheduleVO.getId();
		String speech = "";

		List fastestFlightList = d.getfastestFlight(fastesttFligtId);

		ManageScheduleVO scheduleVO2 = (ManageScheduleVO) fastestFlightList.get(0);
		System.out.println("Fastest flight between " + scheduleVO2.getManageAirplaneVO().getAirplaneDescription()
				+ " has Flight Number " + scheduleVO2.getManageAirplaneVO().getAirplaneNumber() + ""
				+ ". it's Departure time " + scheduleVO2.getDepartureTime() + " and it reach to the destination at "
				+ scheduleVO2.getArrivalTime());
		speech = speech + "." + "Fastest flight between " + scheduleVO2.getManageAirplaneVO().getAirplaneDescription()
				+ " has Flight Number " + scheduleVO2.getManageAirplaneVO().getAirplaneNumber() + ""
				+ ". it's Departure time " + scheduleVO2.getDepartureTime() + " and it reach to the destination at "
				+ scheduleVO2.getArrivalTime();

		speechText = String.format(speech
				+ ",. You can also find Flights of your choice date by saying,  Give me avaliable flights of today'*****'");

		System.out.println(":::::::::::AvailableFlightHandler start ::::::::::: ");
		return input.getResponseBuilder().withSimpleCard("ColorSession", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(!noAnswerProvided).withShouldEndSession(false).build();
	}

}
