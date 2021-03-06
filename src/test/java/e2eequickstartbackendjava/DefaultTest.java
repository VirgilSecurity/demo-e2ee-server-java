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

package e2eequickstartbackendjava;

import data.model.DefaultUser;
import data.model.User;
import data.model.response.UsersResponse;
import org.junit.Test;
import util.SerializationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class DefaultTest {

    @Test public void jsonSerialization() {
        Map<String, String> pm = new ConcurrentHashMap<>();
        pm.put("Lol", "M");
        pm.put("Lalka", "F");

        String serializedMap = SerializationUtils.toJson(new ArrayList<>(pm.values()));
        assertNotNull(serializedMap);

        System.out.println(serializedMap);

        assertEquals("[ \"F\", \"M\" ]", serializedMap);
    }

    @Test public void userResponseSerializationJackson() {
        List<User> users = new ArrayList<>();

        users.add(new DefaultUser("User-1"));
        users.add(new DefaultUser("User-2"));
        users.add(new DefaultUser("User-3"));
        users.add(new DefaultUser("User-4"));

        UsersResponse usersResponse = new UsersResponse(users);

        String serializedResponse = SerializationUtils.toJson(usersResponse);

        System.out.println(serializedResponse);

        assertEquals("{\n" +
                             "  \"users\" : [ {\n" +
                             "    \"name\" : \"User-1\"\n" +
                             "  }, {\n" +
                             "    \"name\" : \"User-2\"\n" +
                             "  }, {\n" +
                             "    \"name\" : \"User-3\"\n" +
                             "  }, {\n" +
                             "    \"name\" : \"User-4\"\n" +
                             "  } ]\n" +
                             "}",
                     serializedResponse);
    }
}
