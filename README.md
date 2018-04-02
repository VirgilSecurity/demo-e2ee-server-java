# e2ee-server-java
A simple java server application that uses in end-to-end encryption use-case.

# Prerequisites

* Java Development Kit (JDK) 7+
* Maven 3+

# Configuration

## Create Google application

Go to [Developer Console](https://console.developers.google.com)
Register oAuth 2.0 client

## Configure application
Define system variables
```
SECURITY_OAUTH2_CLIENT_CLIENTID=<your client identifier>
SECURITY_OAUTH2_CLIENT_CLIENTSECRET=<your client secret>
```

# Authentication

Use [oAuth 2.0](https://tools.ietf.org/html/rfc6749) to authenticate user. After login user session created. `JSESSIONID` cookie used as a session identifier.

# REST endpoints

## Current user details
**Request info**
```
HTTP Request method    GET
Request URL            /user
Authentication         Optional
```

# WebSocket
The endpoint for WebSocket connection is `/websocket`.

## Destinations

### Update active users list
Send message to server if you need to refresh users list manually.
```
/users
```

### Send a message
```
/message
```
Message sent by a client could be received by active clients only.

*Message body*
```json
{
  "body": "The text of a message"
}
```

## Topics

### Active users
```
/topic/activeusers
```
Server sends a list of active users automatically every time when client connects/disconnects to server. To receive this messages client should subscribed to the topic.

*Message body*
```json
[
  {
    "name" : "User1 name",
    "email": "User1 email"
  },
  {
    "name" : "User2 name",
    "email": "User2 email"
  }
  ...
]
```

### Receive a message
```
/topic/messages
```
Once client sends a message, this message will be received by all active clients including sender.

*Message body*
```json
{
  "author":"User1 name",
  "body":"The text of a message"
}
```
