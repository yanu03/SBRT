<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" images />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" images />
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1" images>
    <meta name="robots" content="index, follow">
<title>엑셀데이터검증</title>
    <link rel="shortcut icon" href="images/icon.ico">
 <link rel="stylesheet" type="text/css" href="/cm/css/excelUpload/reset.css">
 <link rel="stylesheet" type="text/css" href="/cm/css/excelUpload/main.css">
 <link rel="stylesheet" type="text/css" href="/cm/css/excelUpload/jquery.datetimepicker.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script src="/websquare/externalJS/jquery/jquery-1.10.2.min.js"></script>
<script src="./cm/js/util.js"></script>
<script src="./cm/js/excelUpload/excelUpload.js"></script>
<script src="./cm/js/excelUpload/jquery.datetimepicker.full.min.js"></script>
<script src="./cm/js/excelUpload/xlsx.full.min.js"></script>
<script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/babel-standalone/6.26.0/babel.min.js"></script>
<script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/babel-polyfill/7.10.4/polyfill.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=ce20435f6434f0fa427b988545325b2c&amp;libraries=services&amp;autoload=false"></script>
<script type='text/javascript'>
	

	
	$(document).ready(function()
	{
		kakao.maps.load(function() {
			excelUpload.init();
		});	
		
	});
	

</script>
</head>
 
    <body class="admin-wrap">
    <header>
        <div class="title-wrap">
            <h1>엑셀데이터검증</h1>
            <!-- <p>ILSD v1.1</p>
            <p>영업소</p> -->
        </div>
    </header>
    <div class="search-wrap">
        <div>
            <span>일시 :&nbsp;</span>
            <input type="text" id="start" />&nbsp;~&nbsp;<input type="text" id="end" />
        </div>
        <div>
           <!--  <span>차량번호 :&nbsp;</span>
            <input type="text" /> -->
            <button type="button" class="btn-search ir_pm">찾기</button>
            <!-- <button type="button" class="btn-refrash ir_pm">갱신</button> -->
            <input type="file" id="file1" name="file1" style="width:auto; background:#919cab; margin-left:10px;" >
		    <!-- <button id="btn_submit" onclick="javascript:fn_submit()">전송</button>  -->   
           <!--  <input type="file" id="file1" class="btn-excelUpload"></input>
            <button type="button" class="btn-excelUpload">엑셀업로드</button> -->
        </div>
    </div>
    
    
    
 	   <div id="map" style="width:100%;height:820px;"></div>
    </body> 
</html>
