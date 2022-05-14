package com.amazon.ask.helloworld.handlers;


import static com.amazon.ask.request.Predicates.intentName;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

public class CityNameIntentHandler implements RequestHandler{

	@Override
	public boolean canHandle(HandlerInput input) {
		
		return input.matches(intentName("CityNameIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {

		Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        
        /*Slot cityName = slots.get(COLOR_SLOT);*/
        String speechText;


		
		
		
		
		return null;
	}

}
