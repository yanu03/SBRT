//티맵용 전역변수
var TMAP = {
	map : {},
	marker : {},
	polyline : {},
	markers : [],
	markers_user : [],
	infoWindow : null,
	infoArr : [],
	
	// 반경 표시용 원 배열
	circles : [], 
	
	// 노드 드로잉 배열
	nodes : [],
	draggable : false
}

var tmapInfo = [];

var map, marker, polyline;
var markers = [];
var markers_user = [];
var infoWindow = null;
var infoArr = [];

// 반경 표시용 원 배열
var circles = []; 

// 노드 드로잉 배열
var nodes = [];

// 제한속도
var limitSpeed;

// 정류장 노드 타입
var busstopNodeType = "NT02";

// 음성 편성 노드 타입
var orgaNodeType;
var orgaIcon = "/assets/images/tmap/voice_node.png";

var busstopIcon = "/assets/images/tmap/busstop.png"

//var routeData = null;
var addSeq = 0;
var stnSeq = 1;
var maxNodeCnt = 800;
	
/**티맵 시작**/
function initTmap(mapId,options) {
	debugger;
	var map = new Tmapv2.Map(mapId,  
	{
		center: new Tmapv2.LatLng(36.502212, 127.256300), // 지도 초기 좌표
		width: options.width, 
		height: options.height,
		zoom: 14
	});
	if(options.onClick) {
		map.addListener("click", options.onClick);
	} else {
		//map.addListener("click", onClick);
	}
	

	tmapInfo[mapId] = TMAP;
	tmapInfo[mapId].map=map;
	
	initDisplay(mapId);

	return map;
} 

/**맵 이동**/
function moveMap(mapId, lat, lon) {
	tmapInfo[mapId].map.setCenter(new Tmapv2.LatLng(lat, lon));
}

/**마커삭제**/
function removeMarkers(mapId) {
	if(tmapInfo[mapId].markers != null && tmapInfo[mapId].markers.length != 0) {
		for (var i = 0; i < tmapInfo[mapId].markers.length; i++) {
			tmapInfo[mapId].markers[i].setMap(null);
		}
	}
	
	tmapInfo[mapId].markers = [];
}

/**유저마커삭제**/
function removeMarkers_user(mapId) {
	for (var i = 0; i < tmapInfo[mapId].markers_user.length; i++) {
		tmapInfo[mapId].markers_user[i].setMap(null);
	}
	tmapInfo[mapId].markers_user = [];
}


/**마커그리기**/
function mapMarker(mapId, lat, lng){
	removeMarkers(mapId);
	
	tmapInfo[mapId].marker = new Tmapv2.Marker({
		position: new Tmapv2.LatLng(lat, lng), //Marker의 중심좌표 설정.
		map: tmapInfo[mapId].map //Marker가 표시될 Map 설정..
	});
	tmapInfo[mapId].map.setCenter(new Tmapv2.LatLng(lat,lng));
	tmapInfo[mapId].markers.push(tmapInfo[mapId].marker);
}

/**선그리기**/
function drawLine(mapId, lat_arr, lng_arr){
	var path = [];
	for(var i=0; i < lat_arr.length; i++){
		path.push(new Tmapv2.LatLng(lat_arr[i], lng_arr[i]));
	}
	
	tmapInfo[mapId].polyline = new Tmapv2.Polyline({
		path: path,
		strokeColor: "#FF005E", // 라인 색상
		strokeWeight: 3, // 라인 두게
		map: tmapInfo[mapId].map // 지도 객체
	});
}

/**선그리기**/
function drawLine2(mapId, lat_arr, lng_arr, seq_arr){
	var path = [];
	for(var i=0; i < lat_arr.length; i++){
		if(seq_arr[i] != 0){			
			path.push(new Tmapv2.LatLng(lat_arr[i], lng_arr[i]));
		}
	}
	
	tmapInfo[mapId].polyline = new Tmapv2.Polyline({
		path: path,
		strokeColor: "#dd00dd", // 라인 색상
		strokeWeight: 3, // 라인 두게
		map: tmapInfo[mapId].map // 지도 객체
	});
}

