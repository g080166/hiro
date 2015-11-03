package com.hiro.spider.process.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class WeiXinService implements IWeiXinService{

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
	
	
	public JSONObject visitHistoryUrl(String cookies,String biz){
		try {
			resetCookie(cookies);
			if (StringUtils.isEmpty(biz)) {
				biz = "MzAxMDAzNjQxOQ==";
			}

			String requrl = URLEncoder
					.encode("http://mp.weixin.qq.com/mp/getmasssendmsg?__biz="
							+ biz + "#wechat_webview_type=1&wechat_redirect",
							"utf-8");

			String historyUrl = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxcheckurl?requrl="
					+ requrl;
			Map<String, String> historyHeaders = new HashMap<String, String>();
			String historyCookie = "MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; pgv_pvi="
					+ PGV_PVI
					+ "; pgv_si="
					+ PGV_SI
					+ "; webwxuvid="
					+ WEB_WX_UVID
					+ "; wxloadtime="
					+ WX_LOAD_TIME
					+ "; mm_lang=zh_CN; wxpluginkey="
					+ WX_PLUNGIN_KEY
					+ "; wxuin="
					+ WX_UIN
					+ "; wxsid="
					+ WX_SID
					+ "; webwx_data_ticket=" + WEB_WX_DATA_TICKET + ";";
			// logger.info(historyCookie);
			historyHeaders.put("Cookie", historyCookie);
			Result historyResult = SendRequest.sendGet(historyUrl,
					historyHeaders, null, ENCODE, true);
			String historyResponse = EntityUtils.toString(historyResult
					.getHttpEntity());

			// 获取到分享到微信客户端的历史列表
			String uinReg = "(uin|key|pass_ticket)\\s*?=\\s*?\"([\\s\\S]*?)\"";
			Pattern uinPattern = Pattern.compile(uinReg);
			Matcher matcher = uinPattern.matcher(historyResponse);
			JSONObject userInfo = new JSONObject();
			while (matcher.find()) {
				// logger.info(matcher.group(1) + ":" + matcher.group(2));
				userInfo.put(matcher.group(1), matcher.group(2));
			}
			String uin = userInfo.getString("uin");
			String key = userInfo.getString("key");
			String passTicket = userInfo.getString("pass_ticket");

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("uin", uin);
			jsonObj.put("key", key);
			jsonObj.put("passTicket", passTicket);
			return jsonObj;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

	}
	
	@Override
	public String getHistoryArticleList(String cookies, String biz) {
		try {
			JSONObject userInfo = visitHistoryUrl(cookies, biz);
			String uin = userInfo.getString("uin");
			String key = userInfo.getString("key");
			String pass_ticket = userInfo.getString("pass_ticket");
			// 第二步：获取到基本信息后
			String articleListUrl = "http://mp.weixin.qq.com/mp/getmasssendmsg?__biz="
					+ biz
					+ "&uin="
					+ uin
					+ "&key="
					+ key
					+ "&devicetype=webwx&version=70000001&lang=zh_CN&pass_ticket="
					+ pass_ticket + "&f=json&count=" + 5000;
			logger.info(articleListUrl);
			Map<String, String> articleHeaderMap = new HashMap<String, String>();
			String articleHeaderCookie = "MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; pgv_pvi="
					+ PGV_PVI
					+ "; webwx_data_ticket="
					+ WEB_WX_DATA_TICKET
					+ ";";
			articleHeaderMap.put("Cookie", articleHeaderCookie);
			Result result = SendRequest.sendGet(articleListUrl,
					articleHeaderMap, null, ENCODE, true);
			String articleListContent = EntityUtils.toString(result
					.getHttpEntity());
			logger.info(articleListContent);
			return articleListContent;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
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
}
