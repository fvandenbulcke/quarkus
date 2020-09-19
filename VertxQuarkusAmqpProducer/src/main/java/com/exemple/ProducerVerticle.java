package com.exemple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.amqp.AmqpClient;
import io.vertx.ext.amqp.AmqpClientOptions;
import io.vertx.ext.amqp.AmqpConnection;
import io.vertx.ext.amqp.AmqpMessage;
import io.vertx.ext.amqp.AmqpSender;

public class ProducerVerticle extends AbstractVerticle {

	// projet init
	// https://medium.com/@yazidaqel/quarkus-vertx-a-powerfull-combination-part-1-introduction-b039b911686

	// vertx rabbitmq client
	// https://github.com/vert-x3/vertx-rabbitmq-client/issues/30

	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerVerticle.class);
	private AmqpClient client;

	@Override
	public void start() throws Exception {

		AmqpClientOptions options = new AmqpClientOptions().setHost("localhost").setPort(5672).setUsername("rabbit")
				.setPassword("rabbit");
		// Create a client using its own internal Vert.x instance.
		// AmqpClient client = AmqpClient.create(options);

		// USe an explicit Vert.x instance.
		client = AmqpClient.create(vertx, options);

		client.connect(ar -> {
			if (ar.failed()) {
				LOGGER.info("Unable to connect to the broker");
			} else {
				LOGGER.info("Connection succeeded");
				AmqpConnection connection = ar.result();
				connection.createSender("my-queue", done -> {
					if (done.failed()) {
						LOGGER.info("Unable to create a sender");
					} else {
						LOGGER.info("Sender created");
						AmqpSender sender = done.result();
						vertx.setPeriodic(5000, x -> {
							LOGGER.info("Sending Hello to consumer");
							sender.send(AmqpMessage.create().withBody("Hello from Producer").build());
						});
					}
				});
			}
		});

		super.start();
	}

	@Override
	public void stop() throws Exception {
		client.close(x -> {
			if (x.succeeded()) {
				LOGGER.info("AmqpClient closed successfully");
			}
		});
		super.stop();
	}
}