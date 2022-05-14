package com.amazon.ask.helloworld.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

public class customIntentHandler implements RequestHandler{

	 @Override
	    public boolean canHandle(HandlerInput input) {
	        return input.matches(intentName("customIntent"));
	    }

	    @Override
	    public Optional<Response> handle(HandlerInput input) {
	        String speechText = "Hello world welcome to custom intent";
	       return input.getResponseBuilder()
	                .withSpeech(speechText)
	                .withSimpleCard("HelloWorld", speechText)
	                .build();
	    }

}
