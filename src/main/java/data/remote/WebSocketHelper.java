/*
 * Copyright (c) 2015-2018, Virgil Security, Inc.
 *
 * Lead Maintainer: Virgil Security Inc. <support@virgilsecurity.com>
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     (1) Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *
 *     (2) Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *     (3) Neither the name of virgil nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package data.remote;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.Gson;
import controller.UserSessionsController;
import data.model.Message;
import io.javalin.Javalin;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * .._  _
 * .| || | _
 * -| || || |   Created by:
 * .| || || |-  Danylo Oliinyk
 * ..\_  || |   on
 * ....|  _/    4/6/18
 * ...-| | \    at Virgil Security
 * ....|_|-
 */
public final class WebSocketHelper {

    private static WebSocketHelper instance;

    private final Javalin javalin;
    private UserSessionsController userSessionsController;

    private WebSocketHelper() {
        javalin = Javalin.create()
                         .port(7070)
                         .ws("/chat", ws -> {
                             ws.onConnect(session -> {
                                 userSessionsController.addUserSession(session);
                                 broadcastUserListChanged(userSessionsController.getUserSessions().keySet(),
                                                          userSessionsController.getUserSessions().values());
                             });
                             ws.onClose((session, status, message) -> {
                                 userSessionsController.removeUserSession(session);
                                 broadcastUserListChanged(userSessionsController.getUserSessions().keySet(),
                                                          userSessionsController.getUserSessions().values());
                             });
                             ws.onMessage((session, messageRaw) -> {
                                 Message message = new Gson().fromJson(messageRaw, Message.class);
                                 sendDirectMessage(userSessionsController.getUserSession(message.getReceiver()),
                                                   message);
                             });
                         });


        userSessionsController = new UserSessionsController();
    }

    public static WebSocketHelper getInstance() {
        if (instance == null)
            instance = new WebSocketHelper();

        return instance;
    }

    public Javalin getJavalin() {
        return javalin;
    }

    public void start() {
        javalin.start();
    }

    private void sendDirectMessage(Session receiverSession, Message message) {
        try {
            receiverSession.getRemote()
                           .sendString(new Gson().toJson(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBroadcastMessage(final List<Session> sessions, final Message message) {
        sessions.stream()
                .filter(Session::isOpen)
                .forEach(session -> {
                    try {
                        session.getRemote().sendString(
                                new Gson().toJson(message)
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void broadcastUserListChanged(final Collection<Session> sessions, final Collection<String> messageBodies) {
        sessions.stream()
                .filter(Session::isOpen)
                .forEach(session -> {
                    try {
                        session.getRemote().sendString(
                                new YaGson().toJson(new ArrayList<>(messageBodies))
//                                "Hello!"
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
