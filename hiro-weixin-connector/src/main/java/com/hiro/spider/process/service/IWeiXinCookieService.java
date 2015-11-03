package com.hiro.spider.process.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.ClientProtocolException;

public interface IWeiXinCookieService {

	void beginToSyn(String passTikect, String cookie, String sysUrl)
			throws KeyManagementException, ClientProtocolException, NoSuchAlgorithmException, IOException;

}
