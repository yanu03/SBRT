	var dashboard = {
		ROUT_INFO : [],
		ROUT_STTN_INFO : [],
		ROUT_CROSS_INFO : [],
		CUR_WAY : 'WD002',
		CUR_CRS_NODE : [],
		CUR_DISPATCH_STATE : 0,
    curTimeOut : 0,
    timer : null,
    timerOut : null,
	};
	
	dashboard.init = function() {
		dashboard.ROUT_INFO['WD001'] =[
			{NODE_ID : "293053009", NODE_TYPE:"NT002", NODE_NM : "한솔동", GPS_X : "127.261783", GPS_Y : "36.480915", LOC : 0 }
			,{NODE_ID : "CR00000003", NODE_TYPE:"NT001", NODE_NM : "스타벅스세종첫마을점", GPS_X : "127.261989", GPS_Y : "36.480653", LOC : 8 }
			,{NODE_ID : "CR00000004", NODE_TYPE:"NT001", NODE_NM : "첫마을교차로", GPS_X : "127.263058", GPS_Y : "36.479227", LOC : 16 }
			,{NODE_ID : "293064221", NODE_TYPE:"NT002", NODE_NM : "시외버스터미널", GPS_X : "127.273163", GPS_Y : "36.470695", LOC : 25 }
			,{NODE_ID : "CR00000051", NODE_TYPE:"NT001", NODE_NM : "시외터미널(교차로)", GPS_X : "127.273462", GPS_Y : "36.470746", LOC : 33 }
			,{NODE_ID : "CR00000052", NODE_TYPE:"NT001", NODE_NM : "대평동(교차로)", GPS_X : "127.276334", GPS_Y : "36.472089", LOC : 42 }
			,{NODE_ID : "293064252", NODE_TYPE:"NT002", NODE_NM : "대평동", GPS_X : "127.276449", GPS_Y : "36.472161", LOC : 50 }
			,{NODE_ID : "293064206", NODE_TYPE:"NT002", NODE_NM : "보람동.대평동", GPS_X : "127.280883", GPS_Y : "36.475041", LOC : 75 }
			,{NODE_ID : "CR00000008", NODE_TYPE:"NT001", NODE_NM : "행정중심3-2생활", GPS_X : "127.281208", GPS_Y : "36.475254", LOC : 81 }
			,{NODE_ID : "CR00000009", NODE_TYPE:"NT001", NODE_NM : "여울초교앞", GPS_X : "127.283628", GPS_Y : "36.476434", LOC : 88 }
			,{NODE_ID : "CR00000053", NODE_TYPE:"NT001", NODE_NM : "보람고앞", GPS_X : "127.285318", GPS_Y : "36.47713", LOC : 94 }
			,{NODE_ID : "293064210", NODE_TYPE:"NT002", NODE_NM : "세종시청", GPS_X : "127.288182", GPS_Y : "36.478342", LOC : 100 }
      ,{NODE_ID : "CR00000010", NODE_TYPE:"NT001", NODE_NM : "세종시청정류장", GPS_X : "127.288182", GPS_Y : "36.478342", LOC : -1 }
		];
		
		dashboard.ROUT_INFO['WD002'] =[
			{NODE_ID : "293064211", NODE_TYPE:"NT002", NODE_NM : "세종시청", GPS_X : "127.288758", GPS_Y : "36.478667", LOC : 0}
			,{NODE_ID : "CR00000010", NODE_TYPE:"NT001", NODE_NM : "세종시청", GPS_X : "127.288467", GPS_Y : "36.47854" , LOC : 7 }
			,{NODE_ID : "CR00000053", NODE_TYPE:"NT001", NODE_NM : "보람고앞", GPS_X : "127.285276", GPS_Y : "36.477173" , LOC : 13 }
			,{NODE_ID : "CR00000009", NODE_TYPE:"NT001", NODE_NM : "여울초교앞", GPS_X : "127.283617", GPS_Y : "36.476467" , LOC : 19 }
			,{NODE_ID : "293064209", NODE_TYPE:"NT002", NODE_NM : "보람동.대평동", GPS_X : "127.281446", GPS_Y : "36.47547" , LOC : 25 }
			,{NODE_ID : "CR00000008", NODE_TYPE:"NT001", NODE_NM : "행정중심3-2생활", GPS_X : "127.281174", GPS_Y : "36.475304" , LOC : 33 }
			,{NODE_ID : "CR00000052", NODE_TYPE:"NT001", NODE_NM : "대평동(지하)", GPS_X : "127.276335", GPS_Y : "36.472175" , LOC : 41 }
			,{NODE_ID : "293064251", NODE_TYPE:"NT002", NODE_NM : "대평동", GPS_X : "127.276212", GPS_Y : "36.472087" , LOC : 50 }	
			,{NODE_ID : "CR00000051", NODE_TYPE:"NT001", NODE_NM : "시외터미널(교차로)", GPS_X : "127.273426", GPS_Y : "36.470812" , LOC : 62 }
			,{NODE_ID : "293064220", NODE_TYPE:"NT002", NODE_NM : "시외버스터미널", GPS_X : "127.273219", GPS_Y : "36.470781" , LOC : 75 }
			,{NODE_ID : "CR00000004", NODE_TYPE:"NT001", NODE_NM : "첫마을교차로", GPS_X : "127.263209", GPS_Y : "36.479222" , LOC : 87 }
			,{NODE_ID : "293053011", NODE_TYPE:"NT002", NODE_NM : "한솔동", GPS_X : "127.262287", GPS_Y : "36.480373" , LOC : 100 }
      ,{NODE_ID : "CR00000003", NODE_TYPE:"NT001", NODE_NM : "스타벅스첫마을점", GPS_X : "127.288182", GPS_Y : "36.478342", LOC : -1 }
		];
		
		dashboard.ROUT_STTN_INFO['WD001'] =[
			{NODE_ID : "293053009", NODE_TYPE:"NT002", NODE_NM : "한솔동", GPS_X : "127.261783", GPS_Y : "36.480915" }
			,{NODE_ID : "293064221", NODE_TYPE:"NT002", NODE_NM : "시외버스터미널", GPS_X : "127.273163", GPS_Y : "36.470695" }
			,{NODE_ID : "293064252", NODE_TYPE:"NT002", NODE_NM : "대평동", GPS_X : "127.276449", GPS_Y : "36.472161" }
			,{NODE_ID : "293064206", NODE_TYPE:"NT002", NODE_NM : "보람동.대평동", GPS_X : "127.280883", GPS_Y : "36.475041" }
			,{NODE_ID : "293064210", NODE_TYPE:"NT002", NODE_NM : "세종시청", GPS_X : "127.288182", GPS_Y : "36.478342" }
		];
		
		dashboard.ROUT_STTN_INFO['WD002'] =[
			{NODE_ID : "293064211", NODE_TYPE:"NT002", NODE_NM : "세종시청", GPS_X : "127.288758", GPS_Y : "36.478667" }
			,{NODE_ID : "293064209", NODE_TYPE:"NT002", NODE_NM : "보람동.대평동", GPS_X : "127.281446", GPS_Y : "36.47547" }
			,{NODE_ID : "293064251", NODE_TYPE:"NT002", NODE_NM : "대평동", GPS_X : "127.276212", GPS_Y : "36.472087" }	
			,{NODE_ID : "293064220", NODE_TYPE:"NT002", NODE_NM : "시외버스터미널", GPS_X : "127.273219", GPS_Y : "36.470781" }
			,{NODE_ID : "293053011", NODE_TYPE:"NT002", NODE_NM : "한솔동", GPS_X : "127.262287", GPS_Y : "36.480373" }
		];
		
		dashboard.ROUT_CROSS_INFO['WD001'] =[
			{NODE_ID : "CR00000003", NODE_TYPE:"NT001", NODE_NM : "스타벅스세종첫마을점", GPS_X : "127.261989", GPS_Y : "36.480653", SIGNAL:"1,2", CUR_SIGNAL:"1"}
			,{NODE_ID : "CR00000004", NODE_TYPE:"NT001", NODE_NM : "첫마을교차로", GPS_X : "127.263058", GPS_Y : "36.479227", SIGNAL:"1"}
			,{NODE_ID : "CR00000051", NODE_TYPE:"NT001", NODE_NM : "시외터미널(교차로)", GPS_X : "127.273462", GPS_Y : "36.470746", SIGNAL:"1,2,4", CUR_SIGNAL:"1"}
			,{NODE_ID : "CR00000052", NODE_TYPE:"NT001", NODE_NM : "대평동(지하)", GPS_X : "127.276334", GPS_Y : "36.472089", SIGNAL:"1", CUR_SIGNAL:"1"}
			,{NODE_ID : "CR00000008", NODE_TYPE:"NT001", NODE_NM : "행정중심3-2생활", GPS_X : "127.281208", GPS_Y : "36.475254", SIGNAL:"1,2", CUR_SIGNAL:"1"}
			,{NODE_ID : "CR00000009", NODE_TYPE:"NT001", NODE_NM : "여울초교앞", GPS_X : "127.283628", GPS_Y : "36.476434", SIGNAL:"1", CUR_SIGNAL:"1"}
			,{NODE_ID : "CR00000053", NODE_TYPE:"NT001", NODE_NM : "보람고앞", GPS_X : "127.285318", GPS_Y : "36.47713", SIGNAL:"1", CUR_SIGNAL:"1"}
		];
		
		dashboard.ROUT_CROSS_INFO['WD002'] =[
			{NODE_ID : "CR00000010", NODE_TYPE:"NT001", NODE_NM : "세종시청", GPS_X : "127.288467", GPS_Y : "36.47854", SIGNAL:"1", CUR_SIGNAL:"1"}
			,{NODE_ID : "CR00000053", NODE_TYPE:"NT001", NODE_NM : "보람고앞", GPS_X : "127.285276", GPS_Y : "36.477173", SIGNAL:"1", CUR_SIGNAL:"1"}
			,{NODE_ID : "CR00000009", NODE_TYPE:"NT001", NODE_NM : "여울초교앞", GPS_X : "127.283617", GPS_Y : "36.476467", SIGNAL:"1", CUR_SIGNAL:"1"}
			,{NODE_ID : "CR00000008", NODE_TYPE:"NT001", NODE_NM : "행정중심3-2생활", GPS_X : "127.281174", GPS_Y : "36.475304", SIGNAL:"1,2", CUR_SIGNAL:"1"}
			,{NODE_ID : "CR00000052", NODE_TYPE:"NT001", NODE_NM : "대평동(지하)", GPS_X : "127.276335", GPS_Y : "36.472175", SIGNAL:"1", CUR_SIGNAL:"1"}
			,{NODE_ID : "CR00000051", NODE_TYPE:"NT001", NODE_NM : "시외터미널(교차로)", GPS_X : "127.273426", GPS_Y : "36.470812", SIGNAL:"1,4", CUR_SIGNAL:"1"}
			,{NODE_ID : "CR00000004", NODE_TYPE:"NT001", NODE_NM : "첫마을교차로", GPS_X : "127.263209", GPS_Y : "36.479222", SIGNAL:"1", CUR_SIGNAL:"1"}
		];
		
		dashboard.connect();
		dashboard.routDraw();
   dashboard.dispatchDraw(0, null,null);
   
//dashboard.test();
	};

