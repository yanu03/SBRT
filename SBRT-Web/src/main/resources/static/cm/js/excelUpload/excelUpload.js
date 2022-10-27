var excelUpload = {
		
	};
var map = null;
var markers= [];
var EXCEL_JSON = null;

excelUpload.init = function() {
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = { 
		center: new kakao.maps.LatLng(36.502212, 127.256300), // 지도의 중심좌표
		level: 5 // 지도의 확대 레벨
	};
	
	// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
	map = new kakao.maps.Map(mapContainer, mapOption); 
	
};

$( function() {
    $('#start, #end').datetimepicker();
    $("#file1").on("change", handleExcelFileSelect);
    
    $("#file1").on("change", function (e) {
        let files = e.target.files;
        let i, f;
        for (i = 0; i != files.length; ++i) {
            f = files[i];

            let reader = new FileReader();
            
          //성공적으로 읽기 동작이 완료된 경우 실행되는 이벤트 핸들러를 설정한다.
            reader.onload = function (e) {
            	
            	//FileReader 결과 데이터(컨텐츠)를 가져온다.
                let data = e.target.result;
                
                let binary = "";
                let bytes = new Uint8Array(data);
                let length = bytes.byteLength;
                for (let i = 0; i < length; i++) {
                    binary += String.fromCharCode(bytes[i]);
                }

                //바이너리 형태로 엑셀파일을 읽는다.
                let workbook = XLSX.read(binary, {type: 'binary'});

                //엑셀파일의 시트 정보를 읽어서 JSON 형태로 변환한다.
                workbook.SheetNames.forEach(function (item, index, array) {
	                EXCEL_JSON = XLSX.utils.sheet_to_json(workbook.Sheets[item]);
	                //drawMarker(EXCEL_JSON);	
            	});
                
            };

            reader.readAsArrayBuffer(f);  //파일객체를 읽는다. 완료되면 원시 이진 데이터가 문자열로 포함됨.

        }
    });
    
    $(".btn-search").on("click", function(e) {
    	removeAllMarker();
    	drawMarker(EXCEL_JSON);
    })
	
	/*$(".btn-excelUpload").click(function() {
		var form = new FormData();
		form.append( "file1", $("#file1")[0].files[0] );
		
		 $.ajax({
		     type : 'post',
		     url : '/excelUpload/uploadFile',
		     data : form,
		     processData : false,
		     dataType : "json",
		     contentType : false,
		     async    : false,
		     success : function(data) {
		         alert("파일 업로드 성공.");
		     },
		     error : function(error) {
		         alert("파일 업로드에 실패하였습니다.");
		        
		     }
		 });  		
		
	});*/
	
	
} );

function handleExcelFileSelect(e) {
    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);

    //var reg = /(.*?)\/(xlsx|xlsm|csv)$/;
    var reg = 'xlsx';

    filesArr.forEach(function(f) {
        if (!f.name.match(reg)) {
            alert("확장자는 엑셀 확장자만 가능합니다.");
            return;
        }

        sel_file = f;

        var reader = new FileReader();
        reader.readAsDataURL(f);
    });
}
/*
function fn_submit(){
    var form = new FormData();
    form.append( "file1", $("#file1")[0].files[0] );
    
     jQuery.ajax({
         url : "/excelUpload/uploadFile"
       , type : "POST"
       , processData : false
       , contentType : false
       , data : form
       , success:function(response) {
           alert("성공하였습니다.");
           console.log(response);
       }
       ,error: function (jqXHR) 
       { 
           alert(jqXHR.responseText); 
       }
   });
}*/

//Json key 값 변경
function renameKey(obj, oldKey, newKey) {
    obj[newKey] = obj[oldKey];
    delete obj[oldKey]
}

