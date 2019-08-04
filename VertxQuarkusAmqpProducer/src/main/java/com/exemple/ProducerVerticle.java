package com.exemple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;

public class ProducerVerticle extends AbstractVerticle {

	// projet init
	// https://medium.com/@yazidaqel/quarkus-vertx-a-powerfull-combination-part-1-introduction-b039b911686

	// vertx rabbitmq client
	// https://github.com/vert-x3/vertx-rabbitmq-client/issues/30

	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerVerticle.class);

	@Override
	public void start() throws Exception {
		RabbitMQOptions mqconfig = new RabbitMQOptions();
		mqconfig.setUri("localhost");
		mqconfig.setPort(5672);
		mqconfig.setUser("quarkus");
		mqconfig.setPassword("quarkus");
		RabbitMQClient client = RabbitMQClient.create(vertx, mqconfig);
		
		System.out.println("client isConnected : "+client.isConnected());
		client.start(startResult -> {
			if (startResult.succeeded()) {
				System.out.println("client started!");
			} else {
				System.out.println("client failed to be started!");
				startResult.cause().printStackTrace();
			}
		});

		JsonObject config = new JsonObject();
		config.put("x-message-ttl", 10_000L);

//		System.out.println("queue declare");
//		client.queueDeclare("vertx-queue", true, false, true, config, queueResult -> {
//			if (queueResult.succeeded()) {
//				System.out.println("Queue declared!");
//			} else {
//				System.err.println("Queue failed to be declared!");
//				queueResult.cause().printStackTrace();
//			}
//		});

		JsonObject message = new JsonObject().put("body", "Hello RabbitMQ, from Vert.x !");
		client.basicPublish("", "vertx-queue", message, pubResult -> {
			if (pubResult.succeeded()) {
				System.out.println("Message published !");
			} else {
				pubResult.cause().printStackTrace();
			}
		});
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}
}