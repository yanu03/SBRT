<%@page import="com.clipsoft.clipreport.oof.OOFFile"%>
<%@page import="com.clipsoft.clipreport.oof.OOFDocument"%>
<%@page import="java.io.File"%>
<%@page import="com.clipsoft.clipreport.server.service.ReportUtil"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
OOFDocument oof = OOFDocument.newOOF();
OOFFile file = oof.addFile("crf.root", "%root%/crf/CLIP.crf");
oof.addConnectionData("*", "oracle1");

oof.addField("PARAM1", "param&&&#---1value");
oof.addField("한 글 123 abc", " param&&&#---1value");


String propertyPath  = request.getSession().getServletContext().getRealPath("/") + File.separator +  "WEB-INF" + File.separator + "clipreport5" + File.separator + "clipreport5.properties";
//String propertyPath  = "C:\\sbrt-web\\workspace\\SBRT-Web\\src\\main\\webapp\\WEB-INF\\clipreport5\\clipreport5.properties";

%><%
//세션을 활용하여 리포트키들을 관리하지 않는 옵션
//request.getSession().setAttribute("ClipReport-SessionList-Allow", false);
String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath);
//리포트의 특정 사용자 ID를 부여합니다.
//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
//clipreport5.properties 의 useuserid 옵션이 true 이고 기본 예제[String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath);] 사용 했을 때 세션ID가 userID로 사용 됩니다.
//String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath, "userID");

//리포트key의 사용자문자열을 추가합니다.(문자숫자만 가능합니다.)
//String resultKey =  ReportUtil.createReport(request, oof, "false", "false", request.getRemoteAddr(), propertyPath, "", "usetKey");

//리포트를 저장 스토리지를 지정하여 생성합니다.
//String resultKey =  ReportUtil.createReportByStorage(request, oof, "false", "false", request.getRemoteAddr(), propertyPath, "rpt1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="./ClipReport/css/clipreport5.css">
<link rel="stylesheet" type="text/css" href="./ClipReport/css/UserConfig5.css">
<link rel="stylesheet" type="text/css" href="./ClipReport/css/font.css">

<script type='text/javascript' src='./ClipReport/js/jquery-1.11.1.js'></script>
<script type='text/javascript' src='./ClipReport/js/clipreport5.js'></script>
<script type='text/javascript' src='./ClipReport/js/UserConfig5.js'></script>
<script type='text/javascript'>
	
function html2xml(divPath){	
    var reportkey = "<%=resultKey%>";
	var report = createReport("./report_server", reportkey, document.getElementById(divPath));
   
	//리포트 실행
    report.view();
}
</script>
</head>
<body onload="html2xml('targetDiv1')">
<div id='targetDiv1' style='position:absolute;top:5px;left:5px;right:5px;bottom:5px;'>
	<span style="visibility: hidden; font-family:나눔고딕">.</span>
	<span style="visibility: hidden; font-family:NanumGothic">.</span>
</div>
</body>
</html>
