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

package controller;

import data.model.DefaultUser;
import data.model.response.UsersResponse;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
public class UserSessionsController {

    private static final String USER_PREFIX = "User-";


    private Map<Session, String> userSessions = new ConcurrentHashMap<>();
    private int nextUserNumber = 1;

    public UserSessionsController() {

    }

    public Session getUserSession(String username) {
        return userSessions.entrySet()
                           .stream()
                           .filter(entry -> entry.getValue().equals(username))
                           .findFirst().orElse(null)
                           .getKey();
    }

    public void addUserSession(Session session) {
        String username = USER_PREFIX + nextUserNumber++;
        userSessions.put(session, username);
    }

    public void removeUserSession(Session session) {
        nextUserNumber--;
        userSessions.remove(session);
    }

    public Map<Session, String> getUserSessionsEntries() {
        return userSessions;
    }

    public Collection<Session> getSessions() {
        return userSessions.keySet();
    }

    public UsersResponse getUsers() {
        return new UsersResponse(userSessions.values()
                                             .stream()
                                             .map(DefaultUser::new)
                                             .collect(Collectors.toList())
        );
    }
}
