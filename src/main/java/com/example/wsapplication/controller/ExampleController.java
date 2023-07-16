package com.example.wsapplication.controller;

import com.example.wsapplication.model.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    private static final Logger LOGGER = LogManager.getLogger(ExampleController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody Message message) {
        simpMessagingTemplate.convertAndSend("/topic/message", message);
        LOGGER.info("Message : " + message.getMessage());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SendTo("/topic/message")
    public Message broadcastMessage(@Payload Message message) {
        return message;
    }


}
