<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>탑승버스현재상황</title>
<link rel="stylesheet" type="text/css" href="/cm/css/dashboard/design.css">
<link rel="stylesheet" type="text/css" href="/cm/css/dashboard/reset.css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script type='text/javascript' src='./ClipReport/js/jquery-1.11.1.js'></script>
<script src="./webjars/sockjs-client/sockjs.min.js"></script>
<script src="./webjars/stomp-websocket/stomp.min.js"></script>
<script src="./cm/js/util.js"></script>
<script src="./cm/js/dashboard/dashboard2.js"></script>
<script type='text/javascript'>

	
	$(document).ready(function()
	{	
		dashboard.init();
		/* for(var i = 0; i < dashboard.ROUT_CROSS_INFO['WD001'].length; i++) {
			var corssInfo = dashboard.ROUT_CROSS_INFO['WD001'][i];
			var crossDisp = "<i class='cross red ir_pm' style='left:"+(i*25+12)+"%'></i>";

			$( "#dashboard_cross" ).append(crossDisp)
		} */

		//var offset = $("#dashboard_bus").offset();
		//$('html, body').animate({scrollTop : offset.top}, 400)		    
	});
	

</script>
</head>
 
    <body>
        <article class="display" style="grid-template-rows: 205px 385px 148px 280px;">
            <!--상황에 따라 class명에 blue, yellow, orange, red 삽입-->
            <section id="priority_situation" class="situation orange">
                <h2 class="blind">현재상황</h2>
                <!-- 디스패치 메시지 적용부분 
                <span class="text" id="priority_signal"></span> -->
                <span class="text" id="titleVhcNo" style="font-size: 90px;">통신 대기중</span>
                <div style="width:384px; position: absolute;margin-right: 100px; right: 10px;">
	                <div class=""  style="display: inline-block; float:right; text-align:center;">
		                <a href="javascript:void(null);" onclick="dashboard.selectVhcNo()"
		                	style="background: #a13e3e;
						    border-color: #a13e3e;
						    margin-left: 4px;
						    height: 70px;
						    line-height: 70px;
						    padding: 0 12px 0 2px;
						    text-indent: 10px;
						    color: #ffffff;
						    font-weight: bold;
						    display: inline-block;
						    background: #5b5b5b;
						    border: 1px solid #3d3d3d;
						    border-radius: 3px;
						    box-sizing: border-box;
						    width:100px;
						    font-size: 30px;
						    text-decoration: none;
						    ">조회</a>
	                </div>
                	<div style="float: left;">
						<input style="width:280px;height:70px;font-size:40px;" id="vhcNoInput" class="w2input search_input" type="text" placeholder="차번" onkeypress="enterKeyEvent(event)">                
                	</div>
                </div>
            </section>
            <section class="sttnETA">
                <h2 class="blind">다음정류장이름</h2>
                <dl>
                    <dt>다음 정류장 :</dt>
                    <dd id="next_node">-</dd>
                </dl>            
                <dl>
                    <dt>예정 시간 :</dt>
                    <dd id="eta">-</dd>
                </dl>
                <!-- <h2 class="blind">신호등</h2>
                <span class="text" id="crs_nm"></span> -->
                <!-- 불켜지면 클래스명 on 삽입 -->
                
                <!-- 	신호구간, 현재 사용하지 않아 주석처리
                <div class="light-wrap">
                    <span class="ir_pm red-light on">빨간불</span>
                    <span class="ir_pm green-light">초록불</span>
                </div> -->
            </section>
            <section class="line-name">
                <h2>운행노선</h2>
                <span class="text" id="rout">-</span>
            </section>
            <section class="next-position">
                <h2 class="blind">다음정류장이름</h2>
                <dl>
                    <dt>앞차  :</dt>
                    <dd id="nextBus">-</dd>
                </dl>
                <dl>
                    <dt>뒷차 :</dt>
                    <dd id="prevBus">-</dd>
                </dl>
            </section>
            <section class="line-info" style="padding:82px;">
                <h2 class="blind">노선정보</h2>
				<ol class="line" id="dashboard_list" style="">
					<li>
						<i class="station ir_pm">정거장아이콘</i>
						<span id="sttn1"></span>
					</li>
					<li>
						<i class="station ir_pm">정거장아이콘</i>
						<span id="sttn2"></span>
					</li>
					<li>
						<i class="station ir_pm">정거장아이콘</i>
						<span id="sttn3"></span>
					</li>
				</ol>                
				<!-- <ol class="line2" id="dashboard_list2"  style="display: none;"></ol> -->
                <!-- 버스와 교차로의 위치는 style="left:"값으로 조정 -->
                <div class="icon-wrap" id="dashboard_bus">
                    <i class="bus ir_pm" style="left:45%">버스</i>
                </div>
                <div class="icon-wrap" id="dashboard_cross">
                </div>
            </section>
        </article>
    </body> 
</html>
