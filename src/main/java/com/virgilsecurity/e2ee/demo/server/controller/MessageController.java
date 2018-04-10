package com.virgilsecurity.e2ee.demo.server.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.virgilsecurity.e2ee.demo.server.domain.TextMessage;

@Controller
public class MessageController {

	@MessageMapping("/message")
	@SendTo("/topic/messages")
	public TextMessage sendMessage(TextMessage message, Principal principal) {
		message.setAuthor(principal.getName());
		return message;
	}

}
