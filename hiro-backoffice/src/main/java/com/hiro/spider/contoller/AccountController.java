package com.hiro.spider.contoller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiro.spider.core.BaseResponse;
import com.hiro.spider.core.Globals;
import com.hiro.spider.process.cache.WeiXinManagerCache;
import com.hiro.spider.process.service.IWeiXinService;

@Component
@RequestMapping("/account/")
public class AccountController {
	
	@Autowired
	WeiXinManagerCache weixinManagerCache;
	
	@Autowired
	IWeiXinService weixinService;


	/***
	 * 添加用户
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addAccount", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public BaseResponse<String> addAccount(HttpServletRequest request) {
		try {
			String managerName = request.getParameter("managerName");
			String cookie = request.getParameter("cookie");
			String sysUrl = request.getParameter("sysUrl");

			if (StringUtils.isEmpty(managerName) || StringUtils.isEmpty(cookie)) {
				System.out.println("managerName:"
						+ request.getParameter("managerName") + "," + "cookie:"
						+ request.getParameter("cookie"));
				return new BaseResponse<String>(Globals.CODE_REQUEST_ERROR,
						Globals.MSG_PARAM_ILLEGAL);
			}

			String crawlerAccountJSON = weixinManagerCache.getManager();
			JSONArray crawlerAccountJSONArray = new JSONArray();
			if (!StringUtils.isEmpty(crawlerAccountJSON)) {
				crawlerAccountJSONArray = JSONArray
						.parseArray(crawlerAccountJSON);
			}
			boolean result = false;
			JSONObject jsonObject = new JSONObject();
			for(int i = 0;i<crawlerAccountJSONArray.size();i++){
				if (crawlerAccountJSONArray.getJSONObject(i)
						.getString("managerName")
						.equals(request.getParameter("userName"))) {
					jsonObject = crawlerAccountJSONArray.getJSONObject(i);
					result = true;
					break;
				}
			}

			JSONObject accountObj = weixinService.visitHistoryUrl(
					cookie, null);

			if (StringUtils.isEmpty(accountObj.getString("passTicket"))) {
				return new BaseResponse<String>(Globals.CODE_SERVER_ERROR,
						"Cookie无效");
			}


			jsonObject.put("managerName", managerName);
			jsonObject.put("cookie", cookie);
			jsonObject.put("passTicket", accountObj.get("passTicket"));
			jsonObject.put("sysUrl", sysUrl);
			jsonObject.put("isAvailable", true);
			System.out.println(crawlerAccountJSONArray);

			if (!result) {
				crawlerAccountJSONArray.add(jsonObject);
			}
			System.out.println(crawlerAccountJSONArray);
			weixinManagerCache.setManager(crawlerAccountJSONArray
					.toJSONString());
			return new BaseResponse<String>(Globals.CODE_SUCCESS,
					Globals.MSG_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseResponse<String>(Globals.CODE_SERVER_ERROR,
					Globals.MSG_SERVER_EXCEPTION + ":" + e.getMessage());
		}
	}
}
