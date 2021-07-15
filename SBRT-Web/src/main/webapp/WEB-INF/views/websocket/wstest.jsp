<%@page contentType="text/html; charset=utf-8" language="java"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?xml version="1.0" encoding="UTF-8"?>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ev="http://www.w3.org/2001/xml-events"
	xmlns:w2="http://www.inswave.com/websquare" xmlns:xf="http://www.w3.org/2002/xforms">
	<head>
		<w2:buildDate/>
		<xf:model>
			<xf:instance>
				<data xmlns=""/>
			</xf:instance>
		</xf:model>
		<script type="javascript"><![CDATA[ 
		]]></script>
		
		<script src="/webjars/sockjs-client/sockjs.min.js"></script>
		<script src="/webjars/stomp-websocket/stomp.min.js"></script>
		<script src="/websquare/externalJS/jquery/jquery-1.10.2.min.js"></script>
		<script src="/cm/js/websocket.js"></script>		
	
		
	</head>
	<body>
		<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being enabled. Please enable Javascript and reload this page!</h2></noscript>
		<div id="main-content" class="container">
		  <div class="row">
		    <div class="col-md-6">
		      <form class="form-inline">
		        <div class="form-group">
		          <label for="connect">WebSocket connection:</label>
		          <button id="connect" class="btn btn-default" type="submit">Connect</button>
		          <button id="disconnect" class="btn btn-default" type="submit" disabled="disabled">Disconnect
		          </button>
		        </div>
		      </form>
		    </div>
		    <div class="col-md-6">
		      <form class="form-inline">
		        <div class="form-group">
		          <label for="content">Enter message</label>
		          <input type="text" id="content" class="form-control" placeholder="Message here...">
		        </div>
		        <button id="send" class="btn btn-default" type="submit">Send</button>
		      </form>
		    </div>
		  </div>
		  <div class="row">
		    <div class="col-md-12">
		      <table id="conversation" class="table table-striped">
		        <thead>
		        <tr>
		          <th>Message</th>
		        </tr>
		        </thead>
		        <tbody id="message">
		        </tbody>
		      </table>
		    </div>
		  </div>
		</div>
	
	</body>
</html>