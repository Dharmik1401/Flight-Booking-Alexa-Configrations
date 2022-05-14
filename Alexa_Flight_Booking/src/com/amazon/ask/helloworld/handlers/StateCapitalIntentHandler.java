package com.amazon.ask.helloworld.handlers;


import static com.amazon.ask.request.Predicates.intentName;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

public class StateCapitalIntentHandler implements RequestHandler{

	@Override
	public boolean canHandle(HandlerInput input) {
		
		return input.matches(intentName("StateCapitalIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		
		Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        Map mp = new HashMap();
        mp.put("Gujarat","gandhinagar");
		mp.put("maharastra","mumbai");
		mp.put("mp","bhopal");
		mp.put("karnataka","banglore");
		
        Slot data = slots.get("stateName");
        String capital_name = (String) mp.get(data.getValue());
        
        String speechText = "Capital of "+data.getValue()+" is "+capital_name;
        return input.getResponseBuilder()
                .withSimpleCard("Capital", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
	}

	}


