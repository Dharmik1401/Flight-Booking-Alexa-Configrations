/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package com.amazon.ask.helloworld;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.helloworld.handlers.AirportByCityNameHandler;
import com.amazon.ask.helloworld.handlers.AvailableFlightHandler;
import com.amazon.ask.helloworld.handlers.BookFlightByPlaneNumberHandler;
import com.amazon.ask.helloworld.handlers.CancelandStopIntentHandler;
import com.amazon.ask.helloworld.handlers.CityNameIntentHandler;
import com.amazon.ask.helloworld.handlers.EmailIntentHandler;
import com.amazon.ask.helloworld.handlers.HelloWorldIntentHandler;
import com.amazon.ask.helloworld.handlers.HelpIntentHandler;
import com.amazon.ask.helloworld.handlers.SessionEndedRequestHandler;
import com.amazon.ask.helloworld.handlers.StateCapitalIntentHandler;
import com.amazon.ask.helloworld.handlers.Weather;
import com.amazon.ask.helloworld.handlers.customIntentHandler;
import com.amazon.ask.helloworld.handlers.LaunchRequestHandler;
import com.amazon.ask.helloworld.handlers.MinPricingByScheduleHandler;
import com.amazon.ask.helloworld.handlers.FallbackIntentHandler;
import com.amazon.ask.helloworld.handlers.FastestFlightHandler;
import com.amazon.ask.helloworld.handlers.FlightByAirlineHandler;
import com.amazon.ask.helloworld.handlers.FlightByDayHandler;

public class HelloWorldStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new CancelandStopIntentHandler(),
                        new HelloWorldIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler(),
                        new FallbackIntentHandler(),
                        new HelloWorldIntentHandler(),
                        new customIntentHandler(),
                        new Weather(),
                        new CityNameIntentHandler(),
                        new AvailableFlightHandler(),
                        new FlightByAirlineHandler(),
                        new FastestFlightHandler(),
                        new MinPricingByScheduleHandler(),
                        new FlightByDayHandler(),
                        new AirportByCityNameHandler(),
                        new EmailIntentHandler(),
                        new BookFlightByPlaneNumberHandler(),
                        new StateCapitalIntentHandler())
                // Add your skill id below
                //.withSkillId("")
                .build();
    }

    public HelloWorldStreamHandler() {
        super(getSkill());
    }

}
