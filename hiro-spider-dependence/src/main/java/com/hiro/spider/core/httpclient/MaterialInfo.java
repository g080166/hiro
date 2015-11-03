package com.hiro.spider.core.httpclient;

public class MaterialInfo {

	private long file_id;
	private String name;
	private long update_time;
	private String size;
	
	public long getFile_id() {
		return file_id;
	}
	public void setFile_id(long fileId) {
		file_id = fileId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(long updateTime) {
		update_time =updateTime;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
}