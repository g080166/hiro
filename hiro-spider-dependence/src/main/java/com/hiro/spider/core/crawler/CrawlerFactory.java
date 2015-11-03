package com.hiro.spider.core.crawler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.ParseException;

public interface CrawlerFactory {

	void start() throws UnsupportedEncodingException, ParseException, IOException, KeyManagementException, NoSuchAlgorithmException, InterruptedException;
	
	void stop();
}