/**선그리기**/
function drawLine3(mapId, lat_arr, lng_arr, seq_arr){
	var path = [];
	for(var i=0; i < lat_arr.length; i++){
		if(seq_arr[i] != 0){			
			path.push(new Tmapv2.LatLng(lat_arr[i], lng_arr[i]));
		}
	}
	
	tmapInfo[mapId].polyline = new Tmapv2.Polyline({
		path: path,
		strokeColor: "#640064", // 라인 색상
		strokeWeight: 3, // 라인 두게
		map: tmapInfo[mapId].map // 지도 객체
	});
}

/**선삭제**/
function deleteLine(mapId){
	if(tmapInfo[mapId].polyline != null) {
		if(typeof tmapInfo[mapId].polyline.setMap !== "undefined") 
			tmapInfo[mapId].polyline.setMap(null);
		tmapInfo[mapId].polyline = null;
	}
}

/**마커여러개추가**/
function addMarkers(mapId, lat_arr, lng_arr, id_arr) {
	for(var i=0; i < lat_arr.length; i++){
		tmapInfo[mapId].marker = new Tmapv2.Marker({
			position: new Tmapv2.LatLng(lat_arr[i], lng_arr[i]), //Marker의 중심좌표 설정.
			//label: id_arr[i]//, //Marker의 라벨.
			label: "<span style='background-color: #46414E; color:white; padding: 3px;'>" + id_arr[i] + "</span>"
			//icon:"http://tmapapi.sktelecom.com//resources/images/common/pin_car.png"
		});
		tmapInfo[mapId].marker.setMap(tmapInfo[mapId].map); //Marker가 표시될 Map 설정.
		tmapInfo[mapId].markers.push(tmapInfo[mapId].marker);
	}
}

/**노드마커 추가**/
function addMarkerInter(mapId, data, grid, idx) {
	debugger;
	tmapInfo[mapId].marker = new Tmapv2.Marker({
		position : new Tmapv2.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 // 설정.
		label : data.label, // Marker의 라벨.
		map : tmapInfo[mapId].map,
		icon : data.icon,
		draggable : data.draggable,
	});
		
	tmapInfo[mapId].marker.addListener("click", function(e) {
			grid.setFocusedCell(idx,"NODE_ID");
			/*ax5.util.search(grid.list, function(){
				return this["seq"] == data.seq;
			});*/
			data.click({
				marker: tmapInfo[mapId].marker,
				nodeId: data.NODE_ID,
				index: data.index
			});
		});
	tmapInfo[mapId].markers.push(tmapInfo[mapId].marker);
}
/**통통튀는 마커 생성**/
function addMarkerAni(lat, lng, id) {
	var aniType = Tmapv2.MarkerOptions.ANIMATE_BOUNCE;
	var coordIdx = 0;

	//removeMarkers(); // 지도에 새로 등록하기 위해 모든 마커를 지우는 함수입니다.
      
	
	var func = function() {
		//Marker 객체 생성.
		tmapInfo[mapId].marker = new Tmapv2.Marker({
			position: new Tmapv2.LatLng(lat, lng), //Marker의 중심좌표 설정.
			draggable: true, //Marker의 드래그 가능 여부.
			animation: aniType, //Marker 애니메이션.
			animationLength: 500, //애니메이션 길이.
			label: "정류장", //Marker의 라벨.
			title: id, //Marker 타이틀.
			map: tmapInfo[mapId].map //Marker가 표시될 Map 설정.
		});
		tmapInfo[mapId].markers.push(marker);

		
	}
	// 일정 시간 간격으로 마커를 생성하는 함수를 실행합니다
	setTimeout(func, 300);
}

/**지도위 팝업 생성**/
function popUp(mapId,lat, lng, msg){

	
	var content =
		"<div class = 'popUp'>" +
		"<span class = 'popUp' style='font-weight:bold;'>" + msg + "</span>" + 
		"<span class = 'popUp' style='font-size: 12px; margin-left:2px; margin-bottom:2px; display:block;'>"+ lat + "," + lng +"</span>"
		+ "</div>";
				

	tmapInfo[mapId].infoWindow = new Tmapv2.InfoWindow({
		position: new Tmapv2.LatLng(lat, lng), //Popup 이 표출될 맵 좌표
		content: content, //Popup 표시될 text
		type: 2, //Popup의 type 설정.
		map: tmapInfo[mapId].map //Popup이 표시될 맵 객체
	});
	
	tmapInfo[mapId].infoArr.push(tmapInfo[mapId].infoWindow);
}

