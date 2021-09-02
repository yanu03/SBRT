package kr.tracom.ws;



public class WsMessage {

	//private String content;
	private int attrId;
	private String vhcId;
	private String gpsX;
	private String gpsY;
	private String dvcId;
	private String dvcCond;

	

	public String getDvcId() {
		return dvcId;
	}

	public void setDvcId(String dvcId) {
		this.dvcId = dvcId;
	}

	public String getDvcCond() {
		return dvcCond;
	}

	public void setDvcCond(String dvcCond) {
		this.dvcCond = dvcCond;
	}

	public int getAttrId() {
		return attrId;
	}

	public void setAttrId(int attrId) {
		this.attrId = attrId;
	}


	public WsMessage() {
		
	}
	
	/*
	public WsMessage(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}*/
	

	public String getGpsX() {
		return gpsX;
	}

	public void setGpsX(String gpsX) {
		this.gpsX = gpsX;
	}

	public String getGpsY() {
		return gpsY;
	}

	public void setGpsY(String gpsY) {
		this.gpsY = gpsY;
	}


	public String getVhcId() {
		return vhcId;
	}


	public void setVhcId(String vhcId) {
		this.vhcId = vhcId;
	}
    
	
}
