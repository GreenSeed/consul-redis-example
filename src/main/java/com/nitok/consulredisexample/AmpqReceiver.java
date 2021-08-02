package com.nitok.consulredisexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@RabbitListener(queues = "test-queue")
public class AmpqReceiver {
    private CountDownLatch latch = new CountDownLatch(1);

    Logger logger = LoggerFactory.getLogger(AmpqReceiver.class);


    @RabbitHandler
    public void listen(String in) {
        logger.warn("Received <" + in + ">");
        latch.countDown();
    }

    @RabbitHandler
    public void listenBytes(byte[] data) {
        logger.warn("Received <" + new String(data) + ">");
        latch.countDown();
    }
    public CountDownLatch getLatch() {
        return latch;
    }
}
