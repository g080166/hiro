package com.hiro.spider.process.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.ClientProtocolException;

public interface IWeiXinService {

	String getHistoryArticleList(String cookies, String biz);


}
