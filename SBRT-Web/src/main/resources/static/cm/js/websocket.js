var stompClient = null;

function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	}
	else {
		$("#conversation").hide();
	}
	$("#message").html("");
}

function connect() {
	var socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);
	
	stompClient.connect({}, onConnected, onError);

}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	
	setConnected(false);
	console.log("Disconnected");
}

function onConnected(frame) {
	
	setConnected(true);
	
	console.log('Connected: ' + frame);
	
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

}


function onError(error) {
	showMessage('Could not connect to WebSocket server. ' + error);
}


function onMessageReceived(payload) {
	
	var jsonObj = JSON.parse(payload.body);
	var content = jsonObj.content;
	
	showMessage(content);
}



function sendMessage() {
	
	var param = {
		content : $("#content").val()
	}
	
	stompClient.send("/app/sendMessage", {}, JSON.stringify(param));
}

function showMessage(message) {
	$("#message").append("<tr><td>" + message + "</td></tr>");
}


$(function () {
	$( "#connect" ).click(function() { connect(); });
	$( "#disconnect" ).click(function() { disconnect(); });
	
	$("form").on('submit', function (e) {
		e.preventDefault();
	});
	$( "#send" ).click(function() { sendMessage(); });

	connect();

});