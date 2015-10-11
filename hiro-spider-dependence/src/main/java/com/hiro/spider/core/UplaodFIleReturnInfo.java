package com.hiro.spider.core;

public class UplaodFIleReturnInfo {

	private String location;
	private String type;
	private String content;
	private BaseResp base_resp;
	

	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public BaseResp getBase_resp() {
		return base_resp;
	}


	public void setBase_resp(BaseResp baseResp) {
		base_resp = baseResp;
	}
}