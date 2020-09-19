package org.acme.quarkus.sample;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.reactivex.Flowable;

/**
 * A bean producing random prices every 5 seconds.
 * The prices are written to an AMQP queue (prices). The AMQP configuration is specified in the
 * application configuration.
 */
@ApplicationScoped
public class PriceGenerator {

    private Random random = new Random();
    
    private List<String> list = Arrays.asList("Apple", "Banana", "Grapes","Mango","Birne","Orange");

    @Outgoing("prices-topic")                        
    public Flowable<Price> generate() {               
        return Flowable.interval(5, TimeUnit.SECONDS)
                .map(tick -> {
                	int price = random.nextInt(100);
                	Price payload = new Price(list.get(random.nextInt(5)), price);
                	System.out.println("sent <"+payload.getLabel()+"> to topic prices");
                	return payload;
                });
    }

}