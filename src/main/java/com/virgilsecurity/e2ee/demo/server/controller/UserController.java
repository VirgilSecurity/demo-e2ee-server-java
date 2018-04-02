package com.virgilsecurity.e2ee.demo.server.controller;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.virgilsecurity.e2ee.demo.server.component.UserHolder;
import com.virgilsecurity.e2ee.demo.server.domain.UserInfo;

@Controller
public class UserController {

	@Autowired
	private UserHolder userHolder;

	@MessageMapping("/users")
	@SendTo("/topic/activeusers")
	public Collection<UserInfo> user(Principal principal) {

		return userHolder.getActiveUsers();
	}

}
