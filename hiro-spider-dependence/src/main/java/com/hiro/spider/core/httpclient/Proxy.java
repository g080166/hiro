package com.hiro.spider.core.httpclient;

public class Proxy {
	@Override
	public String toString() {
		return "Proxy [port=" + port + ", ip=" + ip + "]";
	}
	public Proxy(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	int port;
	String ip;
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
