package org.acme.quarkus.amqp.resouce;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.quarkus.amqp.dto.Fruit;
import org.acme.quarkus.amqp.service.FruitProducer;

@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {
	
    private Set<Fruit> fruits = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    @Inject
    FruitProducer fruitProducer;
    
    @GET
    public Set<Fruit> list() {
        return fruitProducer.getAll();
    }

    @POST
    public Set<Fruit> add(Fruit fruit) {
        fruits.add(fruit);
        return fruits;
    }

    @DELETE
    public Set<Fruit> delete(Fruit fruit) {
        fruits.remove(fruit);
        return fruits;
    }
}