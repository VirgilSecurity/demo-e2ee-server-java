package com.virgilsecurity.e2ee.demo.server.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virgilsecurity.e2ee.demo.server.domain.UserInfo;
import com.virgilsecurity.e2ee.demo.server.utils.UserUtils;

@RestController
public class UserRestController {

	@RequestMapping("/user")
	public UserInfo user(Principal principal) {
		return UserUtils.extractUserInfo(principal);
	}

}
