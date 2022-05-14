package com.amazon.ask.helloworld.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.helloworld.DAO.Basemethods;
import com.amazon.ask.helloworld.DAO.Database;
import com.amazon.ask.helloworld.vo.ManageAirplaneVO;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

public class EmailIntentHandler implements RequestHandler {

	public static final String EMAIL_SLOT = "email";

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("EmailIntentHandler"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		System.out.println(":::::::::::EmailIntentHandler start ::::::::::: ");
		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();

		// Get the color slot from user input.
		Slot emailSlot = slots.get(EMAIL_SLOT);

		String speechText = "", repromptText = "";
		boolean noAnswerProvided = false;
		// Check for favorite color and create output to user.

		// Store the user's favorite color in the Session and create response.
		String email = emailSlot.getValue();

		/* planeNumber=planeNumber.replaceAll("[^a-zA-Z0-9]", ""); */

		System.out.println("email :::::" + email);

		String replacepemail = email.replaceAll(" ", "").replaceAll("dot", ".").replaceAll("at", "@").toLowerCase();
		System.out.println("replacepemail :: " + replacepemail);

		Map<String, Object> map = input.getAttributesManager().getSessionAttributes();
		System.out.println("Map :: " + map);

		String replaceplaneNumber = (String) map.get("airplainNumber");
		System.out.println("replaceplaneNumber : " + replaceplaneNumber);
		Database d = new Database();
		List ls = d.bookingByAirplaneNumber(replaceplaneNumber);
		String speech = "";
		int planId=0;
		ManageAirplaneVO airplaneVO = (ManageAirplaneVO) ls.get(0);

		System.out.println("Flight no" + " " + airplaneVO.getAirplaneNumber() + " " + "is succesfully booked "
				+ "and your flight details are as follows:" + "Flight no" + " " + airplaneVO.getAirplaneNumber()
				+ " " + "is for" + " " + airplaneVO.getAirplaneDescription() + " " + "and it is of" + " "
				+ airplaneVO.getManageAirlineVO().getAirlineName() + " " + "airline");
		speech = "Flight no" + " " + airplaneVO.getAirplaneNumber() + " " + "is succesfully booked "
				+ "and your flight details are as follows:" + "Flight no" + " " + airplaneVO.getAirplaneNumber()
				+ " " + "is for" + " " + airplaneVO.getAirplaneDescription() + " " + "and it is of" + " "
				+ airplaneVO.getManageAirlineVO().getAirlineName() + " " + "airline";
		planId = airplaneVO.getId();
		System.out.println("planId : "+planId);
		
		int loginId =d.checkEmail(replacepemail);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String date = format.format(new Date());
		if (loginId > 0 ) {
			System.out.println("loginId :"+loginId);
			d.saveBooking(loginId,date,planId);

			speechText = String.format(speech);
		} else {
			
			String password=Basemethods.generatePassword();
			Basemethods.sendMail(replacepemail, password);
			
			d.registerUser(replacepemail,password);
			
			int loginId1 =d.checkEmail(replacepemail);
			d.saveBooking(loginId1,date,planId);
			speechText = String.format(speech +". also we store your email address and send your password on your email. if you want to login use it.");
		}
		input.getAttributesManager().setSessionAttributes(map);

		System.out.println(":::::::::::EmailIntentHandler end ::::::::::: ");
		return input.getResponseBuilder().withSimpleCard("Book Flight", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(!noAnswerProvided).withShouldEndSession(false).build();
	}

}