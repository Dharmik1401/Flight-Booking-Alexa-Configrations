package com.amazon.ask.helloworld.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.helloworld.DAO.Database;
import com.amazon.ask.helloworld.vo.ManageAirportVO;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

public class AirportByCityNameHandler implements RequestHandler{
	public static final String CITYNAME_SLOT = "cName";

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AirportByCityNameHandler"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		System.out.println(":::::::::::AirportByCityNameHandler start ::::::::::: ");
		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();

		// Get the color slot from user input.
		Slot cityNameSlot = slots.get(CITYNAME_SLOT);

		String speechText = "", repromptText = "";
		boolean noAnswerProvided = false;
		// Check for favorite color and create output to user.

		// Store the user's favorite color in the Session and create response.
		String cityName = cityNameSlot.getValue();

		System.out.println("cityName :::::" + cityName);

		Database d = new Database();
		
		List ls = d.getAirportByCityName(cityName);
		String speech="";

		for (int i = 0; i < ls.size(); i++) {

			if (i == 0) {
				ManageAirportVO airportVO = (ManageAirportVO) ls.get(0);
				System.out.println(
						(i + 1) + ". Available Nearest Airport in " + airportVO.getManageCityVO().getCityName() + " " + "is" + " " + airportVO.getAirportDescription()
								+ " " + "and Type of Airport is" + " " + airportVO.getAirportType());
				
				speech=(i + 1) + ". " + airportVO.getManageCityVO().getCityName() + " " + "is" + " " + airportVO.getAirportDescription()
				+ " " + "and Type of Airport is" + " " + airportVO.getAirportType() +".";
				
			} else {
				ManageAirportVO airportVO = (ManageAirportVO) ls.get(i);
				System.out.println(
						", "+(i + 1) + ".  "+ airportVO.getManageCityVO().getCityName() + " " +"is" + " " + airportVO.getAirportDescription()
								+ " " + "and Type of Airport is" + " " + airportVO.getAirportType());
				
				speech=speech + (i + 1) + ". Available Nearest Airport in " + airportVO.getManageCityVO().getCityName() + " " + "is" + " " + airportVO.getAirportDescription()
				+ " " + "and Type of Airport is" + " " + airportVO.getAirportType();
			}

		}
		
		speechText = String.format("Available Nearest Airport in "+speech);

		System.out.println(":::::::::::AirportByCityNameHandler start ::::::::::: ");
		return input.getResponseBuilder().withSimpleCard("Airport", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(!noAnswerProvided).withShouldEndSession(false).build();
	}


}
