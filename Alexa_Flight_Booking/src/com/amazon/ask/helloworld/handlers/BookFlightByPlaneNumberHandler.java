package com.amazon.ask.helloworld.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.helloworld.DAO.Database;
import com.amazon.ask.helloworld.vo.ManageAirplaneVO;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

public class BookFlightByPlaneNumberHandler implements RequestHandler {

	public static final String PLANENUMBER_SLOT = "planeNumber";

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("BookFlightByPlaneNumberHandler"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		System.out.println(":::::::::::BookFlightByPlaneNumberHandler start ::::::::::: ");
		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();

		// Get the color slot from user input.
		Slot planeNumberSlot = slots.get(PLANENUMBER_SLOT);

		String speechText = "", repromptText = "";
		boolean noAnswerProvided = false;
		// Check for favorite color and create output to user.

		// Store the user's favorite color in the Session and create response.
		String planeNumber = planeNumberSlot.getValue();
		
		/*planeNumber=planeNumber.replaceAll("[^a-zA-Z0-9]", "");*/

		System.out.println("planeNumber :::::" + planeNumber);
		
		String replaceplaneNumber = planeNumber.replaceAll(" ", "").toUpperCase().trim();
		System.out.println("replaceplaneNumber :: "+ replaceplaneNumber);
		Database d = new Database();
		List ls = d.bookingByAirplaneNumber(replaceplaneNumber);
		String speech="";
		Map map = new HashMap();
		
		if(ls.size()>0){
			ManageAirplaneVO airplaneVO = (ManageAirplaneVO) ls.get(0);

			System.out.println("Flight no" + " " + airplaneVO.getAirplaneNumber() + " "
					+ "is succesfully booked " + "and your flight details are as follows:" + "Flight no" + " "
					+ airplaneVO.getAirplaneNumber() + " " + "is for" + " " + airplaneVO.getAirplaneDescription()
					+ " " + "and it is of" + " " + airplaneVO.getManageAirlineVO().getAirlineName() + " "
					+ "airline");
			speech="Flight no" + " " + airplaneVO.getAirplaneNumber() + " "
					+ "is succesfully booked " + "and your flight details are as follows:" + "Flight no" + " "
					+ airplaneVO.getAirplaneNumber() + " " + "is for" + " " + airplaneVO.getAirplaneDescription()
					+ " " + "and it is of" + " " + airplaneVO.getManageAirlineVO().getAirlineName() + " "
					+ "airline";
		map.put("airplainNumber", replaceplaneNumber);
		System.out.println("map :: "+map);

	speechText = String.format("To book your flight please give your email address first by saying, My email id is 'abc@gmail.com'");
		}else{
			speechText = String.format("Sorry, can't found any record, Try again.");
		}
		input.getAttributesManager().setSessionAttributes(map);		

		System.out.println(":::::::::::BookFlightByPlaneNumberHandler end ::::::::::: ");
		return input.getResponseBuilder().withSimpleCard("Book Flight", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(!noAnswerProvided).withShouldEndSession(false).build();
	}

}
