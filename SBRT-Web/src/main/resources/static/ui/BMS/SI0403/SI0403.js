var fnObj = {}, CODE = {};

/***************************************** 전역 변수 초기화 ******************************************************/
isUpdate = false;
isNewData = false;
selectedRow = null;
selectedRow0 = null;
var routeData = null;
var addSeq = 0;
var stnSeq = 1;
var maxNodeCnt = 800;
var stopAdd;
var nodeAdd;
/*************************************************************************************************************/

/***************************************** 이벤트 처리 코드 ******************************************************/

function searchGrid1(caller, act, data){
	var dataFlag = typeof data !== "undefined";
	data.filter1 = $('#filter1').val();
	axboot.ajax({
        type: "GET",
        url: "/api/v1/BM0109G1S0",
        data: data,
        callback: function (res) {
        	fnObj.gridView1.setData(res);
        	removeAllPopUp();
        	/**추가한거**/
            if(res.list != null && res.list.length != 0) {
            	routeData = res.list.slice();
            	fnObj.gridView1.selectRow(0);
            } else {
            	routeData = [];
            }
            ACTIONS.dispatch(ACTIONS.DRAW_ROUTE);
            /**추가한거끝**/	
        }
    });
}

/*****************************************/
//맵 클릭 이벤트
function onClickMap(e) {
	
	$("input:checkbox[id='toggleStn']").prop("checked", true);
	$("input:checkbox[id='toggleNode']").prop("checked", true);
	var routNm = selectedRow0.routNm;
	
	
	if(stopAdd && !nodeAdd) {
		if(fnObj.gridView1.getData().length >= maxNodeCnt){
			axDialog.alert("더이상 추가할 수 없습니다.");
			return false;
		}
		
		var lonlat = e.latLng;
		var min = 10000000;
		var minIndex = null;

		for(var i = 0; i < routeData.length - 1; i++) {
			var result = getDistanceToLine(
				lonlat.lat(),
				lonlat.lng(),
				routeData[i].lati,
				routeData[i].longi,
				routeData[i + 1].lati,
				routeData[i + 1].longi
			)
			
			if(result.distance) {
				if(min > result.distance) {
					min = result.distance;
					minIndex = i;
				}
			}
		}
		
		if(minIndex == null) {
			axDialog.alert("선택할 수 없는 좌표입니다. 경로를 먼저 입력하세요");
		} else {
			var seq = 0;
			seq = routeData[minIndex].seq + (routeData[minIndex + 1].seq - routeData[minIndex].seq) / 2;				
			var insertIndex = minIndex + 1;
			
			routeData.splice(insertIndex, 0, {
				routId: selectedRow0.routId,
				lati: lonlat.lat(),
				longi: lonlat.lng(),
				seq: seq,
				nodeNm: routNm + "_정류장" + stnSeq,
				krNm : routNm + "_정류장" + stnSeq,
				enNm : routNm + "_stn" + stnSeq,
				nodeType: '1',
				nodeTypeNm: '정류장',
				icon: '',
			});

			routeData.sort(function (a,b){ return a.seq - b.seq });
			stnSeq++;
			
			fnObj.gridView1.setData(routeData);
			ACTIONS.dispatch(ACTIONS.DRAW_ROUTE);
			
		}
	}else if(!stopAdd && nodeAdd){
		if(fnObj.gridView1.getData().length >= maxNodeCnt){
			axDialog.alert("더이상 추가할 수 없습니다.");
			return false;
		}
		
		
		var lonlat = e.latLng;
		
		var idx;
		if(selectedRow.__index != undefined){
			idx = selectedRow.__index;
		}else{
			idx = 0;
		}
		
		var row1 = fnObj.gridView1.getData()[idx];
		var row2 = fnObj.gridView1.getData()[idx+1];

		if(row1 == undefined){
			seq = 100;
		}else{
			if(row2 != undefined){
				seq = (row1.seq + row2.seq) / 2;
			}else{
				seq = row1.seq + 100;
			}
		}
		
		var data = {
				routId: selectedRow0.routId,
				lati: lonlat.lat(),
				longi: lonlat.lng(),
				seq: seq,
				nodeNm: routNm + "_작업노드" + seq,
				nodeType: '30', 
				nodeTypeNm: '지점',
				icon: '',};
		
		routeData.splice(insertIndex, 0, data);
		
		routeData.sort(function (a,b){ return a.seq - b.seq });
		addSeq++;
		fnObj.gridView1.setData(routeData);
		fnObj.gridView1.selectRow(idx+1);
		ACTIONS.dispatch(ACTIONS.DRAW_ROUTE);
	}
	
}

