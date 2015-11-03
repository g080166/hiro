package com.hiro.spider.process.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiro.spider.core.exception.ServiceException;
import com.hiro.spider.core.httpclient.Result;
import com.hiro.spider.core.httpclient.SendRequest;
import com.hiro.spider.process.cache.CookieCache;
import com.hiro.spider.process.cache.WeiXinManagerCache;

@Service
public class WeiXinCookieService implements IWeiXinCookieService{

	@Autowired
	CookieCache cookieCache;
	
	@Autowired
	WeiXinManagerCache weixinManagerCache;
	
	String MM_WX_NOTIFY_STATE;
	String MM_WX_SOUND_STATE;
	String PGV_PVI;
	String PGV_SI;
	String WEB_WX_UVID;
	String WX_LOAD_TIME;
	String MM_LANG;
	String WX_PLUNGIN_KEY;
	String WEB_WX_DATA_TICKET;
	String WX_UIN;
	String WX_SID;
	String SKEY ;
	String SYNCKEY = "1_628969336|2_628969446|3_628969232|11_628969437|201_1439977891|203_1439977296|1000_1439945885";
	String ENCODE = "utf-8";
	
	static String KEY_PGV_PVI = "pgv_pvi";
	static String KEY_PGV_SI = "pgv_si";
	static String KEY_WEBWXUVID = "webwxuvid";
	static String KEY_WXLOADTIME = "wxloadtime";
	static String KEY_WXPLUGINKEY = "wxpluginkey";
	static String KEY_WXUIN = "wxuin";
	static String KEY_SID = "wxsid";
	static String KEY_SYNCKEY = "synckey";
	static String KEY_SKEY = "skey";
	static String KEY_WEBWX_DATA_TICKET = "webwx_data_ticket";
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void connect(){
		try{
			String managerJSON = weixinManagerCache.getManager();
			
			if(StringUtils.isEmpty(managerJSON)){
				logger.info("尚未添加管理员》》》》》》》》》》》》》》》");
				return;
			}
			
			JSONArray managerJSONArray = JSONArray.parseArray(managerJSON);
			if(null != managerJSONArray&&managerJSONArray.size()>0){
				for(int i =0;i<managerJSONArray.size();i++){
					JSONObject managerJSONObject = managerJSONArray.getJSONObject(i);
					String passTicket = managerJSONObject.getString("passTicket");
					String cookie = managerJSONObject.getString("cookie");
					String managerName = managerJSONObject.getString("managerName");
					String sysUrl = managerJSONObject.getString("sysUrl");
					if(StringUtils.isEmpty("passTicket")||StringUtils.isEmpty("cookie")){
						logger.info("缺失passTicket或者cookie");
						continue;
					}
					logger.info("开始进行同步：【用户名】"+managerName+",【cookie】"+cookie+",【passTicket】"+passTicket);
					beginToSyn(passTicket, cookie, sysUrl);
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	public Map<String, String> resetCookie(String cookies) {
		String[] cookieArray = cookies.split(";");
		Map<String, String> paramMap = new HashMap<String, String>();
		for (String cookie : cookieArray) {

			String[] _cookieArray = cookie.trim().split("=");
			if (_cookieArray.length < 2) {
				continue;
			}
			String name = _cookieArray[0];
			String value = _cookieArray[1];
			paramMap.put(name, value);
		}
		PGV_PVI = paramMap.get(KEY_PGV_PVI);
		PGV_SI = paramMap.get(KEY_PGV_SI);
		WEB_WX_UVID = paramMap.get(KEY_WEBWXUVID);
		WX_LOAD_TIME = paramMap.get(KEY_WXLOADTIME);
		WX_PLUNGIN_KEY = paramMap.get(KEY_WXPLUGINKEY);
		WEB_WX_DATA_TICKET = paramMap.get(KEY_WEBWX_DATA_TICKET);
		WX_UIN = paramMap.get(KEY_WXUIN);
		WX_SID = paramMap.get(KEY_SID);
		// SKEY = paramMap.get(KEY_SKEY);

		String[] requestedParam = { KEY_PGV_PVI, KEY_PGV_SI, KEY_WEBWXUVID,
				KEY_WXLOADTIME, KEY_WXPLUGINKEY, KEY_WEBWX_DATA_TICKET,
				KEY_WXUIN, KEY_SID };
		for (String param : requestedParam) {
			if (!paramMap.containsKey(param)) {
				throw new ServiceException("cookie缺失参数：" + param);
			}
		}

		return paramMap;

	}
	@Override
	public void beginToSyn(String passTikect, String cookie, String sysUrl)
			throws KeyManagementException, ClientProtocolException,
			NoSuchAlgorithmException, IOException {
		resetCookie(cookie);
		if (StringUtils.isEmpty(sysUrl)) {
			sysUrl = "https://webpush2.weixin.qq.com/cgi-bin/mmwebwx-bin/synccheck?r="
					+ System.currentTimeMillis()
					+ "&skey="
					+ URLEncoder.encode(SKEY, ENCODE)
					+ "&sid="
					+ WX_SID
					+ "&uin="
					+ WX_UIN
					+ "&deviceid=e069700934225693&synckey="
					+ URLEncoder.encode(SYNCKEY, ENCODE)
					+ "&lang=zh_CN&pass_ticket="
					+ URLEncoder.encode(passTikect, "utf-8");
		}
		Map<String, String> sysHeaders = new HashMap<String, String>();
		sysHeaders.put("Cookie", "pgv_pvi=" + PGV_PVI + "; pgv_si=" + PGV_SI
				+ "; webwx_data_ticket=" + WEB_WX_DATA_TICKET + "");
		sysHeaders.put("Connection", "keep-alive");
		sysHeaders
				.put("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.125 Safari/537.36");
		Result result = SendRequest.sendGet(sysUrl, sysHeaders, null, ENCODE,
				true);
		logger.info(EntityUtils.toString(result.getHttpEntity()));
	}
}
