package com.hiro.spider.process.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.hiro.spider.data.entity.PublicNumber;

public interface ISouGouService {

	void saveOfficialAccount(PublicNumber publicNumber) throws UnsupportedEncodingException;

	List<PublicNumber> getOfficalAccount();

}
