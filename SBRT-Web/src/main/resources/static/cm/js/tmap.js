//티맵용 전역변수
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
var busstopNodeType;

// 음성 편성 노드 타입
var orgaNodeType;
var orgaIcon = "/assets/images/tmap/voice_node.png";

var busstopIcon = "/assets/images/tmap/busstop.png"

/**티맵 시작**/
function initTmap(options) {
	map = new Tmapv2.Map("mapView0",  
	{
		center: new Tmapv2.LatLng(36.502212, 127.256300), // 지도 초기 좌표
		width: options.width, 
		height: options.height,
		zoom: 15
	});
	if(options.onClick) {
		map.addListener("click", options.onClick);
	} else {
		//map.addListener("click", onClick);
	}
	
	
	return map;
} 

/**맵 이동**/
function moveMap(lat, lon) {
	map.setCenter(new Tmapv2.LatLng(lat, lon));
}

/**마커삭제**/
function removeMarkers() {
	if(markers != null && markers.length != 0) {
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(null);
		}
	}
	
	markers = [];
}

/**유저마커삭제**/
function removeMarkers_user() {
	for (var i = 0; i < markers_user.length; i++) {
		markers_user[i].setMap(null);
	}
	markers_user = [];
}


/**마커그리기**/
function mapMarker(lat, lng){
	removeMarkers();
	
	marker = new Tmapv2.Marker({
		position: new Tmapv2.LatLng(lat, lng), //Marker의 중심좌표 설정.
		map: map //Marker가 표시될 Map 설정..
	});
	map.setCenter(new Tmapv2.LatLng(lat,lng));
	markers.push(marker);
}

/**선그리기**/
function drawLine(lat_arr, lng_arr){
	var path = [];
	for(var i=0; i < lat_arr.length; i++){
		path.push(new Tmapv2.LatLng(lat_arr[i], lng_arr[i]));
	}
	
	polyline = new Tmapv2.Polyline({
		path: path,
		strokeColor: "#FF005E", // 라인 색상
		strokeWeight: 3, // 라인 두게
		map: map // 지도 객체
	});
}

/**선그리기**/
function drawLine2(lat_arr, lng_arr, seq_arr){
	var path = [];
	for(var i=0; i < lat_arr.length; i++){
		if(seq_arr[i] != 0){			
			path.push(new Tmapv2.LatLng(lat_arr[i], lng_arr[i]));
		}
	}
	
	polyline = new Tmapv2.Polyline({
		path: path,
		strokeColor: "#dd00dd", // 라인 색상
		strokeWeight: 3, // 라인 두게
		map: map // 지도 객체
	});
}

/**선삭제**/
function deleteLine(){
	if(polyline != null) {
		polyline.setMap(null);
		polyline = null;
	}
}

/**마커여러개추가**/
function addMarkers(lat_arr, lng_arr, id_arr) {
	for(var i=0; i < lat_arr.length; i++){
        marker = new Tmapv2.Marker({
            position: new Tmapv2.LatLng(lat_arr[i], lng_arr[i]), //Marker의 중심좌표 설정.
            //label: id_arr[i]//, //Marker의 라벨.
        	label: "<span style='background-color: #46414E; color:white; padding: 3px;'>" + id_arr[i] + "</span>"
        	//icon:"http://tmapapi.sktelecom.com//resources/images/common/pin_car.png"
        });
        marker.setMap(map); //Marker가 표시될 Map 설정.
        markers.push(marker);
	}
}

