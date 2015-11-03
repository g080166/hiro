package com.hiro.spider.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PublicNumber entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "public_number")
public class PublicNumber implements java.io.Serializable {

	// Fields

	private Integer id;
	private String weixinAccount;
	private String weixinName;
	private String weixinPicture;
	private String openid;

	// Constructors

	/** default constructor */
	public PublicNumber() {
	}

	/** full constructor */
	public PublicNumber(String weixinAccount, String weixinName,
			String weixinPicture, String openid) {
		this.weixinAccount = weixinAccount;
		this.weixinName = weixinName;
		this.weixinPicture = weixinPicture;
		this.openid = openid;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "weixin_account", length = 20)
	public String getWeixinAccount() {
		return this.weixinAccount;
	}

	public void setWeixinAccount(String weixinAccount) {
		this.weixinAccount = weixinAccount;
	}

	@Column(name = "weixin_name", length = 20)
	public String getWeixinName() {
		return this.weixinName;
	}

	public void setWeixinName(String weixinName) {
		this.weixinName = weixinName;
	}

	@Column(name = "weixin_picture")
	public String getWeixinPicture() {
		return this.weixinPicture;
	}

	public void setWeixinPicture(String weixinPicture) {
		this.weixinPicture = weixinPicture;
	}

	@Column(name = "openid", length =100)
	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Column(name = "biz", length =100)
	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}

	private String biz;


}