/**팝업 전체 삭제**/
function removeAllPopUp(mapId){
	if(tmapInfo[mapId].infoArr != null){
		for(var i=0; i<tmapInfo[mapId].infoArr.length; i++){
			tmapInfo[mapId].infoArr[i].setMap(null);
		}
		tmapInfo[mapId].infoArr = [];
	}
}

/**두 지점간의 거리 계산 **/
function getDistanceBetween(x1, y1, x2, y2) {
	let kEarthRadiusKms = 6376.5;
    
    var lat1_rad = y1 * (Math.PI / 180.0);
    var lng1_rad = x1 * (Math.PI / 180.0);
    var lat2_rad = y2 * (Math.PI / 180.0);
    var lng2_rad = x2 * (Math.PI / 180.0);

    var lat_gap = lat2_rad - lat1_rad;
    var lng_gap = lng2_rad - lng1_rad;
    
    var mid_val = Math.pow(Math.sin(lat_gap / 2.0), 2.0) +
                     Math.cos(lat1_rad) * 
                     Math.cos(lat2_rad) *
                     Math.pow(Math.sin(lng_gap / 2.0), 2.0);

    var circle_distance = 2.0 * Math.atan2(Math.sqrt(mid_val), Math.sqrt(1.0 - mid_val));
    var distance = kEarthRadiusKms * circle_distance * 1000; 
    
    return distance; 
}

/**한점이 직선상에 직교좌표를 생성한 좌표를 반환**/
function getPointToLine(x, y, x1, y1, x2, y2) {
	var isValid = false;
	var point;
	
	if(y1 == y2 && x1 == y2)
		y1 -= 0.00001;
	var U = ((y - y1) * (y2 - y1)) + ((x - x1) * (x2 - x1));
	var Udenom = Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2);
	
	U /= Udenom;
	
	var y = y1 + (U * (y2 - y1));
	var x = x1 + (U * (x2 - x1));
	point =  {
		x: x,
		y: y
	};
	
	var minx, maxx, miny, maxy;
	
	minx = Math.min(y1, y2);
	maxx = Math.max(y1, y2);
	
	miny = Math.min(x1, x2);
	maxy = Math.max(x1, x2);
	
	isValid = (point.y >= minx && point.y <= maxx) && (point.x >= miny && point.x <= maxy);
	
	return isValid ? point : null;
}

/**한점이 직선에 직교좌표를 생성하고 거리를 계산**/
function getDistanceToLine(x, y, x1, y1, x2, y2) {
	var point = getPointToLine(x, y, x1, y1, x2, y2);
	
	if(point == null) {
		return false;
	} else {
		var distance = getDistanceBetween(x, y, point.x, point.y);
		return {
			point: point,
			distance: distance
		}
	}
}

function getPointToLine2(x, y, x1, y1, x2, y2) {
	var point;
	
	if(y1 == y2 && x1 == y2)
		y1 -= 0.00001;
	var U = ((y - y1) * (y2 - y1)) + ((x - x1) * (x2 - x1));
	var Udenom = Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2);
	
	U /= Udenom;
	
	var y = y1 + (U * (y2 - y1));
	var x = x1 + (U * (x2 - x1));
	point =  {
		x: x,
		y: y
	};
	
	var minx, maxx, miny, maxy;
	
	minx = Math.min(y1, y2);
	maxx = Math.max(y1, y2);
	
	miny = Math.min(x1, x2);
	maxy = Math.max(x1, x2);
	
	
	return point;
}

function getDistanceToLine2(x, y, x1, y1, x2, y2) {
	var point = getPointToLine2(x, y, x1, y1, x2, y2);
	
	if(point == null) {
		return false;
	} else {
		var distance = getDistanceBetween(x, y, point.x, point.y);
		return {
			point: point,
			distance: distance
		}
	}
}

