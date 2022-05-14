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

public class MinPricingByScheduleHandler implements RequestHandler {

	public static final String TOCITY_SLOT = "tCity";
	public static final String FROMCITY_SLOT = "fCity";

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("MinPricingByScheduleHandler"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		System.out.println(":::::::::::MinPricingByScheduleHandler start ::::::::::: ");
		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();

		// Get the color slot from user input.
		Slot toCity = slots.get(TOCITY_SLOT);
		Slot fromCity = slots.get(FROMCITY_SLOT);

		String speechText = "", repromptText = "";
		boolean noAnswerProvided = false;
		// Check for favorite color and create output to user.

		// Store the user's favorite color in the Session and create response.
		String fromcity = fromCity.getValue();
		String tocity = toCity.getValue();

		System.out.println("fromCity :::::" + fromcity);
		System.out.println("toCity ::::: " + tocity);
		String speech="";
		Database d = new Database();
		int toCityId = d.getCityId(tocity);
		System.out.println("toCityId: " + toCityId);
		int fromCityId = d.getCityId(fromcity);
		System.out.println("fromCityId: " + fromCityId);

		List ls = d.getminpricingBySchedule(toCityId, fromCityId);

		ManageScheduleVO scheduleVO = (ManageScheduleVO) ls.get(0);
		System.out.println("Cheapest Flight From " + scheduleVO.getManageAirplaneVO().getAirplaneDescription()
				+ " and Flight Number is " + scheduleVO.getManageAirplaneVO().getAirplaneNumber()
				+ " price for trip is " + scheduleVO.getPricing() + " rupees ");
		speech="Cheapest Flight From " + scheduleVO.getManageAirplaneVO().getAirplaneDescription()
				+ " having Flight Number " + scheduleVO.getManageAirplaneVO().getAirplaneNumber()
				+ " and price for the trip is " + scheduleVO.getPricing() + " rupees ";

		speechText = String.format(speech
				+ ",. You can also find Flights of your choice by saying,  Give me avaliable fastest flights of '***'");

		System.out.println(":::::::::::FlightByAirlineHandler start ::::::::::: ");
		return input.getResponseBuilder().withSimpleCard("Minimum Pricing", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(!noAnswerProvided).withShouldEndSession(false).build();

	}
}