//0315 기존정류장 선택
function addStopSelected(data) {
	$("input:checkbox[id='toggleStn']").prop("checked", true);
	$("input:checkbox[id='toggleNode']").prop("checked", true);
	var routNm = selectedRow0.routNm;
	
	if(fnObj.gridView1.getData().length >= maxNodeCnt){
		axDialog.alert("더이상 추가할 수 없습니다.");
		return false;
	}
	
	
	var min = 10000000;
	var minIndex = null;

	for(var i = 0; i < routeData.length - 1; i++) {
		var result = getDistanceToLine(
			data.lati,
			data.longi,
			routeData[i].lati,
			routeData[i].longi,
			routeData[i + 1].lati,
			routeData[i + 1].longi
		)
		
		if(result.distance) {
			if(min > result.distance) {
				min = result.distance;
				minIndex = i;
			}
		}
	}
	
	if(minIndex == null) {
		axDialog.alert("선택할 수 없는 좌표입니다. 경로를 먼저 입력하세요");
	} else {
		isNewData = true;
		var seq = 0;
		seq = routeData[minIndex].seq + (routeData[minIndex + 1].seq - routeData[minIndex].seq) / 2;				
		var insertIndex = minIndex + 1;
		
		routeData.splice(insertIndex, 0, {
			routId: selectedRow0.routId,
			lati: data.lati,
			longi: data.longi,
			seq: seq,
			nodeNm: data.staNm,
			krNm: data.krNm,
			enNm: data.enNm,
			staNo: data.staNo,
			nodeType: '1',
			nodeId: data.staId,
			nodeTypeNm: '정류장',
			updatedAt: data.updatedAt,
			icon: '',
		});

		routeData.sort(function (a,b){ return a.seq - b.seq });
		stnSeq++;
		
		fnObj.gridView1.setData(routeData);
		ACTIONS.dispatch(ACTIONS.DRAW_ROUTE);
		
	}
	
}
//


function returnInsertRouteInfo(lat, lon) {
	var min = 10000000;
	var minIndex = null;
	
	for(var i = 0; i < routeData.length - 1; i++) {
		var result = getDistanceToLine(
			lat,
			lon,
			routeData[i].lati,
			routeData[i].longi,
			routeData[i + 1].lati,
			routeData[i + 1].longi
		)
		
		if(result.distance) {
			if(min > result.distance) {
				min = result.distance;
				minIndex = i;
			}
		}
	}
	
	if(minIndex == null) {
		return false;
	} else {
		var seq = routeData[minIndex].seq + (routeData[minIndex + 1].seq - routeData[minIndex].seq) / 2;
		var insertIndex = minIndex + 1;
		
		return {
			seq: seq,
			index: insertIndex
		};
	}
}