/***************************** BM0405 *************************************/
function addMarker(mapId, data) {
	
	tmapInfo[mapId].marker = new Tmapv2.Marker({
		position: new Tmapv2.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 설정.
		label: data.label, // Marker의 라벨.
		map: tmapInfo[mapId].map,
		icon: data.icon,
		draggable: data.draggable,
	});
	
	if(data.click) {
		tmapInfo[mapId].marker.addListener("click", function(e) {
			data.click({
				marker: tmapInfo[mapId].marker,
				nodeId: data.nodeId,
				index: data.index
			});
		});
	}
	
	tmapInfo[mapId].markers.push(tmapInfo[mapId].marker);
}

function deleteCircle(mapId) {
	if( tmapInfo[mapId].circles != null &&  tmapInfo[mapId].circles.length != 0) {
		for(var i = 0; i <  tmapInfo[mapId].circles.length; i++) {
			if(typeof  tmapInfo[mapId].circles.setMap !== "undefined") 
				 tmapInfo[mapId].circles[i].setMap(null);
		}
		 tmapInfo[mapId].circles = [];
	}
}

function deleteNode(mapId) {
	if( tmapInfo[mapId].nodes != null &&  tmapInfo[mapId].nodes.length != 0) {
		for(var i = 0; i < tmapInfo[mapId].nodes.length; i++) {
			if(typeof tmapInfo[mapId].nodes.setMap !== "undefined") 
				tmapInfo[mapId].nodes[i].setMap(null);
		}
		tmapInfo[mapId].nodes = [];
	}
}

function getDrawingCircle(mapId, lat, lon, radius) {
	tmapInfo[mapId].circle = new Tmapv2.Circle({
		center: new Tmapv2.LatLng(lat, lon),
		radius: radius,
		strokeColor: "#A872EE",
		strokeWeight: 2,
		fillColor: "#A872EE",
		fillOpacity: 0.2,
		map: tmapInfo[mapId].map
	});
	
	return tmapInfo[mapId].circle;
}

function getDrawingNode(mapId, lat, lon) {
	tmapInfo[mapId].node = new Tmapv2.Circle({
		center: new Tmapv2.LatLng(lat, lon),
		radius: 4,
		strokeColor: "#FF005E",
		strokeWeight: 3,
		fillColor: "#FFFFFF",
		fillOpacity: 1,
		map: tmapInfo[mapId].map
	});
	
	return tmapInfo[mapId].node;
}

