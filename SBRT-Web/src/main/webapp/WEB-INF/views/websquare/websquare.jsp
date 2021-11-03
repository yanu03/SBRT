<%@page contentType="text/html; charset=utf-8" language="java"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns='http://www.w3.org/1999/xhtml' xmlns:ev='http://www.w3.org/2001/xml-events' xmlns:w2='http://www.inswave.com/websquare' xmlns:xf='http://www.w3.org/2002/xforms'>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
		<link rel="shortcut icon" href="/cm/images/inc/favicon.ico" />
		<title>S-BRT</title>
		<script type="text/javascript">
			function i18nUrl (url){
			    var locale = WebSquare.cookie.getCookie( "locale" );
			    if(url.indexOf('w2xPath')>=0)return url;
			    if( locale == null || locale == '' ) {
			    	
			    	return "/I18N?w2xPath="+url;
			    } else {
			        return "/I18N?locale="+unescape(locale)+"&w2xPath="+url;
			    }
			}

			var WebSquareExternal = {"baseURI": "/websquare/", "w2xPath" : "<%=(String)request.getAttribute("movePage")%>" };
			 
			if (parent.WebSquare && (parent === this)) {
				// spa 적용 시 초기 iframe을 로딩하는 과정에서 w2xPath의 값이 없이 넘어와 초기 로딩 파일인 index_tabControl.xml을 호출한다.
				// iframe와 같이 상위에 웹스퀘어 객체가 있고 호출 페이지가 login 페이지가 아닌 경우 매개변수를 빈값으로 설정
				if((WebSquareExternal.w2xPath).toLowerCase().indexOf("/cm/main/index_") < 0) {
					WebSquareExternal.w2xPath = "/websquare/blank.xml";		
				} else {
					WebSquareExternal.w2xPath = "";	
				}
			}
		</script>
		<!-- <script src="https://apis.openapi.sk.com/tmap/jsv2?version=1&amp;appkey=l7xx6099b61644bf40d78594611b250c9694"></script> -->
		<script type="text/javascript" src="/cm/js/Chart.js/3.6.0/chart.js"></script>
	    <script type="text/javascript" src="/cm/js/Chart.js/3.6.0/Chart.min.js"></script>
	    <<!-- script type="text/javascript" src="/cm/js/moment.js/2.27.0/moment.min.js" integrity="sha512-rmZcZsyhe0/MAjquhTgiUcb4d9knaFc7b5xAfju483gbEXTkeJRUMIPk6s3ySZMYUHEcjKbjLjyddGWMrNEvZg==" crossorigin="anonymous"></script> -->
			
		<script type="text/javascript" src="http://dapi.kakao.com/v2/maps/sdk.js?appkey=31aa62fb189294413e43c24caeb82419&amp;libraries=services&amp;autoload=false"></script>
		<script type="text/javascript" src="/cm/js/map.js"></script>

		<script type="text/javascript" src="/websquare/javascript.wq?q=/bootloader"></script>
		<script type="text/javascript">
		kakao.maps.load(function() {
			// v3가 모두 로드된 후, 이 콜백 함수가 실행됩니다.
			var map = new kakao.maps.Map(node, options);
		});
		</script>
		<script type="text/javascript">
			window.onload = init;

			function init() {
				try{
					 WebSquare.startApplication(WebSquareExternal.w2xPath);
				} catch(e) {
					alert(e.message);
				}
			}
		</script>
		<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js?autoload=false"></script>
	</head>
<body></body>
</html>