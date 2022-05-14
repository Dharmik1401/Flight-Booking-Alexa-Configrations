package com.amazon.ask.helloworld.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.helloworld.DAO.Database;
import com.amazon.ask.helloworld.vo.ManageAirplaneVO;
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

public class FlightByAirlineHandler implements RequestHandler {

	public static final String AIRLINENAME_SLOT = "airlineName";

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("FlightByAirlineHandler"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		System.out.println(":::::::::::FlightByAirlineHandler start ::::::::::: ");
		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();

		// Get the color slot from user input.
		Slot airlineNameSlot = slots.get(AIRLINENAME_SLOT);

		String speechText = "", repromptText = "";
		boolean noAnswerProvided = false;
		// Check for favorite color and create output to user.

		// Store the user's favorite color in the Session and create response.
		String airlineName = airlineNameSlot.getValue();

		System.out.println("airlineName :::::" + airlineName);

		Database d = new Database();
		List ls = d.getplanByAirLineName(airlineName);
		ManageAirplaneVO airplaneVO1 = (ManageAirplaneVO) ls.get(0);
		String speech = "";

		for (int i = 0; i < ls.size(); i++) {

			if (i == 0) {
				ManageAirplaneVO airplaneVO = (ManageAirplaneVO) ls.get(0);

				System.out.println((i + 1) + ". Flight Number " + airplaneVO.getAirplaneNumber() + " available from "
						+ airplaneVO.getAirplaneDescription());
				speech = (i + 1) + ". Flight Number " + airplaneVO.getAirplaneNumber() + " available from "
						+ airplaneVO.getAirplaneDescription() + ". ";
			} else {
				ManageAirplaneVO airplaneVO = (ManageAirplaneVO) ls.get(i);
				System.out.println((i + 1) + ". Flight Number " + airplaneVO.getAirplaneNumber() + " available from "
						+ airplaneVO.getAirplaneDescription());
				speech = speech + (i + 1) + ". Flight Number " + airplaneVO.getAirplaneNumber() + " available from "
						+ airplaneVO.getAirplaneDescription();
			}

		}

		speechText = String.format(airplaneVO1.getManageAirlineVO().getAirlineName() + " is a"
				+ airplaneVO1.getManageAirlineVO().getAirlineDescription() + " has following flights,. " + speech + "");

		System.out.println(":::::::::::FlightByAirlineHandler start ::::::::::: ");
		return input.getResponseBuilder().withSimpleCard("ColorSession", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(!noAnswerProvided).withShouldEndSession(false).build();
	}
  
}