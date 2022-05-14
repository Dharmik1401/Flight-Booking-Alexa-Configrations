package com.amazon.ask.helloworld.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.helloworld.DAO.Database;
import com.amazon.ask.helloworld.vo.ManageScheduleVO;
import com.amazon.ask.model.Response;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Slot;

public class AvailableFlightHandler implements RequestHandler {

	public static final String TOCITY_SLOT = "tocity";
	public static final String FROMCITY_SLOT = "fromcity";

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AvailableFlightHandler"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		System.out.println(":::::::::::AvailableFlightHandler start ::::::::::: ");
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
		int fromCityId = d.getCityId(fromCity);

		List ls = d.getScheduleByCityId(toCityId, fromCityId);

		String speech = "";

		for (int i = 0; i < ls.size(); i++) {
			if (i == 0) {
				ManageScheduleVO scheduleVO = (ManageScheduleVO) ls.get(0);
				System.out.println((i + 1) + ". Flight Number " + scheduleVO.getManageAirplaneVO().getAirplaneNumber()
						+ " available from " + scheduleVO.getManageAirplaneVO().getAirplaneDescription()
						+ " and route Name is " + scheduleVO.getRouteName());
				speech = (i + 1) + ". Flight Number " + scheduleVO.getManageAirplaneVO().getAirplaneNumber()
						+ " available from " + scheduleVO.getManageAirplaneVO().getAirplaneDescription()
						+ " and route Name is " + scheduleVO.getRouteName();
			} else {
				ManageScheduleVO scheduleVO = (ManageScheduleVO) ls.get(i);
				System.out.println((i + 1) + ". Flight Number " + scheduleVO.getManageAirplaneVO().getAirplaneNumber()
						+ " available from " + scheduleVO.getManageAirplaneVO().getAirplaneDescription()
						+ " and route Name is " + scheduleVO.getRouteName());

				speech = speech + ". " + (i + 1) + ". Flight Number "
						+ scheduleVO.getManageAirplaneVO().getAirplaneNumber() + " available from "
						+ scheduleVO.getManageAirplaneVO().getAirplaneDescription() + " and route Name is "
						+ scheduleVO.getRouteName();
			}

		}

		speechText = String.format(speech
				+ ",. You can also find Flights of your choice airline by saying,  Give me avaliable flights of '***'");

		System.out.println(":::::::::::AvailableFlightHandler start ::::::::::: ");
		return input.getResponseBuilder().withSimpleCard("ColorSession", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(!noAnswerProvided).withShouldEndSession(false).build();
	}
}