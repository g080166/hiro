package com.hiro.spider.core;

public class BaseResp{
	private int ret;
	private String err_msg;
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String errMsg) {
		err_msg = errMsg;
	}
	
}