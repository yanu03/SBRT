<%@page import="com.clipsoft.clipreport.oof.OOFFile"%>
<%@page import="com.clipsoft.clipreport.oof.OOFDocument"%>
<%@page import="java.io.File,java.util.*,java.net.*"%>
<%@page import="com.clipsoft.clipreport.server.service.ReportUtil"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String reportPath = request.getParameter("reportPath");

String paramArr[] = request.getParameter("param").split(":");
OOFDocument oof = OOFDocument.newOOF();

OOFFile file = null;

if(reportPath==null || reportPath.isEmpty())
	file = oof.addFile("crf.root", "%root%/crf/CLIP.crf");
else 
	file = oof.addFile("crf.root", "%root%/crf/"+reportPath);

oof.addConnectionData("*","sbrt");
for(String param:paramArr){
	String tempArr[] = param.split("=");
	if(tempArr==null||tempArr.length<2)continue;
	String key = tempArr[0].replace(" ", "");
	String value = tempArr[1].replace(" ", "");
	value = URLDecoder.decode(value, "UTF-8");	
	file.addField(key, value);
}

String style = request.getParameter("style");
if (null != style) {
	style = style.replaceAll(" ", "");
} else {
	style = "";
}
Map<String, Object> styleMap = new HashMap<String, Object>();
String parseArr[] = {"position","top","left","right","bottom","width","height"};
for(String parse: parseArr){
	if(style.indexOf(parse)!=-1){
		String value = style.substring(style.indexOf(parse)+parse.length()+1);
		value = value.indexOf(";")!=-1?value.substring(0,value.indexOf(";")):value;
		styleMap.put(parse,value);
	}
}

String propertyPath  = request.getSession().getServletContext().getRealPath("/") + File.separator +  "WEB-INF" + File.separator + "clipreport5" + File.separator + "clipreport5.properties";
//String propertyPath  = "C:\\sbrt-web\\workspace\\BMS-Web\\src\\main\\webapp\\WEB-INF\\clipreport5\\clipreport5.properties";

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
	
$(document).ready(function()
{	
	//document.getElementById("reportSupport").style = "<%=style%>";
	//position:absolute;top:100px;left:15px;right:5px;bottom:5px;width:1024px;height:700px
	
	$( "#reportSupport" ).css({position:'<%=styleMap.get("position")%>', top:'<%=styleMap.get("top")%>'
		, left:'<%=styleMap.get("left")%>', right:'<%=styleMap.get("right")%>', bottom:'<%=styleMap.get("bottom")%>'
		, width:'<%=styleMap.get("width")%>', height:'<%=styleMap.get("height")%>'});
	html2xml('reportSupport');
});
function html2xml(divPath){	
    var reportkey = "<%=resultKey%>";
	var report = createReport("./report_server", reportkey, document.getElementById(divPath));
   
	//리포트 실행
    report.view();
}
</script>
</head>
<body onload="">
<div id='reportSupport' class='reportSupport' style='position:absolute;top:100px;left:15px;right:5px;bottom:5px;width:100%;height:100%'></div>
</body>
</html>