/**노선 그리기**/
function drawRoute(list, idx) {
	
	var path1 = [];
	var path2 = [];
	var path3 = [];
	
	removeMarkers();
	deleteLine();
	deleteCircle();
	deleteNode();
	
	var tmpParentIdx = 0;
	var thirdIndex = 0;

	//첫번째 메뉴 세팅

	
	if(list != null && list.length != 0) {
		for(var i = 0; i < list.length; i++) {
			if(list[i].MORN_STD=='MS001'||list[i].MORN_STD=='MS002'||list[i].MORN_STD=='MS003'){
				path1.push(new Tmapv2.LatLng(list[i].ST_GPS_Y, list[i].ST_GPS_X));
				path1.push(new Tmapv2.LatLng(list[i].ED_GPS_Y, list[i].ED_GPS_X));
			}
			if(list[i].MORN_STD=='MS002' || list[i].MORN_STD=='MS003'){
				path2.push(new Tmapv2.LatLng(list[i].ST_GPS_Y, list[i].ST_GPS_X));
				path2.push(new Tmapv2.LatLng(list[i].ED_GPS_Y, list[i].ED_GPS_X));
			}
			if(list[i].MORN_STD=='MS003'){
				path3.push(new Tmapv2.LatLng(list[i].ST_GPS_Y, list[i].ST_GPS_X));
				path3.push(new Tmapv2.LatLng(list[i].ED_GPS_Y, list[i].ED_GPS_X));
			}
			
			list[i].index = i;
			/**드래그이벤트**/
			//list[i].draggable = true;
			// 노드 타입이 버스 정류장 또는 음성편성 노드일 경우 마커 표시
			/*if(list[i].nodeType == busstopNodeType) {
				if(i == idx){
					list[i].icon = "/cm/images/tmap/busstop_selected.png";
				}else{
					list[i].icon = "/cm/images/tmap/busstop.png";					
				}
				list[i].label = "<span style='background-color: white; color:black; padding: 3px; border: 0.5px solid black;'>" + list[i].krNm + "</span>";
			}*/
			// 아닐 경우(일반 노드) 네모 박스 표시
			//else 
			
			{
				if(i == idx){
					list[i].icon = "/cm/images/tmap/road_selected.png";
				}else{
					list[i].icon = "/cm/images/tmap/road_trans.png";										
				}
				list[i].label = "<span style='background-color: white; color:black; padding: 3px; border: 0.5px solid black;'>" + list[i].nodeNm + "</span>";
				nodes.push(getDrawingNode(list[i].ST_GPS_Y, list[i].ST_GPS_X));
			}
			
			addMarkerInter(list[i], fnObj.gridView1, i);
		}
		polyline = new Tmapv2.Polyline({
			path: path1,
			strokeColor: "#0000FF",
			strokeWeight: 2,
			map: map,
			zIndex: -1
		});
		
		polyline = new Tmapv2.Polyline({
			path: path2,
			strokeColor: "#dd00dd",
			strokeWeight: 2,
			map: map,
			zIndex: -1
		});
		
		polyline = new Tmapv2.Polyline({
			path: path3,
			strokeColor: "#FF005E",
			strokeWeight: 2,
			map: map,
			zIndex: -1
		});
		
	}
}

function btnClick(){
	stopAdd = false;
	nodeAdd = false;
	
	$('#stopAdd').on('click', function(){
		addStop();
	});
	$('#nodeAdd').on('click', function(){
		addNode();
	});
	
	//0315추가
	$('#stopAdd2').on('click', function(){
		ACTIONS.dispatch(ACTIONS.ADD_STOP);
	});
	
	$('#refresh').on('click', function(){
		var tmpData = fnObj.gridView1.getData();
		tmpData.sort(function (a,b){return a.seq - b.seq});
		fnObj.gridView1.setData(tmpData);
		drawRoute(fnObj.gridView1.getData());
	});
	
	$('#rowDel').on('click', function(){
		var idx = selectedRow.__index;
		fnObj.gridView1.delRow("selected");
		
		routeData = fnObj.gridView1.getData();
		routeData.sort(function (a,b){ return a.seq - b.seq });
		
		fnObj.gridView1.setData(routeData);
		drawRoute(routeData);
		fnObj.gridView1.selectRow(idx);
	});
	
	$('#searchSta').on('click', function(){
		searchGrid1(fnObj,null,selectedRow0);
	});
}

var insertStn = "정류장추가";
var activeStn = "정류장종료";

var insertNode = "경로추가";
var activeNode = "경로종료";

function addStop(){
	if(stopAdd) {
		stopAdd = false;
		$("#mapView0").removeClass("cursor-crosshair");
		$('#stopAdd').html('<button class="btn btn-default" data-grid-control="stop-add" style="width: 100px;"><i class="cqc-plus"></i>'+ insertStn +'</button>');
	} else {
		stopAdd = true;
		isNewData = true;
		$("#mapView0").addClass("cursor-crosshair");
		$('#stopAdd').html('<button class="btn btn-info" data-grid-control="stop-add" style="width: 100px;"><i class="cqc-minus"></i>' + activeStn + '</button>');
	}
	
	if(nodeAdd) {
		nodeAdd = false;
		$("#mapView0").removeClass("cursor-crosshair");
		$('#nodeAdd').html('<button class="btn btn-default" data-grid-control="node-add" style="width: 100px;"><i class="cqc-plus"></i>' + insertNode + '</button>');
	}
}