function returnInsertRouteInfo(lat, lon, routeData) {
	var min = 10000000;
	var minIndex = null;
	
	for(var i = 0; i < routeData.length - 1; i++) {
		var result = getDistanceToLine(
			lat,
			lon,
			routeData[i].GPS_Y,
			routeData[i].GPS_X,
			routeData[i + 1].GPS_Y,
			routeData[i + 1].GPS_X
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
		var insertIndex = minIndex + 1;
		
		return {
			index: insertIndex
		};
	}
}

function addPathByClick(mapId,grid,routeId,e){
	debugger;
	if(com.getGridViewDataList(grid).length >= maxNodeCnt){
		//com.alert ("더이상 추가할 수 없습니다.");
		return false;
	}
	
	
	var lonlat = e.latLng;
	
	var idx = grid.getFocusedRowIndex();
	
	if(idx==-1)
		idx = com.getGridViewDataList(grid).insertRow();
	else
		idx = com.getGridViewDataList(grid).insertRow(idx);

	var today = new Date();
	var data = {
	
			ROUTE_ID: routeId,
			NODE_SN: idx,
			NODE_NM: /*routNm + */"노드_" + getCurrentDate(),
			NODE_TYPE: 'NT12',
			GPS_Y: lonlat.lat(),
			GPS_X: lonlat.lng(),
			};

	com.getGridViewDataList(grid).setRowJSON(idx, data, true);
	
	//routeData = com.getGridViewDataList(grid).getAllJSON();
	drawRoute(mapId, grid, idx);
}

function focusNode(mapId, grid,idx, draggable){
	routeData = com.getGridViewDataList(grid).getAllJSON();
	drawRoute(mapId, grid, idx, draggable);
	moveMap(mapId, routeData[idx].GPS_Y, routeData[idx].GPS_X);
}

function  insertNodeAll(mapId, grid,dataList,routeId,draggable){
	debugger;
	for (i = 0; i < dataList.length; i++) {
		addNode(mapId, grid,dataList[i],routeId);
	}
	drawRoute(mapId, grid, -1, draggable);
}

//노드 추가
function addNode(mapId, grid, data, routeId) {
	var routeData = com.getGridViewDataList(grid).getAllJSON();
	if(com.getGridViewDataList(grid).length >= maxNodeCnt){
		//com.alert("더이상 추가할 수 없습니다.");
		return false;
	}
	
	var min = 10000000;
	var minIndex = null;
	if(routeData==null||routeData.length==0||routeData.length==1){
		var temp = {
				ROUTE_ID: routeId,
				NODE_ID: data.NODE_ID,
				GPS_Y: data.GPS_Y,
				GPS_X: data.GPS_X,
				NODE_NM: data.NODE_NM,
				NODE_TYPE: data.NODE_TYPE
			};
		
		var idx = com.getGridViewDataList(grid).insertRow();
		com.getGridViewDataList(grid).setRowJSON(idx, temp, true);
		routeData = com.getGridViewDataList(grid).getAllJSON();
	}
	else {
		for(var i = 0; i < routeData.length - 1; i++) {
			var result = getDistanceToLine(
				data.GPS_Y,
				data.GPS_X,
				routeData[i].GPS_Y,
				routeData[i].GPS_X,
				routeData[i + 1].GPS_Y,
				routeData[i + 1].GPS_X
			)
			if(result==false){
				var temp = {
						ROUTE_ID: routeId,
						NODE_ID: data.NODE_ID,
						GPS_Y: data.GPS_Y,
						GPS_X: data.GPS_X,
						NODE_NM: data.NODE_NM,
						NODE_TYPE: data.NODE_TYPE
					};
				
				var idx = com.getGridViewDataList(grid).insertRow();
				com.getGridViewDataList(grid).setRowJSON(idx, temp, true);
				//routeData.splice(idx, 0, temp);
				return;
			}
			if(result.distance) {
				if(min > result.distance) {
					min = result.distance;
					minIndex = i;
				}
			}
		}
		
		if(minIndex == null) {
			//alert.alert("선택할 수 없는 좌표입니다. 경로를 먼저 입력하세요");
		} else {
			//isNewData = true;
			//var seq = 0;
			//seq = routeData[minIndex].seq + (routeData[minIndex + 1].seq - routeData[minIndex].seq) / 2;				
			var insertIndex = minIndex + 1;
			
			var temp = {
					ROUTE_ID: routeId,
					NODE_ID: data.NODE_ID,
					GPS_Y: data.GPS_Y,
					GPS_X: data.GPS_X,
					NODE_NM: data.NODE_NM,
					NODE_TYPE: data.NODE_TYPE
				};
			
			//routeData.splice(insertIndex, 0, temp);
			
			var idx = com.getGridViewDataList(grid).insertRow(insertIndex);
			com.getGridViewDataList(grid).setRowJSON(idx, temp, true);
			
		}
	}
	
}

function moveRoute(mapId, grid, e){
	var routeData = com.getGridViewDataList(grid).getAllJSON();
	var point = e.marker.getPosition();
	var node = $.extend(true, {}, routeData[e.index]);
	//routeData.splice(e.index, 1);
	com.getGridViewDataList(grid).removeRow(e.index);
	var val = returnInsertRouteInfo(point.lat(), point.lng(),routeData);
	
	/*if(e.index == 0 || e.index == routeData.length){
		val = true;
	}*/
	
	if(val) {
		var temp = {
			NODE_ID: e.nodeId,
			GPS_Y: point.lat(),
			GPS_X: point.lng(),
		};
		
		temp = $.extend(true, node, temp);
		
		
		//routeData.splice(val.index, 0, temp);
		
		var idx = com.getGridViewDataList(grid).insertRow(val.index);
		com.getGridViewDataList(grid).setRowJSON(idx, temp, true);

		drawRoute(mapId, grid, routeData);
		
		//routeData.sort(function (a,b){ return a.seq - b.seq });
		//fnObj.gridView1.setData(routeData);
		
		//ACTIONS.dispatch(ACTIONS.DRAW_ROUTE);
	} else {
		//com.alert("선택할 수 없는 좌표입니다.");
		
		drawRoute(mapId, grid, routeData);
	}
}

function drawRoute(mapId, grid, idx, draggable) {
	var list = com.getGridViewDataList(grid).getAllJSON();
	var path = [];
	//var map = tmapInfo[mapId].map;
	//var polyline = tmapInfo[mapId].polyline;
	//var nodes = tmapInfo[mapId].nodes;
	
	removeMarkers(mapId);
	deleteLine(mapId);
	deleteCircle(mapId);
	deleteNode(mapId);
	
	if(list != null && list.length != 0) {
		for(var i = 0; i < list.length; i++) {
			path.push(new Tmapv2.LatLng(list[i].GPS_Y, list[i].GPS_X));
			
			/**드래그이벤트**/
			list[i].click = function(e) {
				moveRoute(mapId,grid,e);
			};
			
			list[i].index = i;
			
			/**드래그이벤트**/
			if(typeof draggable == "undefined"){
				list[i].draggable = tmapInfo[mapId].draggable;
			}
			else{
				list[i].draggable = draggable;
				 tmapInfo[mapId].draggable = draggable
			}
			
			// 노드 타입이 버스 정류장 또는 음성편성 노드일 경우 마커 표시
			if(list[i].NODE_TYPE == busstopNodeType) {
				if(i == idx){
					list[i].icon = "/cm/images/tmap/busstop_selected.png";
				}else{
					list[i].icon = "/cm/images/tmap/busstop.png";					
				}
				list[i].label = "<span style='background-color: white; color:black; padding: 3px; border: 0.5px solid black;'>" + list[i].NODE_NM + "</span>";
			}
			// 아닐 경우(일반 노드) 네모 박스 표시
			else {
				if(i == idx){
					list[i].icon = "/cm/images/tmap/road_selected.png";
				}else{
					list[i].icon = "/cm/images/tmap/road_trans.png";										
				}
				list[i].label = "<span style='background-color: white; color:black; padding: 3px; border: 0.5px solid black;'>" + list[i].NODE_NM + "</span>";
				tmapInfo[mapId].nodes.push(getDrawingNode(mapId,list[i].GPS_Y, list[i].GPS_X));
			}
			
			addMarkerInter(mapId, list[i], grid, i);
		}
		
		tmapInfo[mapId].polyline = new Tmapv2.Polyline({
			path: path,
			strokeColor: "#FF005E",
			strokeWeight: 2,
			map: tmapInfo[mapId].map,
			zIndex: -1
		}); 
	}
}

/**노선 링크 그리기**/
function drawLinkRoute(mapId, grid,idx) {

	var list = com.getGridViewDataList(grid).getAllJSON();
	var path1 = [];
	var path2 = [];
	var path3 = [];
	//var map = tmapInfo[mapId].map;
	//var polyline = tmapInfo[mapId].polyline;
	//var nodes = tmapInfo[mapId].nodes;
	
	removeMarkers(mapId);
	deleteLine(mapId);
	deleteCircle(mapId);
	deleteNode(mapId);
	
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
				tmapInfo[mapId].nodes.push(getDrawingNode(mapId,list[i].ST_GPS_Y, list[i].ST_GPS_X));
			}
			
			addMarkerInter(mapId, list[i], grid, i);
		}
		tmapInfo[mapId].polyline = new Tmapv2.Polyline({
			path: path1,
			strokeColor: "#0000FF",
			strokeWeight: 2,
			map: tmapInfo[mapId].map,
			zIndex: -1
		});
		
		tmapInfo[mapId].polyline = new Tmapv2.Polyline({
			path: path2,
			strokeColor: "#dd00dd",
			strokeWeight: 2,
			map: tmapInfo[mapId].map,
			zIndex: -1
		});
		
		tmapInfo[mapId].polyline = new Tmapv2.Polyline({
			path: path3,
			strokeColor: "#FF005E",
			strokeWeight: 2,
			map: tmapInfo[mapId].map,
			zIndex: -1
		});
		if(list.length>0){
			moveMap(mapId, list[parseInt(list.length/2)].ST_GPS_Y, list[parseInt(list.length/2)].ST_GPS_X);
		}
		tmapInfo[mapId].map.setZoom(15);
	}
}

