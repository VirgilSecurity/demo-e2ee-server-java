var stompClient = null;

function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	} else {
		$("#conversation").hide();
	}
}

function connect() {
	var socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/messages', function(message) {
			showMessage(JSON.parse(message.body));
		});
		stompClient.subscribe('/topic/activeusers', function(message) {
			showUsers(JSON.parse(message.body));
		});
		getUsers();
	});
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendMessage() {
	stompClient.send("/app/message", {}, JSON.stringify({
		'body' : $("#message").val()
	}));
}

function showMessage(message) {
	$("#messages").append(
			"<tr><td>" + message.author + "</td><td>" + message.body
					+ "</td></tr>");
}

function getUsers() {
	stompClient.send("/app/users", {}, "");
}

function showUsers(users) {
	var text = "";
	for (var i in users) {
		text = text + " " + users[i].email;
	}
	$("#activeusers").text(text);
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendMessage();
	});
});