function addNode(){
	if(nodeAdd) {
		nodeAdd = false;
		$("#mapView0").removeClass("cursor-crosshair");
		$('#nodeAdd').html('<button class="btn btn-default" data-grid-control="node-add" style="width: 100px;"><i class="cqc-plus"></i>' + insertNode + '</button>');
	} else {
		nodeAdd = true;
		isNewData = true;
		$("#mapView0").addClass("cursor-crosshair");
		$('#nodeAdd').html('<button class="btn btn-info" data-grid-control="node-add" style="width: 100px;"><i class="cqc-minus"></i>' + activeNode + '</button>');
	}
	
	if(stopAdd) {
		stopAdd = false;
		$("#mapView0").removeClass("cursor-crosshair");
		$('#stopAdd').html('<button class="btn btn-default" data-grid-control="stop-add" style="width: 100px;"><i class="cqc-plus"></i>' + insertStn + '</button>');
	}
}

function chked(){
	$('#toggleStn').on('change', function(){
		if($('#toggleStn').is(":checked")){
			if($('#toggleNode').is(":checked")){
				onOffMarker("11");
			}else{
				onOffMarker("10");
			}
		}else{
			if($('#toggleNode').is(":checked")){
				onOffMarker("01");
			}else{
				onOffMarker("00");
			}
		}
	});
	
	$('#toggleNode').on('change', function(){
		if($('#toggleStn').is(":checked")){
			if($('#toggleNode').is(":checked")){
				onOffMarker("11");
			}else{
				onOffMarker("10");
			}
		}else{
			if($('#toggleNode').is(":checked")){
				onOffMarker("01");
			}else{
				onOffMarker("00");
			}
		}
	});
}

function onOffMarker(input){
	var list = fnObj.gridView1.getData();
	switch(input){
	case "00" :
		removeMarkers();
		break;
		
	case "10" : //정류장만
		removeMarkers();
		for(var i=0; i<list.length; i++){
			if(list[i].nodeType == '1'){
				list[i].icon = "/cm/images/tmap/busstop.png";
				//list[i].label = "<span style='background-color: #46414E; color:white; padding: 3px;'>" + list[i].nodeNm + "</span>";
				list[i].label = "<span style='background-color: white; color:black; padding: 3px; border: 0.5px solid black;'>" + list[i].krNm + "</span>";
				list[i].draggable = true;
				addMarkerInter(list[i], fnObj.gridView1, i);
			}
		}
		break;
		
	case "01" : //경로만
		removeMarkers();
		for(var i=0; i<list.length; i++){
			if(list[i].nodeType == '30'){
				list[i].icon = "/cm/images/tmap/road_trans.png";
				//list[i].label = "<span style='background-color: #46414E; color:white; padding: 3px;'>" + list[i].nodeNm + "</span>";
				list[i].label = "<span style='background-color: white; color:black; padding: 3px; border: 0.5px solid black;'>" + list[i].nodeNm + "</span>";
				list[i].draggable = true;
				addMarkerInter(list[i], fnObj.gridView1, i);
			}
		}
		break;
		
	case "11" : //다
		removeMarkers();
		for(var i=0; i<list.length; i++){
			if(list[i].nodeType=="30"){
				list[i].icon = "/cm/images/tmap/road_trans.png";
				list[i].label = "<span style='background-color: white; color:black; padding: 3px; border: 0.5px solid black;'>" + list[i].nodeNm + "</span>";
			}else{
				list[i].icon = "/cm/images/tmap/busstop.png";				
				list[i].label = "<span style='background-color: white; color:black; padding: 3px; border: 0.5px solid black;'>" + list[i].krNm + "</span>";
			}
			list[i].draggable = true;
			addMarkerInter(list[i], fnObj.gridView1, i);
		}
		break;
	}
}

