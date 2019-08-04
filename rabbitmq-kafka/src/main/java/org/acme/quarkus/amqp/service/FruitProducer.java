package org.acme.quarkus.amqp.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import org.acme.quarkus.amqp.dto.Fruit;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class FruitProducer {
	
    private Set<Fruit> fruits = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    
    public FruitProducer() {
        fruits.add(new Fruit("Apple", "Winter fruit"));
        fruits.add(new Fruit("Poire", "French fruit"));
	}
    
    public Set<Fruit> getAll() {
    	return fruits;
    }

    // @Outgoing("fruits")                        
    public Fruit produce() {               
        return new Fruit("Pineapple", "Tropical fruit");
    }
}
