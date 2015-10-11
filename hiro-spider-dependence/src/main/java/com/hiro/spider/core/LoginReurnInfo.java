package com.hiro.spider.core;

public class LoginReurnInfo {
	String redirect_url;
	private int Ret;
	private String ErrMsg;
	private int  ShowVerifyCode;
	private int ErrCode;
	private int WtloginErrCode;
	public int getRet() {
		return Ret;
	}
	public void setRet(int ret) {
		Ret = ret;
	}
	public String getErrMsg() {
		return ErrMsg;
	}
	public void setErrMsg(String errMsg) {
		ErrMsg = errMsg;
	}
	public int getShowVerifyCode() {
		return ShowVerifyCode;
	}
	public void setShowVerifyCode(int showVerifyCode) {
		ShowVerifyCode = showVerifyCode;
	}
	public int getErrCode() {
		return ErrCode;
	}
	public void setErrCode(int errCode) {
		ErrCode = errCode;
	}
	public int getWtloginErrCode() {
		return WtloginErrCode;
	}
	public void setWtloginErrCode(int wtloginErrCode) {
		WtloginErrCode = wtloginErrCode;
	}

	public String getRedirect_url() {
		return redirect_url;
	}

	public void setRedirect_url(String redirect_url) {
		this.redirect_url = redirect_url;
	}

	@Override
	public String toString() {
		return "LoginReurnInfo [redirect_url=" + redirect_url + ", Ret=" + Ret
				+ ", ErrMsg=" + ErrMsg + ", ShowVerifyCode=" + ShowVerifyCode
				+ ", ErrCode=" + ErrCode + ", WtloginErrCode=" + WtloginErrCode
				+ "]";
	}
}
