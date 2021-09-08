//노선 맵용 전역변수
var routMap = {
	mapInfo : [],
	NODE_TYPE : {
		CROSS : "NT001",
		BUSSTOP : "NT002",
		NORMAL : "NT003",
		VERTEX : "NT005",
		SOUND : "NT006"
	},
	MAX_NODE_CNT : 800,
	LIMIT_SPEED : 50
}




var RoutMAP = function(){
	this.map = {};
	this.polylines = [];
	this.polygons = [];
	this.markers = [];
	this.markers_user = [];
	this.infoWindow = null;
	this.infoArr = [];
	this.overlay = null;
	this.overArr = [];
	// 반경 표시용 원 배열
	this.circles = []; 
	// 노드 드로잉 배열
	this.nodes = [];
	this.draggable = false;
	this.dragging = false; //드래그 중인지
	this.dispCheck = "";
	this.nodeChangeCb = {};
	this.isMove = true;
	this.selectedMarker = null;
	this.selectedIndex = -1;
	this.oldSelectedIndex = -1;
	this.linkMode = false;
	this.busMarkers = [];
	this.busOverlay = null;
	this.busOverArr = [];
	this.selectedBusMarker = null;
	this.isSound = false;
}

/**노선 맵 시작**/
routMap.initMap = function(mapId,options) {
	
	var mapContainer = document.getElementById(mapId);
	var mapOptions = {
		center : new kakao.maps.LatLng(36.502212, 127.256300),
		disableDoubleClickZoom: true,
		level : 5
	};
	
    mapContainer.style.width = options.width;
    mapContainer.style.height = options.height; 
	
	var map = new kakao.maps.Map(mapContainer, mapOptions); //지도 생성 및 객체 리턴

	if(options.onClick) {
		kakao.maps.event.addListener(map, 'click', function(e) {
			
			options.onClick(e);
		});
	} else {
		//map.addListener("click", onClick);
	}

	routMap.mapInfo[mapId] = new RoutMAP();
	routMap.mapInfo[mapId].map=map;
	routMap.mapInfo[mapId].nodeChangeCb = options.nodeChangeCb;
	routMap.mapInfo[mapId].draggable=options.draggable;
	routMap.mapInfo[mapId].linkMode=options.linkMode;
	routMap.mapInfo[mapId].isSound=options.isSound;
	
	//routMap.initDisplay(mapId);

	return map;
}

routMap.initMap2 = function(mapId,options) {
	
	var mapContainer = document.getElementById(mapId);
	var mapOptions = {
			center : new kakao.maps.LatLng(36.502212, 127.256300),
			disableDoubleClickZoom: true,
			level : 2
	};
	
	mapContainer.style.width = options.width;
	mapContainer.style.height = options.height; 
	
	var map = new kakao.maps.Map(mapContainer, mapOptions); //지도 생성 및 객체 리턴
	
	if(options.onClick) {
		kakao.maps.event.addListener(map, 'click', function(e) {
			
			options.onClick(e);
		});
	} else {
		//map.addListener("click", onClick);
	}
	
	routMap.mapInfo[mapId] = new RoutMAP();
	routMap.mapInfo[mapId].map=map;
	routMap.mapInfo[mapId].nodeChangeCb = options.nodeChangeCb;
	routMap.mapInfo[mapId].draggable=options.draggable;
	routMap.mapInfo[mapId].linkMode=options.linkMode;
	routMap.mapInfo[mapId].isSound=options.isSound;
	
	//routMap.initDisplay(mapId);
	
	return map;
} 

/**맵 이동**/
routMap.moveMap = function(mapId, lat, lon) {
	
	if(routMap.mapInfo[mapId].isMove){
		routMap.mapInfo[mapId].map.setCenter(new kakao.maps.LatLng(lat, lon));
	}
}

/**마커삭제**/
routMap.removeMarkers = function(mapId) {
	if(routMap.mapInfo[mapId].markers != null && routMap.mapInfo[mapId].markers.length != 0) {
		for (var i = 0; i < routMap.mapInfo[mapId].markers.length; i++) {
			routMap.mapInfo[mapId].markers[i].setMap(null);
		}
	}
	
	routMap.mapInfo[mapId].markers = [];
}

/**버스마커삭제**/
routMap.removeBusMarkers = function(mapId) {
	if(routMap.mapInfo[mapId].busMarkers != null && routMap.mapInfo[mapId].busMarkers.length != 0) {
		for (var i = 0; i < routMap.mapInfo[mapId].busMarkers.length; i++) {
			routMap.mapInfo[mapId].busMarkers[i].setMap(null);
		}
	}
	
	routMap.mapInfo[mapId].busMarkers = [];
}

routMap.removeIndexBusMarker = function(mapId,index) {
	if(routMap.mapInfo[mapId].busMarkers != null && routMap.mapInfo[mapId].busMarkers.length != 0) {
		routMap.mapInfo[mapId].busMarkers[index].setMap(null);
	}
}

/**유저마커삭제**/
routMap.removeMarkers_user = function(mapId) {
	for (var i = 0; i < routMap.mapInfo[mapId].markers_user.length; i++) {
		routMap.mapInfo[mapId].markers_user[i].setMap(null);
	}
	routMap.mapInfo[mapId].markers_user = [];
}


/**마커그리기**/
routMap.mapMarker = function(mapId, lat, lng){
	routMap.removeMarkers(mapId);
	
	var marker = new kakao.maps.Marker({
		position: new kakao.maps.LatLng(lat, lng), //Marker의 중심좌표 설정.
	});
	marker.setMap(routMap.mapInfo[mapId].map); //Marker가 표시될 Map 설정..
	routMap.mapInfo[mapId].map.setCenter(new kakao.maps.LatLng(lat,lng));
	routMap.mapInfo[mapId].markers.push(marker);
}

/**리스트 선그리기**/
routMap.drawLineArr = function(mapId, lat_arr, lng_arr, color){
	var path = [];
	for(var i=0; i < lat_arr.length; i++){
		path.push(new kakao.maps.LatLng(lat_arr[i], lng_arr[i]));
	}
	
	var polyline = new kakao.maps.Polyline({
		path: path,
		strokeColor: color, // 라인 색상
		strokeWeight: 3 // 라인 두게
	});
	
	polyline.setMap(routMap.mapInfo[mapId].map);
	routMap.mapInfo[mapId].polylines.push(polyline);
}

/**선그리기**/
routMap.drawLine = function(mapId, first, last, color){
	var path = [];
	path.push(new kakao.maps.LatLng(first.GPS_Y, first.GPS_X));
	path.push(new kakao.maps.LatLng(last.GPS_Y, last.GPS_X));
	
	var polyline = new kakao.maps.Polyline({
		path: path,
		strokeColor: color, // 라인 색상
		strokeWeight: 10 // 라인 두께
	});
	
	polyline.setMap(routMap.mapInfo[mapId].map);
	
	routMap.mapInfo[mapId].polylines.push(polyline);
}

/**선삭제**/
routMap.deleteLine = function(mapId){
	if(routMap.mapInfo[mapId].polylines != null && routMap.mapInfo[mapId].polylines.length != 0) {
		for (var i = 0; i < routMap.mapInfo[mapId].polylines.length; i++) {
			routMap.mapInfo[mapId].polylines[i].setMap(null);
		}
	}
	
	routMap.mapInfo[mapId].polylines = [];
}

/**다각형삭제**/
routMap.deletePolygon = function(mapId){
	if(routMap.mapInfo[mapId].polygons != null && routMap.mapInfo[mapId].polygons.length != 0) {
		for (var i = 0; i < routMap.mapInfo[mapId].polygons.length; i++) {
			routMap.mapInfo[mapId].polygons[i].setMap(null);
		}
	}
	
	routMap.mapInfo[mapId].polygons = [];
}

/**마커여러개추가**/
routMap.addMarkers = function(mapId, lat_arr, lng_arr, id_arr) {
	for(var i=0; i < lat_arr.length; i++){
		var marker = new kakao.maps.Marker({
			position: new kakao.maps.LatLng(lat_arr[i], lng_arr[i]), //Marker의 중심좌표 설정.
			//label: id_arr[i]//, //Marker의 라벨.
			//label: "<span style='background-color: #46414E; color:white; padding: 3px;'>" + id_arr[i] + "</span>"
			//icon:"http://tmapapi.sktelecom.com//resources/images/common/pin_car.png"
		});
		marker.setMap(routMap.mapInfo[mapId].map); //Marker가 표시될 Map 설정.
		routMap.mapInfo[mapId].markers.push(marker);
	}
}

routMap.nodeChange = function(mapId, idx){
	routMap.mapInfo[mapId].nodeChangeCb(idx);
}

routMap.setMapCursor = function(mapId, cursor){
	routMap.mapInfo[mapId].map.setCursor(cursor);
}

