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

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import controller.UserSessionsController;
import data.model.DefaultMessage;
import data.model.Message;
import data.model.NetworkErrorType;
import data.model.ResponseType;
import data.model.exception.InitializationException;
import data.model.request.GetTokenRequest;
import data.model.response.DefaultResponse;
import data.model.response.NetworkErrorResponse;
import data.model.response.UsersResponse;
import io.javalin.Javalin;
import org.eclipse.jetty.websocket.api.Session;
import util.SerializationUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
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

    @Inject @Named("clientId") String clientId;
    @Inject protected VirgilHelper virgilHelper;

    private Javalin javalin;
    private UserSessionsController userSessionsController;
    private boolean initialized;

    private WebSocketHelper() {
    }

    public static WebSocketHelper getInstance() {
        if (instance == null)
            instance = new WebSocketHelper();

        return instance;
    }

    public WebSocketHelper init() {

        javalin = Javalin.create().port(7070);

        initWebSocket();
        initTokenEndpoint();

        userSessionsController = new UserSessionsController();
        initialized = true;

        return instance;
    }

    public void start() {
        if (initialized)
            javalin.start();
        else
            throw new InitializationException("WebSocketHelper -> init() method must be called first");
    }


    private void initWebSocket() {
        javalin.ws("/chat", ws -> {
            ws.onConnect(session -> {
                userSessionsController.addUserSession(session);
                DefaultResponse<UsersResponse> response =
                        new DefaultResponse<>(ResponseType.USERS_LIST,
                                              new UsersResponse(userSessionsController.getUsers()));
                broadcastUserListChanged(userSessionsController.getSessions(), response);
            });
            ws.onClose((session, status, message) -> {
                userSessionsController.removeUserSession(session);
                DefaultResponse<UsersResponse> response =
                        new DefaultResponse<>(ResponseType.USERS_LIST,
                                              new UsersResponse(userSessionsController.getUsers()));
                broadcastUserListChanged(userSessionsController.getSessions(), response);
            });
            ws.onMessage((session, messageRaw) -> {
                Message message = SerializationUtils.fromJson(messageRaw, DefaultMessage.class);
                DefaultResponse<Message> response =
                        new DefaultResponse<>(ResponseType.MESSAGE, message);
                sendDirectMessage(userSessionsController.getUserSession(message.getReceiver()),
                                  response);
            });
        });
    }

    private void initTokenEndpoint() {
        javalin.post("/token", ctx -> {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
                                                                               new JacksonFactory())
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            GetTokenRequest tokenRequest;
            try {
                tokenRequest = ctx.bodyAsClass(GetTokenRequest.class);
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(400)
                   .result(SerializationUtils.toJson(
                           new NetworkErrorResponse(NetworkErrorType.GET_TOKEN_REQUEST)
                   ));
                return;
            }

            GoogleIdToken googleIdToken;
            try {
                googleIdToken = verifier.verify(tokenRequest.getGoogleToken());
            } catch (IllegalArgumentException e) {
                ctx.status(400)
                   .result(SerializationUtils.toJson(
                           new NetworkErrorResponse(NetworkErrorType.GOOGLE_TOKEN_VERIFICATION)
                   ));
                return;
            }

            ctx.status(200).result(virgilHelper.generateToken(googleIdToken.getPayload()
                                                                           .getEmail()
                                                                           .split("@")[0])
                                               .stringRepresentation());

        });
    }

    private void sendDirectMessage(Session receiverSession, DefaultResponse<Message> messageResponse) {
        try {
            receiverSession.getRemote()
                           .sendString(SerializationUtils.toJson(messageResponse));
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
                                SerializationUtils.toJson(message)
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void broadcastUserListChanged(final Collection<Session> sessions,
                                          final DefaultResponse<UsersResponse> usersResponse) {
        sessions.stream()
                .filter(Session::isOpen)
                .forEach(session -> {
                    try {
                        session.getRemote().sendString(
                                SerializationUtils.toJson(usersResponse)
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public Javalin getJavalin() {
        return javalin;
    }
}
