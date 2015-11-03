package com.hiro.spider.process;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hiro.spider.core.crawler.CrawlerFactory;
import com.hiro.spider.data.entity.PublicNumber;
import com.hiro.spider.process.service.ISouGouService;


@Component
public class SouGouOfficialAccountJob implements CrawlerFactory{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	ISouGouService souGouService;

	@Scheduled(fixedDelay=60*60*24*1000)
	public void start() throws UnsupportedEncodingException, ParseException, IOException, KeyManagementException,
			NoSuchAlgorithmException, InterruptedException {
		// TODO Auto-generated method stub
		getPublicNumbers();
	}
	
	private void getPublicNumbers() throws KeyManagementException, ClientProtocolException, NoSuchAlgorithmException, IOException{
		try{
			List<PublicNumber> publicNumbers = souGouService.getOfficalAccount();
			for(PublicNumber publicNumber:publicNumbers){
				souGouService.saveOfficialAccount(publicNumber);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void stop() {
		// TODO Auto-generated method stub
		
	}
}