function addMarkerMap(data) {
	var marker = new Tmapv2.Marker({
		position: new Tmapv2.LatLng(data.GPS_Y, data.GPS_X), //Marker의 중심좌표 설정.
		label: data.label, //Marker의 라벨.
		map: map,
		icon: data.icon,
		draggable: data.draggable,
	});
	
	markers.push(marker);
}

function moveLineMap(dataList,e){
	debugger;
	var point = e.marker.getPosition();
	dataList.removeRow(e.index);
	var val = returnInsertRouteInfo(point.lat(), point.lng(),dataList.getAllJSON());
	
	/*if(e.index == 0 || e.index == routeData.length){
		val = true;
	}*/
	
	if(val) {
		var temp = {
			GRG_ID: e.grgId,
			GPS_Y: point.lat(),
			GPS_X: point.lng(),
		};
		
		
		var idx = dataList.insertRow(val.index);
		dataList.setRowJSON(idx, temp, true);

		drawLineMap(dataList);
		
		//routeData.sort(function (a,b){ return a.seq - b.seq });
		//fnObj.gridView1.setData(routeData);
		
		//ACTIONS.dispatch(ACTIONS.DRAW_ROUTE);
	} else {
		//com.alert("선택할 수 없는 좌표입니다.");
		
		drawLineMap(dataList);
	}
}


