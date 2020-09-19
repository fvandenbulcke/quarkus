package org.acme.quarkus.sample;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.vertx.reactivex.core.eventbus.Message;

/**
 * A bean consuming data from the "prices" AMQP queue and applying some conversion.
 * The result is pushed to the "my-data-stream" stream which is an in-memory stream.
 */
@ApplicationScoped
public class PriceConverter {

    private static final double CONVERSION_RATE = 0.88;

//    @Incoming("prices")
//    @Outgoing("my-data-stream")                          
//    @Broadcast                                           
//    public double process(int priceInUsd) {
//        return priceInUsd * CONVERSION_RATE;
//    }
    
    // @Incoming("prices")
    public void consume(Message<Price> price) {
    	System.out.println(price.body().getLabel()+" price is "+price.body().getPrice());
    }

}