//마커 그리기
function drawMarker(allJsonData) {
	
	if(allJsonData.length <= 0) {
		return;
	}
	
	for(var i=0; i<allJsonData.length; i++) {
		//날짜 조건 체크
		if (!jsonCheckDateTime(allJsonData[i])) {
			continue;
		}
		
		// 마커 이미지의 이미지 크기 입니다
		var imageSize = new kakao.maps.Size(35, 35); 
		var markerImage = null;
		var markerOverImage = null;
		var markerSelImage = null;
		var zIndex= 6;
		
		var imgUrl = null;
		
		/*
		if(allJsonData[i].VHC_NO.includes("우진")){
			markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/bus_blue.png", imageSize);
			//markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/bus_blue_select.png", imageSize);
		}
		
		else {
			//markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/bus_red.png", imageSize);
			markerImage = new kakao.maps.MarkerImage("/cm/images/tmap/bus_red_back.png", imageSize);
			//markerSelImage = new kakao.maps.MarkerImage("/cm/images/tmap/bus_red_select.png", imageSize);
		}	
		*/
		switch(allJsonData[i].EVT_TYPE){
			case 'ET001':
				imgUrl = "/cm/images/tmap/ET001.png";
				break;	
			case 'ET002':
				imgUrl = "/cm/images/tmap/ET002.png";
				break;	
			case 'ET003':
				imgUrl = "/cm/images/tmap/ET003.png";
				break;	
			case 'ET004':
				imgUrl = "/cm/images/tmap/ET004.png";
				break;	
			case 'ET005':
				imgUrl = "/cm/images/tmap/ET005.png";
				break;	
			case 'ET006':
				imgUrl = "/cm/images/tmap/ET006.png";
				break;	
			case 'ET007':
				imgUrl = "/cm/images/tmap/ET007.png";
				break;	
			case 'ET008':
				imgUrl = "/cm/images/tmap/ET008.png";
				break;	
			case 'ET009':
				imgUrl = "/cm/images/tmap/ET009.png";
				break;	
			case 'ET010':
				imgUrl = "/cm/images/tmap/ET010.png";
				break;	
			case 'ET011':
				imgUrl = "/cm/images/tmap/ET012.png";
				break;	
			case 'ET013':
				imgUrl = "/cm/images/tmap/ET013.png";
				break;	
			case 'ET014':
				imgUrl = "/cm/images/tmap/ET014.png";
				break;	
			case 'ET015':
				imgUrl = "/cm/images/tmap/ET015.png";
				break;	
			case 'ET016':
				imgUrl = "/cm/images/tmap/ET016.png";
				break;	
			case 'ET017':
				imgUrl = "/cm/images/tmap/ET017.png";
				break;	
			case 'ET018':
				imgUrl = "/cm/images/tmap/ET018.png";
				break;	
			case 'ET019':
				imgUrl = "/cm/images/tmap/ET019.png";
				break;	
			case 'ET020':
				imgUrl = "/cm/images/tmap/ET020.png";
				break;	
			case 'ET021':
				imgUrl = "/cm/images/tmap/ET021.png";
				break;	
			case 'ET022':
				imgUrl = "/cm/images/tmap/ET022.png";
				break;	
		}
		
		markerImage = new kakao.maps.MarkerImage(imgUrl, imageSize);
		
		
		
		// 마커 이미지를 생성합니다    
		marker = new kakao.maps.Marker({
			position : new kakao.maps.LatLng(allJsonData[i].GPS_Y_RAW, allJsonData[i].GPS_X_RAW), // Marker의 중심좌표 // 설정.
			//title : data.label, // Marker의 라벨.
			image : markerImage,
			zIndex: zIndex
		});		
		
		
		marker.ocr_dtm = allJsonData[i].OCR_DTM;
		marker.rout_nm = allJsonData[i].ROUT_NM;
		marker.evt_type_nm = allJsonData[i].EVT_TYPE_NM;
		marker.node_nm = allJsonData[i].NODE_NM;
		marker.node_sn = allJsonData[i].NODE_SN;
		marker.vhc_no = allJsonData[i].VHC_NO;
		
		
		// 마커에 click 이벤트를 등록합니다
		kakao.maps.event.addListener(marker, 'click', function() {
			alert("날짜 : " + this.ocr_dtm  + "\n노선명 : " + this.rout_nm + "\n이벤트 : " + this.evt_type_nm
					+ "\n노드명 : " + this.node_nm + "\n노드순번 : " + this.node_sn + "\n버스번호 : " + this.vhc_no);
			
		});		
		
		if(markers.length >= 10000) {
			return;
		}
		marker.setMap(map);
		markers.push(marker);
		
		
	} //end for
}; //end drawMarker


//시작, 종료날짜가 있을 경우 json 처리
function jsonCheckDateTime(jsonData) {
	
	//날짜 비교용
	var fromDateLength = $("#start").val().length;
	var toDateLength = $("#end").val().length;
	
	var fromDateTime = new Date($("#start").val());
	var toDateTime = new Date($("#end").val());
	
	if(jsonData.OCR_DTM.includes("오전") || jsonData.OCR_DTM.includes("오후")) {
		if(jsonData.OCR_DTM.includes("오전")) {
			if(jsonData.OCR_DTM.match(/오전 12/) != null) { //오전 12시
				jsonData.OCR_DTM = jsonData.OCR_DTM.replace(/오전 12/, "00");
			}
			
			else { //오전 1시 ~ 11시
				jsonData.OCR_DTM = jsonData.OCR_DTM.replace(" ", "");
				jsonData.OCR_DTM = jsonData.OCR_DTM.replace("오전","");
			}
		}
		
		else if(jsonData.OCR_DTM.includes("오후")) {
			//오후 12시
			if(jsonData.OCR_DTM.match(/오후 12/) != null) {
				jsonData.OCR_DTM = jsonData.OCR_DTM.replace(/오후 12/, "12");
			}
			else {
				//오후 1시 ~ 11시
				var matchedText = jsonData.OCR_DTM.match(/오후 \d+/);
				matchedText[0] = matchedText[0].replace("오후", "");
				matchedText[0] = matchedText[0].replace(" ", "");
				matchedText[0] = parseInt(matchedText[0]) + 12;
				matchedText[0] = String(matchedText[0]);
				jsonData.OCR_DTM = jsonData.OCR_DTM.replace(/오후 \d+/, matchedText[0]);
			}
		}
	}
		
	var jsonDateTime = new Date(jsonData.OCR_DTM);
	
	
	if(fromDateLength == 0 && toDateLength == 0){
		return true;
	}
	
	else if(fromDateLength != 0 && toDateLength == 0 ) {
		if(jsonDateTime.getTime() >= fromDateTime.getTime()){
			return true;
		}
		
	}
	
	else if(fromDateLength == 0 && toDateLength != 0 ) {
		if(jsonDateTime.getTime() <= toDateTime.getTime()){
			return true;
		}
	}
	
	else if(fromDateLength != 0 && toDateTime.length != 0 ) {
		if(jsonDateTime.getTime() >= fromDateTime.getTime() && jsonDateTime.getTime() <= toDateTime.getTime()){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
			return true;
		}
	}
	
	return false;
};

//마커 없애기
function removeAllMarker() {
	for (var i=0; i<markers.length; i++) {
		markers[i].setMap(null);
	}
	markers= [];
}
