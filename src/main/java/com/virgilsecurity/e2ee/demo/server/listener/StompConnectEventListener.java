package com.virgilsecurity.e2ee.demo.server.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import com.virgilsecurity.e2ee.demo.server.component.UserHolder;
import com.virgilsecurity.e2ee.demo.server.domain.UserInfo;
import com.virgilsecurity.e2ee.demo.server.utils.UserUtils;

@Component
public class StompConnectEventListener implements ApplicationListener<SessionConnectEvent> {

	private final Log logger = LogFactory.getLog(StompConnectEventListener.class);

	@Autowired
	private UserHolder userHolder;

	public void onApplicationEvent(SessionConnectEvent event) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());

		UserInfo userInfo = UserUtils.extractUserInfo(sha.getUser());
		logger.info("Connect event [sessionId: " + sha.getSessionId() + "; user: " + userInfo.getEmail() + " ]");

		userHolder.addUser(userInfo);
	}

}