var count =0;
dashboard.test = function() {
    if(count>80&&count<100){
      dashboard.dispatch(4, 1,null);
      }
    count++;
    if(count>100)count = 0;
         	setTimeout(function() {
      dashboard.test();
  			},1000);
};

	dashboard.routDraw = function() {
		$("#dashboard_list").empty();
		for (var i = 0; i < dashboard.ROUT_STTN_INFO[dashboard.CUR_WAY].length; i++) {
			var nodeInfo = dashboard.ROUT_STTN_INFO[dashboard.CUR_WAY][i];
			var nodeDisp = "<li><i class='station ir_pm'>정거장아이콘</i><span>"
					+ nodeInfo.NODE_NM + "</span></li>";
			$("#dashboard_list").append(nodeDisp);
		}
	};

	dashboard.routShow = function() {
		if (dashboard.CUR_WAY == 'WD001') {
			$("#dashboard_list").css("display", "block");
		} else {
			$("#dashboard_list").css("display", "none");
		}
	};
	

	dashboard.busDraw = function(node_id) {
		$("#dashboard_bus").empty();
		for (var i = 0; i < dashboard.ROUT_INFO[dashboard.CUR_WAY].length; i++) {
			var nodeInfo = dashboard.ROUT_INFO[dashboard.CUR_WAY][i];
			if (nodeInfo.NODE_ID == node_id&&nodeInfo.LOC!=-1) {
				var busDisp = "<i class='bus ir_pm' style='left:" + nodeInfo.LOC
						+ "%'>버스</i>";
				$("#dashboard_bus").append(busDisp)
				break;
			}
		}
	};
	
	dashboard.dispatchDraw = function(dispatchState, type, value) {
		if(dispatchState==0){
			var message = util.MSG.DISPATCH_MSG_NORMAL;				
			$("#priority_signal").text(message)
			
			$(".situation").removeClass("blue");
			$(".situation").removeClass("orange");
			$(".situation").addClass("green");
		}
		else if(dispatchState==1){
			/*if(value >= 60) {
				min = parseInt(value/60) + "분 ";
			}
			sec = value%60 + "초 ";

			var message = "정류장 정차 : " + min + sec;
			$("#priority_signal").text(message)*/

			$(".situation").removeClass("green");
			$(".situation").removeClass("orange");
			$(".situation").addClass("blue");
      dashboard.curTimeOut = value;
      dashboard.dispatchTimerCb (dispatchState);
		}
		else if(dispatchState==2){
      var min = 0;
      var sec = 0;
			if(value >= 60) {
				min = parseInt(value/60) + "분 ";
			}
			sec = value%60 + "초 ";
			var message = "";
			if(type=='DK002'){
       if(min==0)message = sec + " 늦음";
       else message = min + sec + " 늦음";
			}
			else if(type=='DK003'){
        if(min==0)message = sec + " 빠름";
				else message = min + sec + " 빠름";
			}
			
			$("#priority_signal").text(message)

			$(".situation").removeClass("green");
			$(".situation").removeClass("orange");
			$(".situation").addClass("blue");
       dashboard.curTimeOut = 5;
       dashboard.dispatchTimeOutCb (dispatchState);
		}
		else if(dispatchState==3){
			/*var message = "정류장제어 운영 : "+jsonObj.LIST[0].STOP_SEC;				
			$("#priority_signal").text(message)*/

			$(".situation").removeClass("green");
			$(".situation").removeClass("blue");
			$(".situation").addClass("orange");
      dashboard.curTimeOut = jsonObj.LIST[0].STOP_SEC;
      dashboard.dispatchTimerCb (dispatchState);
		}
		else if(dispatchState==4){
			if(type=='ST001'){
				var message = "교차로제어 운영 : 조기녹색";				
				$("#priority_signal").text(message)
				$(".situation").removeClass("green");
				$(".situation").removeClass("blue");
				$(".situation").addClass("orange");
        dashboard.curTimeOut = 6;
        dashboard.dispatchTimeOutCb (dispatchState);
			}
			else{
				var message = "교차로제어 운영 : 녹색연장";				
				$("#priority_signal").text(message)
				$(".situation").removeClass("green");
				$(".situation").removeClass("blue");
				$(".situation").addClass("orange");
        dashboard.curTimeOut = 6;
        dashboard.dispatchTimeOutCb (dispatchState);
			}
		}
	};
	
  dashboard.dispatchTimerCb = function(dispatchState){
    if(dashboard.CUR_DISPATCH_STATE =! dispatchState)return;
		if(dashboard.curTimeOut==0){
			dashboard.dispatchDraw(0, null,null);
		}
		else{
      if(dispatchState==1){
           var min = 0;
           var sec = 0;
           	var message = "";
    			if(dashboard.curTimeOut >= 60) {
    				min = parseInt(dashboard.curTimeOut/60) + "분 ";
    			}
    			sec = dashboard.curTimeOut%60 + "초 ";
          if(min!=0){
    			  message = "정류장 정차 : " + min + sec;
          }
          else{
            message = "정류장 정차 : " + sec;
          }
    			$("#priority_signal").text(message)
      }
      else  if(dispatchState==3){
    	     var message = "정류장제어 운영 : "+dashboard.curTimeOut;				
			    $("#priority_signal").text(message)
      }
			dashboard.curTimeOut--;
			dashboard.timer = setTimeout(function() {
				dashboard.dispatchTimerCb(dispatchState);	
			},1000);	
		}
	} 
  
	dashboard.dispatchTimeOutCb = function(dispatchState){
    if(dashboard.CUR_DISPATCH_STATE !== dispatchState)return;
		if(dashboard.curTimeOut==0){
			dashboard.dispatchDraw(0, null,null);
		}
		else{
			dashboard.curTimeOut--;
			dashboard.timerOut = setTimeout(function() {
				dashboard.dispatchTimerCb(dispatchState);	
			},1000);	
		}
	} 


 
	dashboard.dispatch = function(dispatchState, type, value) {
  //if(dashboard.curTimeOut>0)return;
		if(dispatchState < dashboard.CUR_DISPATCH_STATE)return;
    if(dashboard.timer != null){
      clearTimeout(dashboard.timer);
      dashboard.timer = null;
    }
    if(dashboard.timerOut != null){
      clearTimeout(dashboard.timerOut);
      dashboard.timerOut = null;
    }
		dashboard.CUR_DISPATCH_STATE = dispatchState;
		dashboard.dispatchDraw(dispatchState, type, value);
	};
	
	

	dashboard.searchNodeId = function(node_id) {
		for (var i = 0; i < dashboard.ROUT_INFO[dashboard.CUR_WAY].length; i++) {
			var nodeInfo = dashboard.ROUT_INFO[dashboard.CUR_WAY][i];
			if (nodeInfo.NODE_ID == node_id) {
				return nodeInfo;
			}
		}
		return null;
	};

	dashboard.nextNodeId = function(node_id, nodeType) {
		var curIdx = -1;
		for (var i = 0; i < dashboard.ROUT_INFO[dashboard.CUR_WAY].length; i++) {
			var nodeInfo = dashboard.ROUT_INFO[dashboard.CUR_WAY][i];
			if (nodeInfo.NODE_ID == node_id) {
				curIdx = i;
			}
			if (curIdx != -1 && i > curIdx && nodeType == nodeInfo.NODE_TYPE) {
				return nodeInfo;
			}
		}
		return null;
	};

	dashboard.searchCrsId = function(node_id) {
		for (var i = 0; i < dashboard.ROUT_CROSS_INFO[dashboard.CUR_WAY].length; i++) {
			var nodeInfo = dashboard.ROUT_CROSS_INFO[dashboard.CUR_WAY][i];
			if (nodeInfo.NODE_ID == node_id) {
				return nodeInfo;
			}
		}
		return null;
	};

	dashboard.setCrsId = function(node_id, cur_signal) {
		for (var i = 0; i < dashboard.ROUT_CROSS_INFO[dashboard.CUR_WAY].length; i++) {
      var nodeInfo = dashboard.ROUT_CROSS_INFO[dashboard.CUR_WAY][i];
			if (nodeInfo.NODE_ID == node_id) {
				dashboard.ROUT_CROSS_INFO[dashboard.CUR_WAY][i].CUR_SIGNAL = cur_signal;
				return;
			}
		}
		return;
	};
  
	dashboard.connect = function() {
		var socket = new SockJS('/websocket');
		stompClient = Stomp.over(socket);
		
		stompClient.connect({}, dashboard.onConnected, dashboard.onError);
	};
	
	dashboard.onConnected = function (frame) {
		console.log('onConnected: ' + frame);
		stompClient.subscribe('/topic/public', dashboard.onMessageReceived);
	};
	
	dashboard.onError = function (error) {
		//alert('Could not connect to WebSocket server. ' + error);
		setTimeout(function() {
			dashboard.connect();
		},1000);
	};	
	
	dashboard.onMessageReceived = function(payload) {
		
		
		var jsonObj = JSON.parse(payload.body);
	
		
		var attrId = jsonObj.ATTR_ID;
		var dataList = jsonObj.LIST;
		
		//GPS값 튈 경우 아무 동작도 안되게 처리
	
		if(attrId == util.ATTR_ID.BUS_ARRIVAL_INFO){
		
		}
		//4012: 운행이벤트
		else if(attrId == util.ATTR_ID.BUS_OPER_EVENT) {
      if(jsonObj.VHC_NO!=="우진76자5876")return;
       //  if(jsonObj.VHC_NO!=="경기76자5587")return;
       //if(jsonObj.VHC_NO!=="세종70자1509")return;
		//console.log(payload.body);
			if (typeof(jsonObj.GPS_X) == "undefined" || typeof(jsonObj.GPS_Y) == "undefined" || jsonObj.GPS_X < 120 || jsonObj.GPS_Y > 130 ||jsonObj.CUR_NODE_ID==null) {
				return;
			}
			if (dashboard.CUR_WAY != jsonObj.WAY_DIV) {
				dashboard.CUR_WAY = jsonObj.WAY_DIV;
				dashboard.routDraw();
			}
	
			if (jsonObj.CUR_STTN_CRS_ID != null) {
				dashboard.busDraw(jsonObj.CUR_STTN_CRS_ID);
			}
	
			{	
				var nodeCrsInfo = dashboard.nextNodeId(jsonObj.CUR_CRS_ID, "NT001");
	
				if (nodeCrsInfo != null) {
          if(nodeCrsInfo.LOC==-1){
             	dashboard.CUR_CRS_NODE = null;
              	$("#crs_nm").text('-');
          }
          else{
  					var nodeInfo = dashboard.searchCrsId(nodeCrsInfo.NODE_ID);
	
  					if (nodeInfo != null) {
  						dashboard.CUR_CRS_NODE = nodeInfo;
  						$("#crs_nm").text(nodeInfo.NODE_NM);
  						if (nodeInfo.SIGNAL.indexOf(nodeInfo.CUR_SIGNAL) !== -1) {
  							$(".ir_pm.red-light").removeClass("on");
  							$(".ir_pm.green-light").addClass("on");
  						} else {
  							$(".ir_pm.green-light").removeClass("on");
  							$(".ir_pm.red-light").addClass("on");
  						}
            }
					} 
          /*else {
						$("#crs_nm").text("-");
						$(".ir_pm.green-light").removeClass("on");
						$(".ir_pm.red-light").addClass("on");
					}*/
				}
			}
	
			if (jsonObj.CUR_CRS_ID != null) {
				var nodeInfo = dashboard.searchNodeId(jsonObj.CUR_CRS_ID);
				if (nodeInfo != null) {
					$("#cur_node").text(nodeInfo.NODE_NM);
				} else if (jsonObj.CUR_NODE_NM != null) {
					jsonObj.CUR_NODE_NM = jsonObj.CUR_NODE_NM.replace('세종고속시외버스터미널', '시외버스터미널')
					$("#cur_node").text(jsonObj.CUR_NODE_NM);
				}
			} else {
				jsonObj.CUR_NODE_NM = jsonObj.CUR_NODE_NM.replace('세종고속시외버스터미널',
						'시외버스터미널')
				$("#cur_node").text(jsonObj.CUR_NODE_NM);
			}
			
			{
				var nodeInfo = dashboard.nextNodeId(jsonObj.CUR_CRS_ID, "NT001");
				if (nodeInfo != null) {
					$("#next_node").text(nodeInfo.NODE_NM);
				} else if (jsonObj.NEXT_NODE_NM != null) {
					if (jsonObj.NEXT_NODE_NM.indexOf("세종시청") !== -1) {
						jsonObj.NEXT_NODE_NM = "세종시청";
					}
					if (jsonObj.NEXT_NODE_NM.indexOf("세종고속시외버스터미널") !== -1) {
						jsonObj.NEXT_NODE_NM = "시외버스터미널";
					}
					$("#next_node").text(jsonObj.NEXT_NODE_NM);
				}
			}
	
			if (jsonObj.ROUT_NM != null && jsonObj.COR_NM != null) {
				jsonObj.COR_NM = jsonObj.COR_NM.replace('B0 하행(청사방면)', '');
				jsonObj.COR_NM = jsonObj.COR_NM.replace('B0 상행(시청방면)', '');
				//$("#rout").text(jsonObj.COR_NM + "-" + jsonObj.ROUT_NM);
        $("#rout").text(jsonObj.ROUT_NM);
			} else if (jsonObj.ROUT_NM != null) {
				$("#rout").text(jsonObj.ROUT_NM);
			}
	
		} 
		// attrId = 4014 : 실시간 차량 위치 정보
		else if(attrId == util.ATTR_ID.BUS_INFO){

		}
		else if(attrId == util.ATTR_ID.DISPATCH) { // 4020: 디스패치
        if(jsonObj.VHC_NO!=="우진76자5876")return;
       //  if(jsonObj.VHC_NO!=="경기76자5587")return;
      //if(jsonObj.VHC_NO!=="세종70자1509")return;
			try { 
				var timeResult = util.getToday();
				
				var dsptchDiv = jsonObj.DSPTCH_DIV;
				var dsptchConts = jsonObj.MESSAGE.split('｜')[0];
				var dsptchKind = jsonObj.DSPTCH_KIND;
				var contsResult = "";
				var min = "";
				var sec = "";
				var dsptchMessage = "";
				
				if(dsptchConts.split(',').length>0){
					dsptchMessage = dsptchConts.split(',')[0];
				}
				else{
					dsptchMessage = dsptchConts;
				}
				//디스패치가 일반메시지가 아닐경우	
				if(parseInt(dsptchMessage) != "undefined" && dsptchDiv != "DP001"&&parseInt(dsptchMessage) != 0) {

					//운행중 디스패치일 경우 
					if(dsptchDiv == "DP002") {
						if(dsptchKind == "DK001") {
							
						}
						else if(dsptchKind == "DK002") {
							dashboard.dispatch(2, dsptchKind, Math.abs(parseInt(dsptchMessage)));
						}
						else if(dsptchKind == "DK003") {
							dashboard.dispatch(2, dsptchKind, Math.abs(parseInt(dsptchMessage))); 
						}
					}
					//정차중 디스패치일 경우
					else if(dsptchDiv == "DP003") {
						dashboard.dispatch(1, null, Math.abs(parseInt(dsptchMessage)));
					}	
					
				} 
				//디스패치 메시지가 일반메시지일 경우
				else if(parseInt(dsptchConts) == "undefined" || dsptchDiv == "DP001") {	
				}
			} catch (e) {
				console.log("[scwin.onMessageReceived] Exception :: " + e.message);		
			}
			console.log("contsResult= " + contsResult);
			
		}
		//신호등 현시 수신
		else if (attrId == util.ATTR_ID.TRAFFIC_LIGHT_STATUS_RESPONSE) {
			var phaseNo = dataList[0].PHASE_NO.toString();
 			var crsId = dataList[0].CRS_ID;
 			dashboard.setCrsId(crsId, phaseNo);
 			nodeInfo = dashboard.CUR_CRS_NODE;
 			
	        if((typeof nodeInfo.NODE_ID !== "undefined") && (nodeInfo.NODE_ID !== null)
	        		&&(nodeInfo.NODE_ID==crsId)){
	          if(nodeInfo.SIGNAL.indexOf(nodeInfo.CUR_SIGNAL) !== -1){
	          	$(".ir_pm.red-light").removeClass("on");
	          	$(".ir_pm.green-light").addClass("on");
	          }
	      		else {
	  		    	$(".ir_pm.green-light").removeClass("on");
	          	$(".ir_pm.red-light").addClass("on");
	          }
	        }
	        /*else{
	        	$("#crs_nm").text("-");
	        	$(".ir_pm.green-light").removeClass("on");
	          	$(".ir_pm.red-light").addClass("on");
	        }*/
	        
		}
		else if (attrId == util.ATTR_ID.TRAFFIC_MODULE_TWO) {
			try {
				dashboard.dispatch(3, jsonObj.LIST[0].CTRL_TYPE,jsonObj.LIST[0].STOP_SEC);
			} catch (e) {
				console.log("[ATTR_ID.TRAFFIC_MODULE_TWO] Exception :: " + e.message);		
			}
		}
		
		//신호 모듈3 수신
		else if (attrId == util.ATTR_ID.TRAFFIC_MODULE_THREE) {
			try {
        dashboard.dispatch(4, jsonObj.LIST[0].CTRL_TYPE,null);
        //if((jsonObj.LIST[0].NODE_ID == 'CR00000004')&&(jsonObj.LIST[0].CTRL_TYPE=='ST001')){
				//  dashboard.dispatch(4, jsonObj.LIST[0].CTRL_TYPE,null);
        //}
        //else{
        //  dashboard.dispatch(4, jsonObj.LIST[0].CTRL_TYPE,null);
        //}
        //else  if(jsonObj.LIST[0].CTRL_TYPE=='ST001'){
        //}
			} catch (e) {
				console.log("[ATTR_ID.TRAFFIC_MODULE_TWO] Exception :: " + e.message);		
			}
		}
		
	};