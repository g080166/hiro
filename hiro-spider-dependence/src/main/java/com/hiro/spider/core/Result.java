package com.hiro.spider.core;

import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

/**
 * 灏佷綇璇锋眰杩斿洖鐨勫弬鏁�
 * @author Legend銆�
 *
 */

public class Result {
    
	private String cookie;
	private int statusCode;
	private HashMap<String, Header> headerAll;
	private HttpEntity httpEntity;
	
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public HashMap<String, Header> getHeaders() {
		return headerAll;
	}
	
	public void setHeaders(Header[] headers){
		headerAll = new HashMap<String, Header>();
		for (Header header : headers) {
			headerAll.put(header.getName(), header);
		}
	}
	public HttpEntity getHttpEntity() {
		return httpEntity;
	}
	public void setHttpEntity(HttpEntity httpEntity) {
		this.httpEntity = httpEntity;
	}
}