/**노드마커 추가**/
routMap.addMarkerInter = function(mapId, data, grid, idx, focusIdx, isOverLayHidden) {
	// 마커 이미지의 이미지 크기 입니다
	var imageSize = new kakao.maps.Size(26, 34); 
	var markerImage = null;
	var markerOverImage = null;
	var markerSelImage = null;
	
	var zIndex= -1;
	if(data.NODE_TYPE == routMap.NODE_TYPE.CROSS){
		zIndex = 1;
		imageSize = new kakao.maps.Size(22, 29);
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/cross.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/cross_selected.png", imageSize);
	}
	else if(data.NODE_TYPE == routMap.NODE_TYPE.BUSSTOP) {
		zIndex = 2;
		imageSize = new kakao.maps.Size(22, 29);
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop_selected.png", imageSize);
	}
	else if(data.NODE_TYPE == routMap.NODE_TYPE.VERTEX){
		imageSize = new kakao.maps.Size(18, 24); 
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/vertex.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/vertex_selected.png", imageSize);
	}
	else {
		imageSize = new kakao.maps.Size(22, 30); 
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/road_trans.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/road_selected.png", imageSize);
	}
	
	var marker = null;
	if(idx==focusIdx) {
		zIndex = 3;
		// 마커 이미지를 생성합니다    
		marker = new kakao.maps.Marker({
			position : new kakao.maps.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 // 설정.
			//title : data.label, // Marker의 라벨.
			image : markerSelImage,
			draggable : data.draggable,
			zIndex: zIndex
		});
		routMap.mapInfo[mapId].selectedMarker = marker;
	}
	else {
		// 마커 이미지를 생성합니다    
		marker = new kakao.maps.Marker({
			position : new kakao.maps.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 // 설정.
			//title : data.label, // Marker의 라벨.
			image : markerImage,
			draggable : data.draggable,
			zIndex: zIndex
		});
	}

	marker.normalImage = markerImage;


	var infoWindow = null;
		
	var overlay = null;
	if(data.NODE_TYPE != routMap.NODE_TYPE.BUSSTOP&&data.NODE_TYPE != routMap.NODE_TYPE.CROSS){
		var msg = "<div class = 'customoverlay'>"
					+ "<span class = 'map_title' style=''>" + data.NODE_NM + "</span>" 
					+ "</div>";	
					//+  "<span class = '' style='font-size: 12px; margin-left:2px; margin-bottom:2px; display:block;'>"+ data.GPS_Y + "," + data.GPS_X +"</span>"
		
		//infoWindow = new kakao.maps.InfoWindow({
		//	content: msg, //Popup 표시될 text
		//	zIndex : 4
		//});
		overlay = new kakao.maps.CustomOverlay({
			content: msg,
			position: marker.getPosition(),
			zIndex : 4
		});
		
	}
	else {
		var msg = "";
		if(data.NODE_TYPE == routMap.NODE_TYPE.BUSSTOP){
			 msg = "<div class = 'customoverlay busstop'>";
		}
		else if(data.NODE_TYPE == routMap.NODE_TYPE.CROSS){
			msg = "<div class = 'customoverlay cross'>";
		}
		if(data.draggable){
			
			msg += "<span class = 'map_title' style=''>" + data.NODE_NM
				+ "&nbsp;<a href='javascript:void(0);' onclick='routMap.nodeChange(\"" + mapId+"\","+ idx + ");' style='color:blue' target='_self'>변경</a> </span>"
				+ "</div>";	
				//+ "<span class = '' style='font-size: 12px; margin-left:2px; margin-bottom:2px; display:block;'>"+ data.GPS_Y + "," + data.GPS_X +"</span>"
		}
		else {
			msg += "<span class = 'map_title' style=''>" + data.NODE_NM + "</span>"
				+ "</div>";
				//+ "<span class = '' style='font-size: 12px; margin-left:2px; margin-bottom:2px; display:block;'>"+ data.GPS_Y + "," + data.GPS_X +"</span>"
		 }
		
		/*infoWindow = new kakao.maps.InfoWindow({
		content: msg, //Popup 표시될 text
		zIndex : zIndex
		});
		
		infoWindow.open(routMap.mapInfo[mapId].map, marker);*/
		
		overlay = new kakao.maps.CustomOverlay({
			content: msg,
			map: routMap.mapInfo[mapId].map,
			position: marker.getPosition(),
			zIndex : zIndex
		});
	}
	//routMap.mapInfo[mapId].infoWindow.setMap(routMap.mapInfo[mapId].map); 
	routMap.mapInfo[mapId].overlay = overlay;
	routMap.mapInfo[mapId].overArr.push(routMap.mapInfo[mapId].overlay);
	
	// 마커에 click 이벤트를 등록합니다
	kakao.maps.event.addListener(marker, 'click', function() {
		//infoWindow.close();

		if(routMap.mapInfo[mapId].dragging){
			data.click({
				marker: marker,
				nodeId: data.NODE_ID,
				index: data.index
			});
			routMap.mapInfo[mapId].isMove = false;
			routMap.mapInfo[mapId].oldSelectedIndex = routMap.mapInfo[mapId].selectedIndex; 
			routMap.mapInfo[mapId].selectedIndex = idx;
			grid.setFocusedCell(idx,"NODE_ID");
			return;
		}
		// 클릭된 마커가 없고, click 마커가 클릭된 마커가 아니면
		// 마커의 이미지를 클릭 이미지로 변경합니다
		if (!routMap.mapInfo[mapId].selectedMarker
				|| routMap.mapInfo[mapId].selectedMarker !== marker) {
			
			// 클릭된 마커 객체가 null이 아니면
			// 클릭된 마커의 이미지를 기본 이미지로 변경하고
			!!routMap.mapInfo[mapId].selectedMarker
					&& routMap.mapInfo[mapId].selectedMarker
							.setImage(routMap.mapInfo[mapId].selectedMarker.normalImage);
	
			// 현재 클릭된 마커의 이미지는 클릭 이미지로 변경합니다
			marker.setImage(markerSelImage);
		}
		
		
		//routMap.mapInfo[mapId].markers[routMap.mapInfo[mapId].selectedIndex].setImage(routMap.mapInfo[mapId].selectedMarker.normalImage);
		
		// 클릭된 마커를 현재 클릭된 마커 객체로 설정합니다
		routMap.mapInfo[mapId].selectedMarker = marker;

		marker.setZIndex(3);
		routMap.mapInfo[mapId].isMove = false;
		routMap.mapInfo[mapId].selectedIndex = idx;
		grid.setFocusedCell(idx,"NODE_ID");
	});
	
	kakao.maps.event.addListener(marker, 'dragstart', function() {
		overlay.setMap(null);
		//infoWindow.close();
		routMap.mapInfo[mapId].dragging = true;
	});
	
	kakao.maps.event.addListener(marker, 'dragend', function() {
		
	});
	
	if((data.NODE_TYPE != routMap.NODE_TYPE.CROSS)&&(data.NODE_TYPE != routMap.NODE_TYPE.BUSSTOP)||isOverLayHidden){
		kakao.maps.event.addListener(marker, 'mouseover', routMap.makeOverListener(routMap.mapInfo[mapId].map, marker, overlay));
		kakao.maps.event.addListener(marker, 'mouseout', routMap.makeOutListener(routMap.mapInfo[mapId],marker,overlay,markerImage));
	}
	
	marker.setMap(routMap.mapInfo[mapId].map); //Marker가 표시될 Map 설정.
	routMap.mapInfo[mapId].markers.push(marker);
}

/**사운드 노드마커 추가**/
routMap.addSoundMarkerInter = function(mapId, data, grid, idx, focusIdx) {
	// 마커 이미지의 이미지 크기 입니다
	var imageSize = new kakao.maps.Size(24, 35); 
	var markerImage = null;
	var markerOverImage = null;
	var markerSelImage = null;
	
	var zIndex= -1;
	if(data.NODE_TYPE == routMap.NODE_TYPE.CROSS){
		zIndex = 1;
		imageSize = new kakao.maps.Size(22, 29);
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/cross.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/cross_selected.png", imageSize);
	}
	else if(data.NODE_TYPE == routMap.NODE_TYPE.BUSSTOP) {
		zIndex = 2;
		imageSize = new kakao.maps.Size(22, 29);
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop_selected.png", imageSize);
	}
	else if(data.NODE_TYPE == routMap.NODE_TYPE.VERTEX){
		imageSize = new kakao.maps.Size(18, 24); 
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/vertex.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/vertex_selected.png", imageSize);
	}
	else if(data.NODE_TYPE == routMap.NODE_TYPE.SOUND){
		imageSize = new kakao.maps.Size(18, 24); 
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/voice_node.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/voice_node_selected.png", imageSize);
	}
	else {
		imageSize = new kakao.maps.Size(22, 30); 
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/road_trans.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/road_selected.png", imageSize);
	}
	
	var marker = null;
	if(idx==focusIdx) {
		zIndex = 3;
		// 마커 이미지를 생성합니다    
		marker = new kakao.maps.Marker({
			position : new kakao.maps.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 // 설정.
			//title : data.label, // Marker의 라벨.
			image : markerSelImage,
			draggable : data.draggable,
			zIndex: zIndex
		});
		routMap.mapInfo[mapId].selectedMarker = marker;
	}
	else {
		// 마커 이미지를 생성합니다    
		marker = new kakao.maps.Marker({
			position : new kakao.maps.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 // 설정.
			//title : data.label, // Marker의 라벨.
			image : markerImage,
			draggable : data.draggable,
			zIndex: zIndex
		});
	}

	marker.normalImage = markerImage;


	var infoWindow = null;
		
	var overlay = null;
	if(data.NODE_TYPE != routMap.NODE_TYPE.BUSSTOP&&data.NODE_TYPE != routMap.NODE_TYPE.CROSS&&data.NODE_TYPE != routMap.NODE_TYPE.SOUND){
		var msg = "<div class = 'customoverlay'>"
					+ "<span class = 'map_title' style=''>" + data.NODE_NM + "</span>" 
					+ "</div>";	
					//+  "<span class = '' style='font-size: 12px; margin-left:2px; margin-bottom:2px; display:block;'>"+ data.GPS_Y + "," + data.GPS_X +"</span>"
		
		//infoWindow = new kakao.maps.InfoWindow({
		//	content: msg, //Popup 표시될 text
		//	zIndex : 4
		//});
		overlay = new kakao.maps.CustomOverlay({
			content: msg,
			position: marker.getPosition(),
			zIndex : 4
		});
		
	}
	else {
		var msg = "";
		if(data.NODE_TYPE == routMap.NODE_TYPE.SOUND){
			 msg = "<div class = 'customoverlay sound'>";
		}
		else if(data.NODE_TYPE == routMap.NODE_TYPE.BUSSTOP){
			 msg = "<div class = 'customoverlay busstop'>";
		}
		else if(data.NODE_TYPE == routMap.NODE_TYPE.CROSS){
			msg = "<div class = 'customoverlay cross'>";
		}
		if(data.draggable){
			
			msg += "<span class = 'map_title' style=''>" + data.NODE_NM
				+ "&nbsp;<a href='javascript:void(0);' onclick='routMap.nodeChange(\"" + mapId+"\","+ idx + ");' style='color:blue' target='_self'>변경</a> </span>"
				+ "</div>";	
				//+ "<span class = '' style='font-size: 12px; margin-left:2px; margin-bottom:2px; display:block;'>"+ data.GPS_Y + "," + data.GPS_X +"</span>"
		}
		else {
			msg += "<span class = 'map_title' style=''>" + data.NODE_NM + "</span>"
				+ "</div>";
				//+ "<span class = '' style='font-size: 12px; margin-left:2px; margin-bottom:2px; display:block;'>"+ data.GPS_Y + "," + data.GPS_X +"</span>"
		 }
		
		/*infoWindow = new kakao.maps.InfoWindow({
		content: msg, //Popup 표시될 text
		zIndex : zIndex
		});
		
		infoWindow.open(routMap.mapInfo[mapId].map, marker);*/
		
		overlay = new kakao.maps.CustomOverlay({
			content: msg,
			map: routMap.mapInfo[mapId].map,
			position: marker.getPosition(),
			zIndex : zIndex
		});
	}
	//routMap.mapInfo[mapId].infoWindow.setMap(routMap.mapInfo[mapId].map); 
	routMap.mapInfo[mapId].overlay = overlay;
	routMap.mapInfo[mapId].overArr.push(routMap.mapInfo[mapId].overlay);
	
	if(data.draggable){
		kakao.maps.event.addListener(marker, 'dblclick', function(mapId,idx) {
			
			routMap.nodeChange(mapId,idx);
		});
	}
	
	// 마커에 click 이벤트를 등록합니다
	kakao.maps.event.addListener(marker, 'click', function() {
		//infoWindow.close();

		if(routMap.mapInfo[mapId].dragging){
			data.click({
				marker: marker,
				nodeId: data.NODE_ID,
				index: data.index
			});
			routMap.mapInfo[mapId].isMove = false;
			routMap.mapInfo[mapId].oldSelectedIndex = routMap.mapInfo[mapId].selectedIndex;
			routMap.mapInfo[mapId].selectedIndex = idx;
			grid.setFocusedCell(idx,"NODE_ID");
			return;
		}
		// 클릭된 마커가 없고, click 마커가 클릭된 마커가 아니면
		// 마커의 이미지를 클릭 이미지로 변경합니다
		if (!routMap.mapInfo[mapId].selectedMarker
				|| routMap.mapInfo[mapId].selectedMarker !== marker) {
			
			// 클릭된 마커 객체가 null이 아니면
			// 클릭된 마커의 이미지를 기본 이미지로 변경하고
			!!routMap.mapInfo[mapId].selectedMarker
					&& routMap.mapInfo[mapId].selectedMarker
							.setImage(routMap.mapInfo[mapId].selectedMarker.normalImage);
	
			// 현재 클릭된 마커의 이미지는 클릭 이미지로 변경합니다
			marker.setImage(markerSelImage);
		}
		
		
		//routMap.mapInfo[mapId].markers[routMap.mapInfo[mapId].selectedIndex].setImage(routMap.mapInfo[mapId].selectedMarker.normalImage);
		
		// 클릭된 마커를 현재 클릭된 마커 객체로 설정합니다
		routMap.mapInfo[mapId].selectedMarker = marker;

		marker.setZIndex(3);
		routMap.mapInfo[mapId].isMove = false;
		routMap.mapInfo[mapId].selectedIndex = idx;
		grid.setFocusedCell(idx,"NODE_ID");
	});
	
	if(data.NODE_TYPE == routMap.NODE_TYPE.SOUND){
		kakao.maps.event.addListener(marker, 'dragstart', function() {
			overlay.setMap(null);
			//infoWindow.close();
			routMap.mapInfo[mapId].dragging = true;
		});
		
		kakao.maps.event.addListener(marker, 'dragend', function() {
			
		});
	}
	if((data.NODE_TYPE != routMap.NODE_TYPE.CROSS)&&(data.NODE_TYPE != routMap.NODE_TYPE.BUSSTOP)&&(data.NODE_TYPE != routMap.NODE_TYPE.SOUND)){
		kakao.maps.event.addListener(marker, 'mouseover', routMap.makeOverListener(routMap.mapInfo[mapId].map, marker, overlay));
		kakao.maps.event.addListener(marker, 'mouseout', routMap.makeOutListener(routMap.mapInfo[mapId],marker,overlay,markerImage));
	}
	
	marker.setMap(routMap.mapInfo[mapId].map); //Marker가 표시될 Map 설정.
	routMap.mapInfo[mapId].markers.push(marker);
}

