package com.hiro.spider.core.httpclient;

public class SinglePhoto {

	private String title = "";
	private String author ="";
	private String digest ="";
	private String filePath ="";
	private boolean isDisplayCover = true;
    private String sourceurl = "";
    private String content  ="";
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public boolean isDisplayCover() {
		return isDisplayCover;
	}
	public void setDisplayCover(boolean isDisplayCover) {
		this.isDisplayCover = isDisplayCover;
	}
	public String getSourceurl() {
		return sourceurl;
	}
	public void setSourceurl(String sourceurl) {
		this.sourceurl = sourceurl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}