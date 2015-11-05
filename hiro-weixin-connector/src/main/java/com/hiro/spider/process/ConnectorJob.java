package com.hiro.spider.process;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiro.spider.process.cache.WeiXinManagerCache;
import com.hiro.spider.process.service.IWeiXinCookieService;

@Component
public class ConnectorJob {
	
	@Autowired
	IWeiXinCookieService weixinCookieService;
	
	@Autowired
	WeiXinManagerCache weixinManagerCache;
	
	String passTicket;
	String cookie;
	String sysUrl;
	String manager;
	String managerName;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Scheduled(fixedDelay=3*1000)
	public void connect() throws UnsupportedEncodingException, ParseException, IOException, KeyManagementException,
			NoSuchAlgorithmException, InterruptedException {
		try{
			if(StringUtils.isEmpty(manager)||StringUtils.isEmpty(passTicket)||StringUtils.isEmpty(cookie)||StringUtils.isEmpty(sysUrl)){
				logger.info("数据异常，[manager]"+manager+",[passTicket]"+passTicket+",[cookie]"+cookie+",[sysUrl]"+sysUrl);
				return;
			}
			
			weixinCookieService.beginToSyn(passTicket, cookie, sysUrl);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Scheduled(fixedDelay=2*1000)
	public void getManager(){
		manager = weixinManagerCache.getManager();
//		JSONObject managerJSONObject = JSONObject.parseObject(manager);
		JSONArray managerJSONArray = JSONArray.parseArray(manager);
		
		if(null == managerJSONArray ||managerJSONArray.size()==0){
			return;
		}
		
		JSONObject managerJSONObject = managerJSONArray.getJSONObject(0);
		passTicket = managerJSONObject.getString("passTicket");
		cookie = managerJSONObject.getString("cookie");
		sysUrl = managerJSONObject.getString("sysUrl");
		managerName = managerJSONObject.getString("managerName");
		
		
	}
}