function addPathMapByClick(data,grgId,e){
	
	var lonlat = e.latLng;
	
	if(idx==-1)
		idx = com.getGridViewDataList(grid).insertRow();
	else
		idx = com.getGridViewDataList(grid).insertRow(idx);

	var today = new Date();
	var data = {
			GRG_ID: grgId,
			SN: idx,
			GPS_Y: lonlat.lat(),
			GPS_X: lonlat.lng()
			};

	data.setRowJSON(idx, data, true);
	
	routeData = com.getGridViewDataList(grid).getAllJSON();
	drawRoute(grid, routeData, idx);
}


//일반 지도 라인 그리기
function drawLineMap(data) {
	debugger;
	var list = data.getAllJSON();
	var path = [];
	
	//removeMarkers();
	//deleteLine();
	//deleteCircle();
	//deleteNode();
	
	var tmpParentIdx = 0;
	var thirdIndex = 0;

	//첫번째 메뉴 세팅
	if(list != null && list.length != 0) {
		for(var i = 0; i < list.length; i++) {
			path.push(new Tmapv2.LatLng(list[i].GPS_Y, list[i].GPS_X));

			list[i].index = i;
			
			/**드래그이벤트**/
			list[i].click = function(e) {
				debugger;
				moveLineMap(data,e);
			};

			list[i].icon = "/cm/images/tmap/road_trans.png";										
			//list[i].label = "<span style='background-color: white; color:black; padding: 3px; border: 0.5px solid black;'>" + list[i].nodeNm + "</span>";
			nodes.push(getDrawingNode(list[i].GPS_Y, list[i].GPS_X));

			addMarkerMap(list[i]);
		}

		polyline = new Tmapv2.Polyline({
			path: path,
			strokeColor: "#FF005E",
			strokeWeight: 2,
			map: map
		});

		if(list.length>0)moveMap(list[0].GPS_Y, list[0].GPS_X);
		
		map.setZoom(15);
		
	}
}

function initDisplay(mapId){
	removeMarkers(mapId);
	deleteLine(mapId);
	deleteCircle(mapId);
	deleteNode(mapId);
}
/**************************************************************************/
