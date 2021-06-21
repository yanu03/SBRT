package kr.tracom.ws;



public class WsMessage {

	private String content;
	
	public WsMessage() {
		
	}
	
	public WsMessage(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
	
}
