package com.virgilsecurity.e2ee.demo.server.component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.virgilsecurity.e2ee.demo.server.domain.UserInfo;

@Scope("singleton")
@Component
public class UserHolder {

	@Autowired
	private SimpMessagingTemplate template;

	private Set<UserInfo> activeUsers = Collections.synchronizedSet(new HashSet<>());

	public Set<UserInfo> getActiveUsers() {
		return Collections.unmodifiableSet(activeUsers);
	}

	public void addUser(UserInfo userInfo) {
		this.activeUsers.add(userInfo);
		this.template.convertAndSend("/topic/activeusers", activeUsers);
	}

	public void removeUser(UserInfo userInfo) {
		this.activeUsers.remove(userInfo);
		this.template.convertAndSend("/topic/activeusers", activeUsers);
	}

}
