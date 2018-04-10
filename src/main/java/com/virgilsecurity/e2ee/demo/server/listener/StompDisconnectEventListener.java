package com.virgilsecurity.e2ee.demo.server.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.virgilsecurity.e2ee.demo.server.component.UserHolder;
import com.virgilsecurity.e2ee.demo.server.domain.UserInfo;
import com.virgilsecurity.e2ee.demo.server.utils.UserUtils;

@Component
public class StompDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

	private final Log logger = LogFactory.getLog(StompDisconnectEventListener.class);

	@Autowired
	private UserHolder userHolder;

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());

		UserInfo userInfo = UserUtils.extractUserInfo(sha.getUser());
		logger.info("Disonnect event [sessionId: " + sha.getSessionId() + "; user: " + userInfo.getEmail() + " ]");

		userHolder.removeUser(userInfo);
	}
}