/**노드마커 추가**/
function addMarkerInter(data, grid, idx) {
	var marker = new Tmapv2.Marker({
        position: new Tmapv2.LatLng(data.lati, data.longi), //Marker의 중심좌표 설정.
        label: data.label, //Marker의 라벨.
        map: map,
    	icon: data.icon,
    	draggable: data.draggable,
    });
		
		marker.addListener("click", function(e) {
			grid.selectRow(idx);
			ax5.util.search(grid.list, function(){
				return this["seq"] == data.seq;
			});
			data.click({
				marker: marker,
				nodeId: data.nodeId,
				index: data.index
			});
		});
    markers.push(marker);
}
/**통통튀는 마커 생성**/
function addMarkerAni(lat, lng, id) {
	var aniType = Tmapv2.MarkerOptions.ANIMATE_BOUNCE;
	var coordIdx = 0;
      
	//removeMarkers(); // 지도에 새로 등록하기 위해 모든 마커를 지우는 함수입니다.
      
	
	var func = function() {
		//Marker 객체 생성.
			var marker = new Tmapv2.Marker({
				position: new Tmapv2.LatLng(lat, lng), //Marker의 중심좌표 설정.
				draggable: true, //Marker의 드래그 가능 여부.
				animation: aniType, //Marker 애니메이션.
				animationLength: 500, //애니메이션 길이.
				label: "정류장", //Marker의 라벨.
				title: id, //Marker 타이틀.
				map: map //Marker가 표시될 Map 설정.
			});
			markers.push(marker);

		
	}
	// 일정 시간 간격으로 마커를 생성하는 함수를 실행합니다
	setTimeout(func, 300);
}

/**지도위 팝업 생성**/
function popUp(lat, lng, msg){
	var content =
		"<div class = 'popUp'>" +
		"<span class = 'popUp' style='font-weight:bold;'>" + msg + "</span>" + 
		"<span class = 'popUp' style='font-size: 12px; margin-left:2px; margin-bottom:2px; display:block;'>"+ lat + "," + lng +"</span>"
		+ "</div>";
				

	infoWindow = new Tmapv2.InfoWindow({
		position: new Tmapv2.LatLng(lat, lng), //Popup 이 표출될 맵 좌표
		content: content, //Popup 표시될 text
		type: 2, //Popup의 type 설정.
		map: map //Popup이 표시될 맵 객체
	});
	
	infoArr.push(infoWindow);
}

/**팝업 전체 삭제**/
function removeAllPopUp(){
	if(infoArr != null){
		for(var i=0; i<infoArr.length; i++){
			infoArr[i].setMap(null);
		}
		infoArr = [];
	}
}

/**좌표받아 인서트(임시)**/
function insertGeo(lat, lng){
        var formData = {y : lat, x : lng};
        
        axboot.promise()
            .then(function (ok, fail, data) {
            	axboot.ajax({
                	type: "POST",
                    url: "/api/v1/BM0999M0I0",
                    data: JSON.stringify(formData),
                    callback: function (res) {
                        ok(res);
                    }
                });
            })
            .then(function (ok, fail, data) {
        		axToast.push(LANG("onupdate"));
        		ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            })
            .catch(function () {
            });
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

/***************************** BM0405 *************************************/
function addMarker(data) {
	var marker = new Tmapv2.Marker({
        position: new Tmapv2.LatLng(data.lati, data.longi), //Marker의 중심좌표 설정.
        label: data.label, //Marker의 라벨.
        map: map,
    	icon: data.icon,
    	draggable: data.draggable,
    });
	
	if(data.click) {
		marker.addListener("click", function(e) {
			data.click({
				marker: marker,
				nodeId: data.nodeId,
				index: data.index
			});
		});
	}
	
    markers.push(marker);
}

function deleteCircle() {
	if(circles != null && circles.length != 0) {
		for(var i = 0; i < circles.length; i++) {
			circles[i].setMap(null);
		}
		circles = [];
	}
}

function deleteNode() {
	if(nodes != null && nodes.length != 0) {
		for(var i = 0; i < nodes.length; i++) {
			nodes[i].setMap(null);
		}
		nodes = [];
	}
}

function getDrawingCircle(lat, lon, radius) {
	var circle = new Tmapv2.Circle({
		center: new Tmapv2.LatLng(lat, lon),
		radius: radius,
		strokeColor: "#A872EE",
		strokeWeight: 2,
		fillColor: "#A872EE",
		fillOpacity: 0.2,
		map: map
	});
	
	return circle;
}

function getDrawingNode(lat, lon) {
	var node = new Tmapv2.Circle({
		center: new Tmapv2.LatLng(lat, lon),
		radius: 4,
		strokeColor: "#FF005E",
		strokeWeight: 3,
		fillColor: "#FFFFFF",
		fillOpacity: 1,
		map: map
	});
	
	return node;
}
/**************************************************************************/