//인포윈도우를 표시하는 클로저를 만드는 함수입니다 
routMap.makeOverListener = function(map, marker, overlay) {
	return function() {
		overlay.setMap(map)
		//infoWindow.open(map, marker);
	};
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다
routMap.makeOutListener = function(mapInfo,mapInfo,overlay,markerImage) {
	return function() {
		overlay.setMap(null)
		//infoWindow.close();
        // 클릭된 마커가 없고, mouseout된 마커가 클릭된 마커가 아니면
        // 마커의 이미지를 기본 이미지로 변경합니다
        /*if (!mapInfo.selectedMarker || mapInfo.selectedMarker !== marker) {
            marker.setImage(markerImage);
        }*/
	};
}

/**통통튀는 마커 생성**/
routMap.addMarkerAni = function(mapId, lat, lng, id) {
	var aniType = kakao.maps.MarkerOptions.ANIMATE_BOUNCE;
	var coordIdx = 0;

	//routMap.removeMarkers(); // 지도에 새로 등록하기 위해 모든 마커를 지우는 함수입니다.
      
	
	var func = function() {
		//Marker 객체 생성.
		var marker = new kakao.maps.Marker({
			position: new kakao.maps.LatLng(lat, lng), //Marker의 중심좌표 설정.
			draggable: routMap.mapInfo[mapId].draggable, //Marker의 드래그 가능 여부.
			animation: aniType, //Marker 애니메이션.
			animationLength: 500, //애니메이션 길이.
			//label: "정류장", //Marker의 라벨.
			title: id //Marker 타이틀.
		});
		marker.setMap(routMap.mapInfo[mapId].map); //Marker가 표시될 Map 설정.
		routMap.mapInfo[mapId].markers.push(marker);

		
	}
	// 일정 시간 간격으로 마커를 생성하는 함수를 실행합니다
	setTimeout(func, 300);
}


/**버스마커 **/
routMap.showBusMarker = function(mapId, data, idx, focusIdx, busGrid) {
	// 마커 이미지의 이미지 크기 입니다
	var imageSize = new kakao.maps.Size(24, 35); 
	var markerImage = null;
	var markerOverImage = null;
	var markerSelImage = null;
	
	var zIndex= 5;
	if(data.VHC_KIND == "VHK01"){
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/bus_red.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/bus_red_selected.png", imageSize);
	}

	else {
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/bus_red.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/bus_red_selected.png", imageSize);
	}
	
	var marker = null;
	if(idx==focusIdx) {
		zIndex = 6;
		// 마커 이미지를 생성합니다    
		marker = new kakao.maps.Marker({
			position : new kakao.maps.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 // 설정.
			//title : data.label, // Marker의 라벨.
			image : markerSelImage,
			draggable : data.draggable,
			zIndex: zIndex
		});
		routMap.mapInfo[mapId].selectedBusMarker = marker;
	}
	else {
		// 마커 이미지를 생성합니다    
		marker = new kakao.maps.Marker({
			position : new kakao.maps.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 // 설정.
			//title : data.label, // Marker의 라벨.
			image : markerImage,
			draggable : data.draggable,
			zIndex: zIndex
		});
	}

	marker.normalImage = markerImage;
	
	var overlayName = null;
	var overlay = null;
	
	if (typeof data.VHC_NO != "undefined") {
		overlayName = data.VHC_NO
	} else if (typeof data.RPC_VHC_NO != "undefined") {
		overlayName = data.RPC_VHC_NO		
	}
	
	
	var msg = "<div class = 'busoverlay'>"
			+ "<span class = 'map_title' style=''>" + overlayName + "</span>"
			+ "</div>";
	

	overlay = new kakao.maps.CustomOverlay({
		content: msg,
		map: routMap.mapInfo[mapId].map,
		position: marker.getPosition(),
		zIndex : zIndex
	});

	//routMap.mapInfo[mapId].infoWindow.setMap(routMap.mapInfo[mapId].map); 
	routMap.mapInfo[mapId].busOverlay = overlay;
	routMap.mapInfo[mapId].busOverlay.setMap(routMap.mapInfo[mapId].map);
	if(idx<routMap.mapInfo[mapId].busOverArr.length){
		routMap.mapInfo[mapId].busOverArr[idx] = routMap.mapInfo[mapId].busOverlay;
	}
	else{
		routMap.mapInfo[mapId].busOverArr.push(routMap.mapInfo[mapId].busOverlay);
	}
	
	
	if(idx!=focusIdx) {
		kakao.maps.event.addListener(marker, 'mouseover', routMap.makeOverListener(routMap.mapInfo[mapId].map, marker, overlay));
		kakao.maps.event.addListener(marker, 'mouseout', routMap.makeOutListener(routMap.mapInfo[mapId],marker,overlay,markerImage));
	}
	
	marker.setMap(routMap.mapInfo[mapId].map); //Marker가 표시될 Map 설정.
	
	if(idx<routMap.mapInfo[mapId].busMarkers.length){
		routMap.mapInfo[mapId].busMarkers[idx] = marker;
	}
	else{
		routMap.mapInfo[mapId].busMarkers.push(marker);
	}
	
	/*
	// 마커에 click 이벤트를 등록합니다
	// 여기부터 클릭 이벤트 수정해야함
	// grid도 나중에 추가한거임 필요없으면 빼야됨
	kakao.maps.event.addListener(marker, 'click', function() {
		
		busGrid.setFocusedCell(idx,"VHC_ID");		
		if(routMap.mapInfo[mapId].dragging){
			data.click({
				marker: marker,
				nodeId: data.NODE_ID,
				index: data.index
			});
			routMap.mapInfo[mapId].isMove = false;
			routMap.mapInfo[mapId].selectedIndex = idx;
			busGrid.setFocusedCell(idx,"VHC_ID");
			return;
		}
		// 클릭된 마커가 없고, click 마커가 클릭된 마커가 아니면
		// 마커의 이미지를 클릭 이미지로 변경합니다
		if (!routMap.mapInfo[mapId].selectedMarker
				|| routMap.mapInfo[mapId].selectedMarker !== marker) {
			
			// 클릭된 마커 객체가 null이 아니면
			// 클릭된 마커의 이미지를 기본 이미지로 변경하고
			!!routMap.mapInfo[mapId].selectedMarker
					&& routMap.mapInfo[mapId].selectedMarker
							.setImage(routMap.mapInfo[mapId].selectedMarker.normalImage);
	
			// 현재 클릭된 마커의 이미지는 클릭 이미지로 변경합니다
			marker.setImage(markerSelImage);
		}
		
		
		//routMap.mapInfo[mapId].markers[routMap.mapInfo[mapId].selectedIndex].setImage(routMap.mapInfo[mapId].selectedMarker.normalImage);
		
		// 클릭된 마커를 현재 클릭된 마커 객체로 설정합니다
		routMap.mapInfo[mapId].selectedMarker = marker;

		marker.setZIndex(3);
		routMap.mapInfo[mapId].isMove = false;
		routMap.mapInfo[mapId].selectedIndex = idx;
		busGrid.setFocusedCell(idx,"VHC_ID");
	});			
	
	*/
}

routMap.showCategory = function(mapId, list) {
	$("#"+mapId).find("#category").empty();
	
	// 좌상단 카테고리 관련
	var categoryContent = null;
	var categoryClass = null;
	var categoryName = null;
	
	var crossCheck = 0;
	var normalCheck = 0;
	var vertexCheck = 0;
	var roadCheck = 0;
	var voiceCheck = 0;
	var abnormalCheck = 0; 
	
	if(list != null && list.length != 0) {
		for(var i = 0; i < list.length; i++) {
			
			if(crossCheck == 0 && list[i].NODE_TYPE == routMap.NODE_TYPE.CROSS){
				categoryClass = "crossimg";
				categoryName = "교차로";	
				categoryContent =	'<li id="">' ;
				categoryContent +=	'<span class="' +categoryClass +'"></span>';
				categoryContent +=	categoryName;
				categoryContent +=	'</li>';   
				crossCheck = 1;
				$("#"+mapId).find("#category").append(categoryContent);
			}
			else if(normalCheck == 0 && list[i].NODE_TYPE == routMap.NODE_TYPE.BUSSTOP){
				categoryClass = "normal_busimg";
				categoryName = "정류장";
				categoryContent =	'<li id="">' ;
				categoryContent +=	'<span class="' +categoryClass +'"></span>';
				categoryContent +=	categoryName;
				categoryContent +=	'</li>';
				normalCheck = 1;
				$("#"+mapId).find("#category").append(categoryContent);
			}
			else if(vertexCheck == 0 && list[i].NODE_TYPE == routMap.NODE_TYPE.VERTEX){
				categoryClass = "roadimg";
				categoryName = "경로";			
				categoryContent =	'<li id="">' ;
				categoryContent +=	'<span class="' +categoryClass +'"></span>';
				categoryContent +=	categoryName;
				categoryContent +=	'</li>';
				vertexCheck = 1;
				$("#"+mapId).find("#category").append(categoryContent);
			}
			else if(voiceCheck == 0 && list[i].NODE_TYPE == routMap.NODE_TYPE.SOUND){
				categoryClass = "voiceimg"
				categoryName = "음성";			
				categoryContent =	'<li id="">' ;
				categoryContent +=	'<span class="' +categoryClass +'"></span>';
				categoryContent +=	categoryName;
				categoryContent +=	'</li>';
				voiceCheck = 1;
				$("#"+mapId).find("#category").append(categoryContent);
			}
			
			if(abnormalCheck == 0 && typeof list[i].COND_ERROR != "undefined" && list[i].COND_ERROR == "Y") {
				categoryClass = "abnormal_busimg"
				categoryName = "고장위치";
				categoryContent =	'<li id="">' ;
				categoryContent +=	'<span class="' +categoryClass +'"></span>';
				categoryContent +=	categoryName;
				categoryContent +=	'</li>';
				abnormalCheck = 1;
				$("#"+mapId).find("#category").append(categoryContent);
			}
		} // end for
	} //end if
	
    var category = document.getElementById('category'),
    children = category.children;
    
    for (var i=0; i<children.length; i++) {
       // children[i].onclick = onClickCategory;
	}

} //showCategory

routMap.showMarker = function(mapId, data, idx, focusIdx, grid) {

	// 마커 이미지의 이미지 크기 입니다
	var imageSize = new kakao.maps.Size(24, 35); 
	var markerImage = null;
	var markerOverImage = null;
	var markerSelImage = null;

	
	var zIndex= -1;
	if(data.NODE_TYPE == routMap.NODE_TYPE.CROSS){
		zIndex = 1;
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/cross.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/cross_selected.png", imageSize);
	}
	else if(data.NODE_TYPE == routMap.NODE_TYPE.BUSSTOP) {
		zIndex = 2;
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop_selected.png", imageSize);
	}
	else if(data.NODE_TYPE == routMap.NODE_TYPE.VERTEX){
		imageSize = new kakao.maps.Size(18, 30); 
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/vertex.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/vertex_selected.png", imageSize);

	} 
	else {
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/road_trans.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/road_selected.png", imageSize);

	} 
	if(typeof data.COND_ERROR != "undefined" && data.COND_ERROR == "Y") {
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop_black.png", imageSize);		
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop_black.png", imageSize);

	}

		
	var marker = null;
	if(idx==focusIdx) {
		zIndex = 3;
		// 마커 이미지를 생성합니다    
		marker = new kakao.maps.Marker({
			position : new kakao.maps.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 // 설정.
			//title : data.label, // Marker의 라벨.
			image : markerSelImage,
			draggable : data.draggable,
			zIndex: zIndex
		});
		routMap.mapInfo[mapId].selectedMarker = marker;
	}
	else {
		// 마커 이미지를 생성합니다    
		marker = new kakao.maps.Marker({
			position : new kakao.maps.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 // 설정.
			//title : data.label, // Marker의 라벨.
			image : markerImage,
			draggable : data.draggable,
			zIndex: zIndex
		});
	}

	marker.normalImage = markerImage;


	var infoWindow = null;

	var overlay = null;
	var msg = "";
	if(data.NODE_TYPE == routMap.NODE_TYPE.BUSSTOP){
		 msg = "<div class = 'customoverlay busstop'>";
		 
		 //장치상태 에러일때
		 if(data.COND_ERROR == 'Y') {
			 msg = "";
			 msg = "<div class = 'customoverlay conderror busstop'>";
		 }
	}
	else if(data.NODE_TYPE == routMap.NODE_TYPE.CROSS){
		msg = "<div class = 'customoverlay cross'>";
		
		 if(data.COND_ERROR == 'Y') {
			 msg = "";
			 msg = "<div class = 'customoverlay conderror busstop'>";
		 }		
	}
	if(data.draggable){
		
		msg += "<span class = 'map_title' style=''>" + data.NODE_NM
			+ "&nbsp;<a href='javascript:void(0);' onclick='routMap.nodeChange(\"" + mapId+"\","+ idx + ");' style='color:blue' target='_self'>변경</a> </span>"
			+ "</div>";	
			//+ "<span class = '' style='font-size: 12px; margin-left:2px; margin-bottom:2px; display:block;'>"+ data.GPS_Y + "," + data.GPS_X +"</span>"
	}
	else {
		msg += "<span class = 'map_title' style=''>" + data.NODE_NM + "</span>"
			+ "</div>";
			//+ "<span class = '' style='font-size: 12px; margin-left:2px; margin-bottom:2px; display:block;'>"+ data.GPS_Y + "," + data.GPS_X +"</span>"
	 }
	
	if(idx==focusIdx) {
		overlay = new kakao.maps.CustomOverlay({
			content: msg,
			map: routMap.mapInfo[mapId].map,
			position: marker.getPosition(),
			zIndex : zIndex
		});
	}
	else {
		overlay = new kakao.maps.CustomOverlay({
			content: msg,
			position: marker.getPosition(),
			zIndex : 4
		});
		
	}
	// 마커에 click 이벤트를 등록합니다
	kakao.maps.event.addListener(marker, 'click', function() {
		//infoWindow.close();
		// 클릭된 마커가 없고, click 마커가 클릭된 마커가 아니면
		// 마커의 이미지를 클릭 이미지로 변경합니다
		if (!routMap.mapInfo[mapId].selectedMarker
				|| routMap.mapInfo[mapId].selectedMarker !== marker) {
			
			// 클릭된 마커 객체가 null이 아니면
			// 클릭된 마커의 이미지를 기본 이미지로 변경하고
			!!routMap.mapInfo[mapId].selectedMarker
					&& routMap.mapInfo[mapId].selectedMarker
							.setImage(routMap.mapInfo[mapId].selectedMarker.normalImage);
			
			// 현재 클릭된 마커의 이미지는 클릭 이미지로 변경합니다
			marker.setImage(markerSelImage);
		}
		
		
		//routMap.mapInfo[mapId].markers[routMap.mapInfo[mapId].selectedIndex].setImage(routMap.mapInfo[mapId].selectedMarker.normalImage);
		
		// 클릭된 마커를 현재 클릭된 마커 객체로 설정합니다
		routMap.mapInfo[mapId].selectedMarker = marker;
		marker.setZIndex(3);
		routMap.mapInfo[mapId].isMove = false;
		//routMap.mapInfo[mapId].overArr[routMap.mapInfo[mapId].selectedIndex].setMap(null);
		routMap.mapInfo[mapId].oldSelectedIndex = routMap.mapInfo[mapId].selectedIndex;
		routMap.mapInfo[mapId].selectedIndex = idx;
		if(typeof grid != "undefined") {
			grid.setFocusedCell(idx,"NODE_ID");
		}
	});
	
	/*kakao.maps.event.addListener(marker, 'dragstart', function() {
		overlay.setMap(null);
		//infoWindow.close();
		routMap.mapInfo[mapId].dragging = true;
	});
	
	kakao.maps.event.addListener(marker, 'dragend', function() {
		
	});	*/
	//routMap.mapInfo[mapId].infoWindow.setMap(routMap.mapInfo[mapId].map); 
	routMap.mapInfo[mapId].overlay = overlay;
	routMap.mapInfo[mapId].overArr.push(routMap.mapInfo[mapId].overlay);
	
	if(idx==focusIdx) {
		routMap.mapInfo[mapId].overArr[focusIdx].setMap(routMap.mapInfo[mapId].map);
	}
	else {
		kakao.maps.event.addListener(marker, 'mouseover', routMap.makeOverListener(routMap.mapInfo[mapId].map, marker, overlay));
		kakao.maps.event.addListener(marker, 'mouseout', routMap.makeOutListener(routMap.mapInfo[mapId],marker,overlay,markerImage));
	}
	
	marker.setMap(routMap.mapInfo[mapId].map); //Marker가 표시될 Map 설정.
	routMap.mapInfo[mapId].markers.push(marker);
}


/**노드마커 **/
routMap.showMarker2 = function(mapId, data, idx) {

	// 마커 이미지의 이미지 크기 입니다
	var imageSize = new kakao.maps.Size(24, 35); 
	var markerImage = null;
	var markerOverImage = null;
	var markerSelImage = null;
	
	var zIndex= -1;
	if(data.NODE_TYPE == routMap.NODE_TYPE.CROSS){
		zIndex = 1;
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/cross.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/cross_selected.png", imageSize);
	}
	else if(data.NODE_TYPE == routMap.NODE_TYPE.BUSSTOP) {
		zIndex = 2;
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop_selected.png", imageSize);
	}
	else if(data.NODE_TYPE == routMap.NODE_TYPE.VERTEX){
		imageSize = new kakao.maps.Size(18, 30); 
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/vertex.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/vertex_selected.png", imageSize);
	}
	else {
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/road_trans.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/road_selected.png", imageSize);
	}
	
	var marker = null;
	
	// 마커 이미지를 생성합니다    
	marker = new kakao.maps.Marker({
		position : new kakao.maps.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 // 설정.
		//title : data.label, // Marker의 라벨.
		image : markerImage,
		draggable : data.draggable,
		zIndex: zIndex
	});


	marker.normalImage = markerImage;


	var infoWindow = null;

	var overlay = null;
	var msg = "";
	if(data.NODE_TYPE == routMap.NODE_TYPE.BUSSTOP){
		 msg = "<div class = 'customoverlay busstop'>";
		 
		 //장치상태 에러일때
		 if(data.COND_ERROR == 'Y') {
			 msg = "";
			 msg = "<div class = 'customoverlay conderror busstop'>";
		 }
	}
	else if(data.NODE_TYPE == routMap.NODE_TYPE.CROSS){
		msg = "<div class = 'customoverlay cross'>";
		
		 if(data.COND_ERROR == 'Y') {
			 msg = "";
			 msg = "<div class = 'customoverlay conderror busstop'>";
		 }		
	}
	if(data.draggable){
		
		msg += "<span class = 'map_title' style=''>" + data.NODE_NM
			+ "&nbsp;<a href='javascript:void(0);' onclick='routMap.nodeChange(\"" + mapId+"\","+ idx + ");' style='color:blue' target='_self'>변경</a> </span>"
			+ "</div>";	
			//+ "<span class = '' style='font-size: 12px; margin-left:2px; margin-bottom:2px; display:block;'>"+ data.GPS_Y + "," + data.GPS_X +"</span>"
	}
	else {
		msg += "<span class = 'map_title' style=''>" + data.NODE_NM + "</span>"
			+ "</div>";
			//+ "<span class = '' style='font-size: 12px; margin-left:2px; margin-bottom:2px; display:block;'>"+ data.GPS_Y + "," + data.GPS_X +"</span>"
	 }
	
	overlay = new kakao.maps.CustomOverlay({
		content: msg,
		position: marker.getPosition(),
		zIndex : 4
	});
		
	kakao.maps.event.addListener(marker, 'mouseover', routMap.makeOverListener(routMap.mapInfo[mapId].map, marker, overlay));
	kakao.maps.event.addListener(marker, 'mouseout', routMap.makeOutListener(routMap.mapInfo[mapId],marker,overlay,markerImage));
	
	marker.setMap(routMap.mapInfo[mapId].map); //Marker가 표시될 Map 설정.
	routMap.mapInfo[mapId].markers.push(marker);
}

routMap.showFacilityMarker = function(mapId, data, idx, focusIdx, busGrid) {
	// 마커 이미지의 이미지 크기 입니다
	var imageSize = new kakao.maps.Size(24, 35); 
	var markerImage = null;
	var markerOverImage = null;
	var markerSelImage = null;
	
	var zIndex= 5;
	if(data.VHC_KIND == "VHK01"){
		zIndex = 2;
		imageSize = new kakao.maps.Size(22, 29);
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop_selected.png", imageSize);
	}

	else {
		zIndex = 2;
		imageSize = new kakao.maps.Size(22, 29);
		markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop.png", imageSize);
		markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/busstop_selected.png", imageSize);
	}
	
	var marker = null;
	if(idx==focusIdx) {
		zIndex = 6;
		// 마커 이미지를 생성합니다    
		marker = new kakao.maps.Marker({
			position : new kakao.maps.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 // 설정.
			//title : data.label, // Marker의 라벨.
			image : markerSelImage,
			draggable : data.draggable,
			zIndex: zIndex
		});
		routMap.mapInfo[mapId].selectedBusMarker = marker;
	}
	else {
		// 마커 이미지를 생성합니다    
		marker = new kakao.maps.Marker({
			position : new kakao.maps.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 // 설정.
			//title : data.label, // Marker의 라벨.
			image : markerImage,
			draggable : data.draggable,
			zIndex: zIndex
		});
	}

	marker.normalImage = markerImage;
	var overlay = null;
	var msg = "<div class = 'busoverlay'>"
			+ "<span class = 'map_title' style=''>" + data.NODE_NM + "</span>"
			+ "</div>";
	

	overlay = new kakao.maps.CustomOverlay({
		content: msg,
		map: routMap.mapInfo[mapId].map,
		position: marker.getPosition(),
		zIndex : zIndex
	});

	//routMap.mapInfo[mapId].infoWindow.setMap(routMap.mapInfo[mapId].map); 
	routMap.mapInfo[mapId].busOverlay = overlay;
	routMap.mapInfo[mapId].busOverlay.setMap(routMap.mapInfo[mapId].map);
	if(idx<routMap.mapInfo[mapId].busOverArr.length){
		routMap.mapInfo[mapId].busOverArr[idx] = routMap.mapInfo[mapId].busOverlay;
	}
	else{
		routMap.mapInfo[mapId].busOverArr.push(routMap.mapInfo[mapId].busOverlay);
	}
	
	
	if(idx!=focusIdx) {
		kakao.maps.event.addListener(marker, 'mouseover', routMap.makeOverListener(routMap.mapInfo[mapId].map, marker, overlay));
		kakao.maps.event.addListener(marker, 'mouseout', routMap.makeOutListener(routMap.mapInfo[mapId],marker,overlay,markerImage));
	}
	
	marker.setMap(routMap.mapInfo[mapId].map); //Marker가 표시될 Map 설정.
	
	if(idx<routMap.mapInfo[mapId].busMarkers.length){
		routMap.mapInfo[mapId].busMarkers[idx] = marker;
	}
	else{
		routMap.mapInfo[mapId].busMarkers.push(marker);
	}
	
}

/**지도위 팝업 생성**/
routMap.popUp = function(mapId,lat, lng, msg){

	var content =
		"<div class = 'popUp'>" +
		"<span class = 'popUp' style='font-weight:bold;'>" + msg + "</span>" + 
		"<span class = 'popUp' style='font-size: 12px; margin-left:2px; margin-bottom:2px; display:block;'>"+ lat + "," + lng +"</span>"
		+ "</div>";
				

	routMap.mapInfo[mapId].infoWindow = new kakao.maps.InfoWindow({
		position: new kakao.maps.LatLng(lat, lng), //Popup 이 표출될 맵 좌표
		content: content //Popup 표시될 text
	});
	
	//routMap.mapInfo[mapId].infoWindow.setMap(routMap.mapInfo[mapId].map); 
	routMap.mapInfo[mapId].infoArr.push(routMap.mapInfo[mapId].infoWindow);
}

/**팝업 전체 삭제**/
routMap.removeAllInfoWindow = function(mapId){
	if(routMap.mapInfo[mapId].infoArr != null){
		for(var i=0; i<routMap.mapInfo[mapId].infoArr.length; i++){
			routMap.mapInfo[mapId].infoArr[i].close();
			routMap.mapInfo[mapId].infoArr[i] = null;
		}
		routMap.mapInfo[mapId].infoArr = [];
	}
}

routMap.removeAllOverlay = function(mapId){
	if(routMap.mapInfo[mapId].overArr != null){
		for(var i=0; i<routMap.mapInfo[mapId].overArr.length; i++){
			routMap.mapInfo[mapId].overArr[i].setMap(null);
			routMap.mapInfo[mapId].overArr[i] = null;
		}
		routMap.mapInfo[mapId].overArr = [];
	}
}

routMap.removeAllBusOverlay = function(mapId){
	if(routMap.mapInfo[mapId].busOverArr != null){
		for(var i=0; i<routMap.mapInfo[mapId].busOverArr.length; i++){
			routMap.mapInfo[mapId].busOverArr[i].setMap(null);
			routMap.mapInfo[mapId].busOverArr[i] = null;
		}
		routMap.mapInfo[mapId].busOverArr = [];
	}
}

routMap.removeIndexBusOverlay = function(mapId,index){
	debugger;
	if(routMap.mapInfo[mapId].busOverArr != null&&routMap.mapInfo[mapId].busOverArr.length!=0&&routMap.mapInfo[mapId].busOverArr[index]!=null){
		routMap.mapInfo[mapId].busOverArr[index].setMap(null);
		routMap.mapInfo[mapId].busOverArr[index] = null;
	}
}

routMap.addMarker = function(mapId, data) {
	
	var marker = new kakao.maps.Marker({
		position: new kakao.maps.LatLng(data.GPS_Y, data.GPS_X), // Marker의 중심좌표 설정.
		label: data.label, // Marker의 라벨.
		map: routMap.mapInfo[mapId].map,
		icon: data.icon,
		draggable: data.draggable,
	});
	
	marker.setMap(routMap.mapInfo[mapId].map); //Marker가 표시될 Map 설정.
	
	if(data.click) {
		marker.addListener("click", function(e) {
			data.click({
				marker: marker,
				nodeId: data.nodeId,
				index: data.index
			});
		});
	}
	
	routMap.mapInfo[mapId].markers.push(marker);
}

routMap.deleteCircle = function(mapId) {
	if( routMap.mapInfo[mapId].circles != null &&  routMap.mapInfo[mapId].circles.length != 0) {
		for(var i = 0; i <  routMap.mapInfo[mapId].circles.length; i++) {
			if(typeof  routMap.mapInfo[mapId].circles[i].setMap !== "undefined") 
				 routMap.mapInfo[mapId].circles[i].setMap(null);
		}
		 routMap.mapInfo[mapId].circles = [];
	}
}

routMap.deleteNode = function(mapId) {
	if( routMap.mapInfo[mapId].nodes != null &&  routMap.mapInfo[mapId].nodes.length != 0) {
		for(var i = 0; i < routMap.mapInfo[mapId].nodes.length; i++) {
			if(typeof routMap.mapInfo[mapId].nodes[i].setMap !== "undefined") 
				routMap.mapInfo[mapId].nodes[i].setMap(null);
		}
		routMap.mapInfo[mapId].nodes = [];
	}
}

routMap.getDrawingCircle = function(mapId, lat, lon, radius) {
	var circle = new kakao.maps.Circle({
		center: new kakao.maps.LatLng(lat, lon),
		radius: radius,
		strokeColor: "#A872EE",
		strokeWeight: 2,
		fillColor: "#A872EE",
		fillOpacity: 0.2
	});
	
	circle.setMap(routMap.mapInfo[mapId].map);
	
	return circle;
}

routMap.getDrawingNode = function(mapId, lat, lon) {
	var node = new kakao.maps.Circle({
		center: new kakao.maps.LatLng(lat, lon),
		radius: 4,
		strokeColor: "#FF005E",
		strokeWeight: 3,
		fillColor: "#FFFFFF",
		fillOpacity: 1
	});
	
	// 지도에 원을 표시합니다 
	node.setMap(routMap.mapInfo[mapId].map);
	
	return node;
}

routMap.returnInsertRouteInfo = function(lat, lon, routeData, curIndex) {
	var min = 10000000;
	var minIndex = null;
	
	for(var i = 0; i < routeData.length - 1; i++) {
		if(i==curIndex)continue;
		
		var plusVal = 1;
		if((i + 1)==curIndex)plusVal=2;
			
		var result = getDistanceToLine(
			lat,
			lon,
			routeData[i].GPS_Y,
			routeData[i].GPS_X,
			routeData[i+plusVal].GPS_Y,
			routeData[i+plusVal].GPS_X
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

routMap.addPathByClick = function(mapId,grid,routeId,e){
	
	if(com.getGridViewDataList(grid).length >= routMap.MAX_NODE_CNT){
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
	
			ROUT_ID: routeId,
			NODE_SN: idx,
			NODE_NM: /*routNm + */"노드_" + getCurrentDate().substring(4),
			NODE_TYPE: routMap.NODE_TYPE.NORMAL,
			GPS_Y: getDispGps(lonlat.Ma,7),
			GPS_X: getDispGps(lonlat.La,7),
			draggable:routMap.mapInfo[mapId].draggable
			};

	com.getGridViewDataList(grid).setRowJSON(idx, data, true);
	
	//routeData = com.getGridDispJsonData(grid);
	routMap.drawRoute(mapId, grid, idx);
}

routMap.addVertexByClick = function(mapId,grid,routeId,e){
	
	var routeData = com.getGridDispJsonData(grid);
	if(routeData.length >= routMap.MAX_NODE_CNT){
		//com.alert ("더이상 추가할 수 없습니다.");
		return false;
	}
	
	var lonlat = e.latLng;
	var min = 10000000;
	var minIndex = null;

	for(var i = 0; i < routeData.length - 1; i++) {
		var result = getDistanceToLine(
			lonlat.Ma,
			lonlat.La,
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
		com.alert("선택할 수 없는 좌표입니다. 경로를 먼저 입력하세요");
	} else {
		var idx = minIndex + 1;;
		
		idx = com.getGridViewDataList(grid).insertRow(idx);
	
		var today = new Date();
		var data = {
		
				ROUT_ID: routeId,
				NODE_SN: idx,
				NODE_NM: /*routNm + */"버텍스_" + getCurrentDate().substring(4),
				NODE_TYPE: routMap.NODE_TYPE.VERTEX,
				GPS_Y: getDispGps(lonlat.Ma,7),
				GPS_X: getDispGps(lonlat.La,7),
				draggable:routMap.mapInfo[mapId].draggable
				};
	
		com.getGridViewDataList(grid).setRowJSON(idx, data, true);
		
		//routeData = com.getGridDispJsonData(grid);
		routMap.drawRoute(mapId, grid, idx);
	}
}

routMap.addSttnByClick = function(mapId,grid,routeId,e){
	
	var idx = -1;
	var routeData = com.getGridDispJsonData(grid);
	if(routeData.length >= routMap.MAX_NODE_CNT){
		//com.alert ("더이상 추가할 수 없습니다.");
		return idx;
	}
	
	var lonlat = e.latLng;
	var min = 10000000;
	var minIndex = null;

	for(var i = 0; i < routeData.length - 1; i++) {
		var result = getDistanceToLine(
			lonlat.Ma,
			lonlat.La,
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
		com.alert("선택할 수 없는 좌표입니다. 경로를 먼저 입력하세요");
	} else {
		idx = minIndex + 1;;
		
		idx = com.getGridViewDataList(grid).insertRow(idx);
	
		var today = new Date();
		var data = {
		
				ROUT_ID: routeId,
				NODE_SN: idx,
				NODE_NM: /*routNm + */"정류소_" + getCurrentDate().substring(4),
				NODE_TYPE: routMap.NODE_TYPE.BUSSTOP,
				GPS_Y: getDispGps(lonlat.Ma,7),
				GPS_X: getDispGps(lonlat.La,7),
				draggable:routMap.mapInfo[mapId].draggable
				};
	
		com.getGridViewDataList(grid).setRowJSON(idx, data, true);
		
		//routeData = com.getGridDispJsonData(grid);
		//routMap.drawRoute(mapId, grid, idx);
	}
	return idx;
}

routMap.addCrossByClick = function(mapId,grid,routeId,e){
	var idx = -1;
	var routeData = com.getGridDispJsonData(grid);
	if(routeData.length >= routMap.MAX_NODE_CNT){
		//com.alert ("더이상 추가할 수 없습니다.");
		return idx;
	}
	
	var lonlat = e.latLng;
	var min = 10000000;
	var minIndex = null;

	for(var i = 0; i < routeData.length - 1; i++) {
		var result = getDistanceToLine(
			lonlat.Ma,
			lonlat.La,
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
		com.alert("선택할 수 없는 좌표입니다. 경로를 먼저 입력하세요");
	} else {
		idx = minIndex + 1;;
		
		idx = com.getGridViewDataList(grid).insertRow(idx);
	
		var today = new Date();
		var data = {
		
				ROUT_ID: routeId,
				NODE_SN: idx,
				NODE_NM: /*routNm + */"교차로_" + getCurrentDate().substring(4),
				NODE_TYPE: routMap.NODE_TYPE.CROSS,
				GPS_Y: getDispGps(lonlat.Ma,7),
				GPS_X: getDispGps(lonlat.La,7),
				draggable:routMap.mapInfo[mapId].draggable
				};
	
		com.getGridViewDataList(grid).setRowJSON(idx, data, true);
		
		//routeData = com.getGridDispJsonData(grid);
		//routMap.drawRoute(mapId, grid, idx);
	}
	return idx;
}


routMap.addSoundByClick = function(mapId,grid,routeId,e){
	var idx = -1;
	var routeData = com.getGridDispJsonData(grid);
	if(routeData.length >= routMap.MAX_NODE_CNT){
		//com.alert ("더이상 추가할 수 없습니다.");
		return idx;
	}
	
	var lonlat = e.latLng;
	var min = 10000000;
	var minIndex = null;

	for(var i = 0; i < routeData.length - 1; i++) {
		var result = getDistanceToLine(
			lonlat.Ma,
			lonlat.La,
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
		com.alert("선택할 수 없는 좌표입니다. 경로를 먼저 입력하세요");
	} else {
		idx = minIndex + 1;;
		
		idx = com.getGridViewDataList(grid).insertRow(idx);
	
		var today = new Date();
		var data = {
		
				ROUT_ID: routeId,
				NODE_SN: idx,
				NODE_NM: /*routNm + */"사운드_" + getCurrentDate().substring(4),
				NODE_TYPE: routMap.NODE_TYPE.SOUND,
				GPS_Y: getDispGps(lonlat.Ma,7),
				GPS_X: getDispGps(lonlat.La,7),
				draggable:routMap.mapInfo[mapId].draggable
				};
	
		com.getGridViewDataList(grid).setRowJSON(idx, data, true);
		
		//routeData = com.getGridDispJsonData(grid);
		//routMap.drawRoute(mapId, grid, idx);
	}
	return idx;
}

routMap.focusNode = function(mapId, grid,focusIdx){
	
	focusIdx = com.getGridDispIndex(grid,focusIdx);
	//routeData = com.getGridDispJsonData(grid);
	//if(routMap.mapInfo[mapId].selectedIndex!=focusIdx){
		routMap.mapInfo[mapId].oldSelectedIndex = routMap.mapInfo[mapId].selectedIndex;
		routMap.mapInfo[mapId].selectedIndex = focusIdx;
		
		// ?????
		if(mapId == "map_AL0101" || mapId == "map_FM0201"){
			routMap.showNode(mapId, com.getGridDispJsonData(grid), focusIdx);
		}
		else {
			if(routMap.mapInfo[mapId].isSound){
				routMap.drawSound(mapId, grid, focusIdx);
			}
			else {
				routMap.drawRoute(mapId, grid, focusIdx);
			}
		}
	//}
	routMap.mapInfo[mapId].isMove = true;
	//routMap.moveMap(mapId, routeData[focusIdx].GPS_Y, routeData[focusIdx].GPS_X);
}

routMap.insertNodeAll = function(mapId, grid,dataList,routeId,area){
	
	for (i = 0; i < dataList.length; i++) {
		routMap.addNode(mapId, grid,dataList[i],routeId,area);
	}
	routMap.drawRoute(mapId, grid, -1);
}

routMap.insertStdNodeAll = function(mapId, grid,dataList,routeId,area){
	
	for (i = 0; i < dataList.length; i++) {
		routMap.addStdNode(mapId, grid,dataList[i],routeId,area);
	}
	routMap.drawRoute(mapId, grid, -1);
}

//노드 추가
routMap.addNode = function(mapId, grid, data, routeId,area) {
	var routeData = com.getGridDispJsonData(grid);
	if(com.getGridViewDataList(grid).length >= routMap.MAX_NODE_CNT){
		//com.alert("더이상 추가할 수 없습니다.");
		return false;
	}
	
	var min = 10000000;
	var minIndex = null;
	if(routeData==null||routeData.length==0||routeData.length==1){
		var temp = {
				ROUT_ID: routeId,
				NODE_ID: data.NODE_ID,
				GPS_Y: data.GPS_Y,
				GPS_X: data.GPS_X,
				NODE_NM: data.NODE_NM,
				NODE_TYPE: data.NODE_TYPE,
				STTN_NO: data.STTN_NO,
				STTN_ID: data.STTN_ID,
				AREA: area
			};
		
		var idx = com.getGridViewDataList(grid).insertRow();
		com.getGridViewDataList(grid).setRowJSON(idx, temp, true);
		routeData = com.getGridDispJsonData(grid);
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
						ROUT_ID: routeId,
						NODE_ID: data.NODE_ID,
						GPS_Y: data.GPS_Y,
						GPS_X: data.GPS_X,
						NODE_NM: data.NODE_NM,
						NODE_TYPE: data.NODE_TYPE,
						STTN_NO: data.STTN_NO,
						STTN_ID: data.STTN_ID,
						AREA: area
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
					ROUT_ID: routeId,
					NODE_ID: data.NODE_ID,
					GPS_Y: data.GPS_Y,
					GPS_X: data.GPS_X,
					NODE_NM: data.NODE_NM,
					NODE_TYPE: data.NODE_TYPE,
					STTN_NO: data.STTN_NO,
					STTN_ID: data.STTN_ID,
					AREA: area
				};
			
			//routeData.splice(insertIndex, 0, temp);
			
			var idx = com.getGridViewDataList(grid).insertRow(insertIndex);
			com.getGridViewDataList(grid).setRowJSON(idx, temp, true);
			
		}
	}
	
}

//표준노드 추가
routMap.addStdNode = function(mapId, grid, data, routeId,area) {
	var routeData = com.getGridDispJsonData(grid);
	if(com.getGridViewDataList(grid).length >= routMap.MAX_NODE_CNT){
		//com.alert("더이상 추가할 수 없습니다.");
		return false;
	}
	
	var min = 10000000;
	var minIndex = null;
	if(routeData==null||routeData.length==0||routeData.length==1){
		var temp = {
				ROUT_ID: routeId,
				MOCK_NODE_ID: data.MOCK_NODE_ID,
				MOCK_LINK_ID: data.MOCK_LINK_ID,
				GPS_Y: data.GPS_Y,
				GPS_X: data.GPS_X,
				NODE_NM: data.NODE_NM,
				NODE_TYPE: data.NODE_TYPE,
				AREA: area
			};
		
		var idx = com.getGridViewDataList(grid).insertRow();
		com.getGridViewDataList(grid).setRowJSON(idx, temp, true);
		routeData = com.getGridDispJsonData(grid);
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
						ROUT_ID: routeId,
						MOCK_NODE_ID: data.MOCK_NODE_ID,
						MOCK_LINK_ID: data.MOCK_LINK_ID,
						GPS_Y: data.GPS_Y,
						GPS_X: data.GPS_X,
						NODE_NM: data.NODE_NM,
						NODE_TYPE: data.NODE_TYPE,
						AREA: area
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
					ROUT_ID: routeId,
					MOCK_NODE_ID: data.MOCK_NODE_ID,
					MOCK_LINK_ID: data.MOCK_LINK_ID,
					GPS_Y: data.GPS_Y,
					GPS_X: data.GPS_X,
					NODE_NM: data.NODE_NM,
					NODE_TYPE: data.NODE_TYPE,
					AREA: area
				};
			
			//routeData.splice(insertIndex, 0, temp);
			
			var idx = com.getGridViewDataList(grid).insertRow(insertIndex);
			com.getGridViewDataList(grid).setRowJSON(idx, temp, true);
			
		}
	}
	
}

routMap.moveRoute = function(mapId, grid, e){
	
	
	var routeData = com.getGridDispJsonData(grid);
	var point = e.marker.getPosition();
	var node = $.extend(true, {}, routeData[e.index]);
	//routeData.splice(e.index, 1);
	
	var val = false;
	
	if(e.index == 0 || e.index == routeData.length-1){
		val = true;
	}
	else {
		val = routMap.returnInsertRouteInfo(point.Ma, point.La,routeData,e.index);
	}
	
	if(val) {
		var temp = {
			NODE_ID: e.nodeId,
			GPS_Y: getDispGps(point.Ma,7),
			GPS_X: getDispGps(point.La,7),
		};
		
		temp = $.extend(true, node, temp);
		
		//routeData.splice(val.index, 0, temp);
		
		var data = com.getGridViewDataList(grid);
		
		data.removeRow(e.index);
		
		/*if(data.getRowStatus(e.index)=="C"){ //생성된 경우 데이터에서 삭제함
			data.removeRow(e.index);
		}
		else {
			grid.setRowVisible(e.index, false);
			data.deleteRow(e.index);
		}*/
		
		var idx = 0;
		if(val==true){
			idx = data.insertRow(e.index);
			data.setRowJSON(idx, temp, true);
		}
		else {
			idx = data.insertRow(val.index);
			data.setRowJSON(idx, temp, true);
		}
		
		if(routMap.mapInfo[mapId].isSound){
			routMap.drawSound(mapId, grid, idx);
		}
		else {
			routMap.drawRoute(mapId, grid, idx);
		}
		//routeData.sort(function (a,b){ return a.seq - b.seq });
		//fnObj.gridView1.setData(routeData);
		
		//ACTIONS.dispatch(ACTIONS.DRAW_ROUTE);
	} else {
		//com.alert("선택할 수 없는 좌표입니다.");
		
		routMap.drawRoute(mapId, grid, e.index);
	}
}

routMap.drawRoute = function(mapId, grid, focusIdx) {
	
	var list = com.getGridDispJsonData(grid);

	if(routMap.mapInfo[mapId].linkMode){
		if(list.length>0){
			var data = list[list.length-1];
			var temp = {
				NODE_ID: data.ED_NODE_ID,
				GPS_Y: data.ED_GPS_Y,
				GPS_X: data.ED_GPS_X,
				NODE_NM: data.ED_NODE_NM,
				NODE_TYPE: data.ED_NODE_TYPE
			};
			list.push(temp);
		}
	}
	routMap.initDisplay(mapId);
	
	if(list != null && list.length != 0) {
		for(var i = 0; i < list.length; i++) {
			
			/**드래그이벤트**/
			if(routMap.mapInfo[mapId].draggable){
				list[i].click = function(e) {
					routMap.mapInfo[mapId].dragging = false;
					routMap.moveRoute(mapId,grid,e);
				};
			}
			
			list[i].index = i;
			
			/**드래그이벤트**/
			list[i].draggable = routMap.mapInfo[mapId].draggable;
			
			if(list[i].NODE_TYPE != routMap.NODE_TYPE.VERTEX){
				routMap.mapInfo[mapId].nodes.push(routMap.getDrawingNode(mapId,list[i].GPS_Y, list[i].GPS_X));
			}
			
			// 노드 타입이 버스 정류장 마커 표시
			if(list[i].NODE_TYPE == routMap.NODE_TYPE.BUSSTOP && routMap.mapInfo[mapId].dispCheck.indexOf(routMap.NODE_TYPE.BUSSTOP)>=0) {
				routMap.addMarkerInter(mapId, list[i], grid, i, focusIdx);
			}
			else if(list[i].NODE_TYPE == routMap.NODE_TYPE.CROSS && routMap.mapInfo[mapId].dispCheck.indexOf(routMap.NODE_TYPE.CROSS)>=0) {
				routMap.addMarkerInter(mapId, list[i], grid, i, focusIdx);
			}
			else if(list[i].NODE_TYPE == routMap.NODE_TYPE.VERTEX && routMap.mapInfo[mapId].dispCheck.indexOf(routMap.NODE_TYPE.VERTEX)>=0) {
				routMap.addMarkerInter(mapId, list[i], grid, i, focusIdx);
			}
			// 아닐 경우(일반 노드) 네모 박스 표시
			else if(list[i].NODE_TYPE == routMap.NODE_TYPE.NORMAL && routMap.mapInfo[mapId].dispCheck.indexOf(routMap.NODE_TYPE.NORMAL)>=0) {
				routMap.addMarkerInter(mapId, list[i], grid, i, focusIdx);
			}
			
			
			
			if(i < list.length -1){
				var color = "#0000FF";
				if(list[i].MORN_STD=='MS002'){
					color = "#dd00dd";
				}
				else if(list[i].MORN_STD=='MS003'){
					color = "#FF005E";
				}

				routMap.drawLine(mapId, list[i], list[i+1], color);
			}
		}
		routMap.mapInfo[mapId].dragging = false;

		
		if(list.length>0){
			if(focusIdx!=-1){
				routMap.moveMap(mapId, list[focusIdx].GPS_Y, list[focusIdx].GPS_X);
			}
			else {
				routMap.moveMap(mapId, list[parseInt(list.length/2)].GPS_Y, list[parseInt(list.length/2)].GPS_X);
			}
		}
	}
}

routMap.drawRoute2 = function(mapId, list, focusIdx) {
	if(routMap.mapInfo[mapId].linkMode){
		if(list.length>0){
			var data = list[list.length-1];
			var temp = {
					NODE_ID: data.ED_NODE_ID,
					GPS_Y: data.ED_GPS_Y,
					GPS_X: data.ED_GPS_X,
					NODE_NM: data.ED_NODE_NM,
					NODE_TYPE: data.ED_NODE_TYPE
			};
			list.push(temp);	
		}
	}
	routMap.initDisplay(mapId);
	
	if(list != null && list.length != 0) {
		for(var i = 0; i < list.length; i++) {
			
			/**드래그이벤트**/
			if(routMap.mapInfo[mapId].draggable){
				list[i].click = function(e) {
					routMap.mapInfo[mapId].dragging = false;
					routMap.moveRoute(mapId,grid,e);
				};
			}
			
			list[i].index = i;

			/**드래그이벤트**/
			list[i].draggable = routMap.mapInfo[mapId].draggable;
			
			if(list[i].NODE_TYPE != routMap.NODE_TYPE.VERTEX){
				routMap.mapInfo[mapId].nodes.push(routMap.getDrawingNode(mapId,list[i].GPS_Y, list[i].GPS_X));
			}
			// 노드 타입이 버스 정류장 마커 표시
			if(list[i].NODE_TYPE == routMap.NODE_TYPE.BUSSTOP && routMap.mapInfo[mapId].dispCheck.indexOf(routMap.NODE_TYPE.BUSSTOP)>=0) {
				routMap.addMarkerInter(mapId, list[i], grid, i, focusIdx);
			}
			else if(list[i].NODE_TYPE == routMap.NODE_TYPE.CROSS && routMap.mapInfo[mapId].dispCheck.indexOf(routMap.NODE_TYPE.CROSS)>=0) {
				routMap.addMarkerInter(mapId, list[i], grid, i, focusIdx);
			}
			else if(list[i].NODE_TYPE == routMap.NODE_TYPE.VERTEX && routMap.mapInfo[mapId].dispCheck.indexOf(routMap.NODE_TYPE.VERTEX)>=0) {
				routMap.addMarkerInter(mapId, list[i], grid, i, focusIdx);
			}
			// 아닐 경우(일반 노드) 네모 박스 표시
			else if(list[i].NODE_TYPE == routMap.NODE_TYPE.NORMAL && routMap.mapInfo[mapId].dispCheck.indexOf(routMap.NODE_TYPE.NORMAL)>=0) {
				routMap.addMarkerInter(mapId, list[i], grid, i, focusIdx);
			}
			
			if(i < list.length -1){
				var color = "#0000FF";
				if(list[i].MORN_STD=='MS002'){
					color = "#dd00dd";
				}
				else if(list[i].MORN_STD=='MS003'){
					color = "#FF005E";
				}
				
				routMap.drawLine(mapId, list[i], list[i+1], color);
			}
		}
		routMap.mapInfo[mapId].dragging = false;
		
		if(list.length>0){
			if(focusIdx!=-1){
				routMap.moveMap(mapId, list[focusIdx].GPS_Y, list[focusIdx].GPS_X);
			}
			else {
				routMap.moveMap(mapId, list[parseInt(list.length/2)].GPS_Y, list[parseInt(list.length/2)].GPS_X);
			}
		}
	}
}

routMap.drawSound = function(mapId, grid, focusIdx) {
	
	var list = com.getGridDispJsonData(grid);

	if(routMap.mapInfo[mapId].linkMode){
		if(list.length>0){
			var data = list[list.length-1];
			var temp = {
				NODE_ID: data.ED_NODE_ID,
				GPS_Y: data.ED_GPS_Y,
				GPS_X: data.ED_GPS_X,
				NODE_NM: data.ED_NODE_NM,
				NODE_TYPE: data.ED_NODE_TYPE
			};
			list.push(temp);
		}
	}
	routMap.initDisplay(mapId);
	
	if(list != null && list.length != 0) {
		for(var i = 0; i < list.length; i++) {
			
			/**드래그이벤트**/
			if(routMap.mapInfo[mapId].draggable && list[i].NODE_TYPE == routMap.NODE_TYPE.SOUND){
				list[i].click = function(e) {
					routMap.mapInfo[mapId].dragging = false;
					routMap.moveRoute(mapId,grid,e);
				};
			}
			
			list[i].index = i;
			
			if(list[i].NODE_TYPE == routMap.NODE_TYPE.SOUND) {
				
				if( list[i].ALL_PLAY_TM != null && list[i].ALL_PLAY_TM != 0) {
						var radius = Math.round((routMap.LIMIT_SPEED / 3600 * 1000) * list[i].ALL_PLAY_TM);
						
						routMap.mapInfo[mapId].circles.push(routMap.getDrawingCircle(mapId, list[i].GPS_Y, list[i].GPS_X, radius));
				}
			}
			else if(list[i].NODE_TYPE != routMap.NODE_TYPE.VERTEX){
				routMap.mapInfo[mapId].nodes.push(routMap.getDrawingNode(mapId,list[i].GPS_Y, list[i].GPS_X));
			}
			
			// 노드 타입이 사운드, 버스, 정류장 마커 표시
			if(list[i].NODE_TYPE == routMap.NODE_TYPE.SOUND) {
				list[i].draggable = routMap.mapInfo[mapId].draggable;
				routMap.addSoundMarkerInter(mapId, list[i], grid, i, focusIdx);
			}
			else if(list[i].NODE_TYPE == routMap.NODE_TYPE.BUSSTOP) {
				list[i].draggable = false;
				routMap.addSoundMarkerInter(mapId, list[i], grid, i, focusIdx);
			}
			else if(list[i].NODE_TYPE == routMap.NODE_TYPE.CROSS) {
				list[i].draggable = false;
				routMap.addSoundMarkerInter(mapId, list[i], grid, i, focusIdx);
			}

			if(i < list.length -1){
				var color = "#0000FF";
				if(list[i].MORN_STD=='MS002'){
					color = "#dd00dd";
				}
				else if(list[i].MORN_STD=='MS003'){
					color = "#FF005E";
				}

				routMap.drawLine(mapId, list[i], list[i+1], color);
			}
		}
		routMap.mapInfo[mapId].dragging = false;

		
		if(list.length>0){
			if(focusIdx!=-1){
				routMap.moveMap(mapId, list[focusIdx].GPS_Y, list[focusIdx].GPS_X);
			}
			else {
				routMap.moveMap(mapId, list[parseInt(list.length/2)].GPS_Y, list[parseInt(list.length/2)].GPS_X);
			}
		}
	}
}

routMap.showRoute = function(mapId, list, id, type) {
	var focusIdx = -1;
	routMap.initDisplay(mapId);
	if(list != null && list.length != 0) {
		for(var i = 0; i < list.length; i++) {
			list[i].index = i;
			
			/**드래그이벤트**/
			list[i].draggable = routMap.mapInfo[mapId].draggable;
			
			if(list[i].NODE_TYPE != routMap.NODE_TYPE.VERTEX){
				routMap.mapInfo[mapId].nodes.push(routMap.getDrawingNode(mapId,list[i].GPS_Y, list[i].GPS_X));
			}
			
			if(type == "BUSSTOP" && list[i].STTN_ID == id){
				focusIdx = i;
				if((list[i].NODE_TYPE != routMap.NODE_TYPE.NORMAL) &&(list[i].NODE_TYPE != routMap.NODE_TYPE.VERTEX))
					routMap.showMarker(mapId, list[i], i, focusIdx);
			}
			else if(type == "CROSS" && list[i].CRS_ID == id){
				focusIdx = i;
				if((list[i].NODE_TYPE != routMap.NODE_TYPE.NORMAL) &&(list[i].NODE_TYPE != routMap.NODE_TYPE.VERTEX))
					routMap.showMarker(mapId, list[i], i, focusIdx);
			}
			else if(type != "ONLYLINE"){
				if((list[i].NODE_TYPE != routMap.NODE_TYPE.NORMAL) &&(list[i].NODE_TYPE != routMap.NODE_TYPE.VERTEX))
					routMap.showMarker(mapId, list[i], i, focusIdx);
			}
			
			if(i < list.length -1){
				var color = "#0000FF";
				if(list[i].MORN_STD=='MS002'){
					color = "#dd00dd";
				}
				else if(list[i].MORN_STD=='MS003'){
					color = "#FF005E";
				}
				if(list[i].ROUT_ID == list[i+1].ROUT_ID) //동일 노선끼리만 선 연결 되도록 함
					routMap.drawLine(mapId, list[i], list[i+1], color);
			}
		}

		if(list.length>0){
			if(focusIdx!=-1){
				routMap.moveMap(mapId, list[focusIdx].GPS_Y, list[focusIdx].GPS_X);
			}
			else {
				routMap.moveMap(mapId, list[parseInt(list.length/2)].GPS_Y, list[parseInt(list.length/2)].GPS_X);
			}
		}
	}
}

routMap.showRoute2 = function(mapId, list, focusIdx, grid) {
	routMap.initDisplay(mapId);
	if(list != null && list.length != 0) {
		for(var i = 0; i < list.length; i++) {
			list[i].index = i;
			
			/**드래그이벤트**/
			list[i].draggable = routMap.mapInfo[mapId].draggable;
			
			/*if(list[i].NODE_TYPE != routMap.NODE_TYPE.VERTEX){
				routMap.mapInfo[mapId].nodes.push(routMap.getDrawingNode(mapId,list[i].GPS_Y, list[i].GPS_X));
			}*/
			
			if((list[i].NODE_TYPE != routMap.NODE_TYPE.NORMAL) &&(list[i].NODE_TYPE != routMap.NODE_TYPE.VERTEX))
				routMap.showMarker(mapId, list[i], i, focusIdx, grid);
			
			if(i < list.length -1){
				var color = "#0000FF";
				if(list[i].MORN_STD=='MS002'){
					color = "#dd00dd";
				}
				else if(list[i].MORN_STD=='MS003'){
					color = "#FF005E";
				}
				if(list[i].ROUT_ID == list[i+1].ROUT_ID) //동일 노선끼리만 선 연결 되도록 함
					routMap.drawLine(mapId, list[i], list[i+1], color);
			}
		}

		if(list.length>0){
			if(focusIdx!=-1){
				routMap.moveMap(mapId, list[focusIdx].GPS_Y, list[focusIdx].GPS_X);
			}
			else {
				routMap.moveMap(mapId, list[parseInt(list.length/2)].GPS_Y, list[parseInt(list.length/2)].GPS_X);
			}
		}
	}
}

routMap.showNode = function(mapId, list, focusIdx, grid) {
	routMap.initNode(mapId);
	if(list != null && list.length != 0) {
		for(var i = 0; i < list.length; i++) {
			list[i].index = i;
			
			/**드래그이벤트**/
			list[i].draggable = routMap.mapInfo[mapId].draggable;
			
			if((list[i].NODE_TYPE != routMap.NODE_TYPE.NORMAL) &&(list[i].NODE_TYPE != routMap.NODE_TYPE.VERTEX))
				routMap.showMarker(mapId, list[i], i, focusIdx, grid);
		}

		if(list.length>0){
			if(focusIdx!=-1){
				routMap.moveMap(mapId, list[focusIdx].GPS_Y, list[focusIdx].GPS_X);
			}
			else {
				routMap.moveMap(mapId, list[parseInt(list.length/2)].GPS_Y, list[parseInt(list.length/2)].GPS_X);
			}
		}
	}
}

routMap.showVehicle = function(mapId, list, vhc_id, grid) {

	var focusIdx = -1;
	routMap.initBus(mapId);
	
	if(list != null && list.length != 0) {
		for(var i = 0; i < list.length; i++) {
			list[i].index = i;
			
			/**드래그이벤트**/
			list[i].draggable = routMap.mapInfo[mapId].draggable;
			
			
			if(list[i].VHC_ID == vhc_id){
				focusIdx = i;
				routMap.showBusMarker(mapId, list[i], i, focusIdx, grid);
			}
			else {
				routMap.showBusMarker(mapId, list[i], i, focusIdx, grid);
			}
			
		}

		if(list.length>0){
			if(focusIdx!=-1){
				routMap.moveMap(mapId, list[focusIdx].GPS_Y, list[focusIdx].GPS_X);
			}
			else {
				routMap.moveMap(mapId, list[parseInt(list.length/2)].GPS_Y, list[parseInt(list.length/2)].GPS_X);
			}
		}
	}
}


<<<<<<< HEAD
=======
/*웹소켓 차량 정보 지도에 표시*/
routMap.showVehicle2 = function(mapId, json, cur_vhc_id, grid, index) {

>>>>>>> f277a8ca01b8a3a53a2b60f3f31767c1146e61b2
	var focusIdx = -1;
	
	//주석 빼기
    routMap.initIndexBus(mapId,index);


	/**드래그이벤트**/
	json.draggable = routMap.mapInfo[mapId].draggable;
	
	
	if(json.VHC_ID == cur_vhc_id){
		focusIdx = index;
		routMap.showBusMarker(mapId, json, index, focusIdx, grid);
	}
	else if(json.GRP_VHC_ID == cur_vhc_id){
		focusIdx = index;
		routMap.showBusMarker(mapId, json, index, focusIdx, grid);
	}
	else {
		routMap.showBusMarker(mapId, json, index, focusIdx, grid);
	}
	if(json != null){
		if(focusIdx!=-1){
			routMap.moveMap(mapId, json.GPS_Y, json.GPS_X);
		}
	}
}


routMap.changeLocVehicleByClick = function(mapId, grid, curIndex, e){
	var data = com.getGridViewDataList(grid);
	var lonlat = e.latLng;

	data.setCellData(curIndex, "GPS_Y", getDispGps(lonlat.Ma,7));
	data.setCellData(curIndex, "GPS_X", getDispGps(lonlat.La,7));
	var jsonObj = data.getRowJSON(curIndex);
	
	routMap.showVehicle3(mapId, jsonObj, grid, 0);
}

routMap.showVehicle3 = function(mapId, json, grid) {
	
	//주석 빼기
    routMap.initIndexBus(mapId,0);


	/**드래그이벤트**/
	json.draggable = routMap.mapInfo[mapId].draggable;
	
	routMap.showBusMarker(mapId, json, 0, 0, grid);

	if(json != null){
		routMap.moveMap(mapId, json.GPS_Y, json.GPS_X);
	}
}

routMap.showFacility = function(mapId, list, fclt_id, grid) {
	var focusIdx = -1;
	routMap.initBus(mapId);
	
	if(list != null && list.length != 0) {
		for(var i = 0; i < list.length; i++) {
			list[i].index = i;
			
			/**드래그이벤트**/
			list[i].draggable = routMap.mapInfo[mapId].draggable;
			
			
			if(list[i].FCLT_ID == fclt_id){
				focusIdx = i;
				routMap.showFacilityMarker(mapId, list[i], i, focusIdx, grid);
			}
			else {
				routMap.showFacilityMarker(mapId, list[i], i, focusIdx, grid);
			}
			
		}
		
		if(list.length>0){
			if(focusIdx!=-1){
				routMap.moveMap(mapId, list[focusIdx].GPS_Y, list[focusIdx].GPS_X);
			}
			else {
				routMap.moveMap(mapId, list[parseInt(list.length/2)].GPS_Y, list[parseInt(list.length/2)].GPS_X);
			}
		}
	}
}

routMap.addPolygonByClick = function(mapId, data, grgId, grgNm, e){
	
	var lonlat = e.latLng;
	
	var idx = data.insertRow();

	var today = new Date();
	var tmp = {
			GRG_ID: grgId,
			SN: idx,
			GPS_Y: getDispGps(lonlat.Ma,7),
			GPS_X: getDispGps(lonlat.La,7)
			};

	data.setRowJSON(idx, tmp, true);
	routMap.drawPolygon(mapId, data);
}


//일반 지도 라인 그리기
routMap.drawPolygon = function(mapId, data, name) {
	

	routMap.initDisplay(mapId);
	
	var list = 	com.getGridDispJsonData2(data);
	var path = [];
	
	//routMap.removeMarkers();
	//routMap.deleteLine();
	//routMap.deleteCircle();
	//routMap.deleteNode();

	//첫번째 메뉴 세팅
	if(list != null && list.length != 0) {
		for(var i = 0; i < list.length; i++) {
			path.push(new kakao.maps.LatLng(list[i].GPS_Y, list[i].GPS_X));

			list[i].index = i;

			routMap.mapInfo[mapId].nodes.push(routMap.getDrawingNode(mapId, list[i].GPS_Y, list[i].GPS_X));
		}

		// 지도에 표시할 다각형을 생성합니다
		var polygon = new kakao.maps.Polygon({
			path:path, // 그려질 다각형의 좌표 배열입니다
			strokeWeight: 3, // 선의 두께입니다
			strokeColor: '#39DE2A', // 선의 색깔입니다
			strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
			strokeStyle: 'solid', // 선의 스타일입니다
			fillColor: '#A2FF99', // 채우기 색깔입니다
			fillOpacity: 0.7 // 채우기 불투명도 입니다
		});
		
		// 지도에 다각형을 표시합니다
		polygon.setMap(routMap.mapInfo[mapId].map);
		routMap.mapInfo[mapId].polygons.push(polygon);
		
		if(typeof name !== "undefined"){
			var center = routMap.mapInfo[mapId].map.getCenter(); 
			var content = '<div class="map_info">' + 
				'<div class="map_title">' + '차고지 : ' + name + '</div>' +
				'<div class="size">총 면적 : 약 ' + Math.floor(polygon.getArea()) + ' m<sup>2</sup></area>' +
				'</div>';
			routMap.mapInfo[mapId].infoWindow = new kakao.maps.InfoWindow({removable: true});
			routMap.mapInfo[mapId].infoWindow.setContent(content); 
			routMap.mapInfo[mapId].infoWindow.setPosition(center); 
			routMap.mapInfo[mapId].infoWindow.setMap(routMap.mapInfo[mapId].map);
			routMap.mapInfo[mapId].infoArr.push(routMap.mapInfo[mapId].infoWindow);
		}
		
		// 다각형에 마우스오버 이벤트가 발생했을 때 변경할 채우기 옵션입니다
		var mouseoverOption = { 
			fillColor: '#EFFFED', // 채우기 색깔입니다
			fillOpacity: 0.8 // 채우기 불투명도 입니다        
		};

		// 다각형에 마우스아웃 이벤트가 발생했을 때 변경할 채우기 옵션입니다
		var mouseoutOption = {
			fillColor: '#A2FF99', // 채우기 색깔입니다 
			fillOpacity: 0.7 // 채우기 불투명도 입니다        
		};

		// 다각형에 마우스오버 이벤트를 등록합니다
		kakao.maps.event.addListener(polygon, 'mouseover', function() { 

		// 다각형의 채우기 옵션을 변경합니다
			polygon.setOptions(mouseoverOption);

		}); 

		kakao.maps.event.addListener(polygon, 'mouseout', function() { 

			// 다각형의 채우기 옵션을 변경합니다
			polygon.setOptions(mouseoutOption);
		
		}); 

		// 다각형에 마우스다운 이벤트를 등록합니다
		var downCount = 0;
		kakao.maps.event.addListener(polygon, 'mousedown', function() { 
		}); 
		
		// 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시합니다
		kakao.maps.event.addListener(polygon, 'click', function(mouseEvent) {
			routMap.mapInfo[mapId].infowindow.open(routMap.mapInfo[mapId].map);
			
		});
	}
}

routMap.initDisplay = function(mapId){
	
	routMap.removeAllInfoWindow(mapId);
	routMap.removeAllOverlay(mapId);
	routMap.removeMarkers(mapId);
	routMap.deleteLine(mapId);
	routMap.deleteCircle(mapId);
	routMap.deleteNode(mapId);
	routMap.deletePolygon(mapId);
}


routMap.initNode = function(mapId){
	routMap.removeAllOverlay(mapId);
	routMap.removeMarkers(mapId);
}

routMap.initBus = function(mapId){
	routMap.removeAllBusOverlay(mapId);
	routMap.removeBusMarkers(mapId);
}

routMap.initIndexBus = function(mapId,index){
	routMap.removeIndexBusOverlay(mapId,index);
	routMap.removeIndexBusMarker(mapId,index);
}

routMap.initMapInfo = function(mapId){
	routMap.initDisplay(mapId);
	routMap.initBus(mapId);
	routMap.mapInfo[mapId].polylines = [];
	routMap.mapInfo[mapId].markers = [];
	routMap.mapInfo[mapId].markers_user = [];
	routMap.mapInfo[mapId].infoWindow = null;
	routMap.mapInfo[mapId].infoArr = [];
	routMap.mapInfo[mapId].overlay = null;
	routMap.mapInfo[mapId].overArr = [];
	// 반경 표시용 원 배열
	routMap.mapInfo[mapId].circles = []; 
	// 노드 드로잉 배열
	routMap.mapInfo[mapId].nodes = [];
	routMap.mapInfo[mapId].dragging = false; //드래그 중인지
	routMap.mapInfo[mapId].selectedMarker = null;
	routMap.mapInfo[mapId].selectedIndex = -1;
}

routMap.setDispCheck = function(mapId, dispCheck) {
	routMap.mapInfo[mapId].dispCheck = dispCheck;
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

//function onClickCategory {
//    var id = this.id;
//    var className = this.className;
//    var category = document.getElementById('category'),
//    var children = category.children
//	//placeOverlay.setMap(null);
//	
//	if (className === 'on') {
//	    currCategory = '';
//	    for (var i=0; i<children.length; i++) {
//	    	children[i].className = '';
//	    }
//	    //removeMarker();
//	} else {
//	    currCategory = id;
//	    this.className = 'on';
//	}	
//}
