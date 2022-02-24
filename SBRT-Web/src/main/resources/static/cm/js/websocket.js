var stompClient = null;

/* var locations = {
		[
			{}
			,{}
			,{}
			,{}
			,{}
			
		]
		 
 }*/

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
	
	var attrId = jsonObj.ATTR_ID;
	var content = payload.body;
	
	if(attrId == 4014) { //4014: 실시간 차량 위치 정보
		//payload.body >>>> {"GPS_Y":36.484005,"ATTR_ID":4014,"DVC_ID":"DV00000002","GPS_X":127.25973,"VHC_ID":"VH00000002"}		
		
		//jsonObj.VHC_ID => 차량아이디
		//jsonObj.GPS_X => x좌표
		//jsonObj.GPS_Y; => y좌표
		
		//content = jsonObj.VHC_ID + "," + jsonObj.GPS_X + ", " + jsonObj.GPS_Y;
		
		
	} else if(attrId == 4015) { //4015: 정류장별 차량 도착 정보
		
		//payload.body >>>> {"ATTR_ID":4015,"LIST":[{"ROUT_ID":"RT00000080","REMAIN_TM":1364334,"REMAIN_STTN":2}],"STTN_ID":"ND00000017"}
		
		//jsonObj.STTN_ID => 정류장아이디
		//jsonObj.LIST => 도착정보 리스트
		// ㄴ ROUT_ID => 노선아이디
		// ㄴ REMAIN_TM => 남은시간(초)
		// ㄴ REMAIN_STTN => 몇 정류장 전

		
		
	} else if(attrId == 4012) { //4012: 운행이벤트
		
		//console.log(payload.body);
		
				
	} else if(attrId == 4020) { //4020: 디스패치
		
		//payload.body >>>> {MESSAGE=천천히가세요, DSPTCH_DIV=DP001, ATTR_ID=4020, DSPTCH_KIND=DK002, VHC_ID=VH00000002}
		
		//jsonObj.DSPTCH_DIV => 디스패치 구분코드 : DP001(일반메시지), DP002(운행중디스패치), DP003(정차중디스패치)
		//jsonObj.DSPTCH_KIND => 디스패치 종류(레벨)코드 : DK001(1단계), DK002(2단계), DK003(3단계)
		//jsonObj.MESSAGE => 디스패치 메시지

		
		
	}
	
	
	showMessage(content);
}

//setInterval(sendMessage, 1000);

function sendMessage() {
	
	//locations[i].x , y
	
	var param = {
		ATTR_ID : $("#attrId").val(),
		GPS_X : $("#gpsX").val(),
		GPS_Y : $("#gpsY").val(),
		VHC_ID : $("#vhcId").val(),
		DVC_ID : $("#dvcId").val(),
		DVC_COND : $("#dvcCond").val()
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