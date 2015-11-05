package com.hiro.spider.core;

public class BaseResponse<T> {
	String ret;
	String msg;
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public BaseResponse(String ret, String msg) {
		super();
		this.ret = ret;
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
