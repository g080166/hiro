package com.hiro.spider.core.util;




public class MySQL5Dialect extends org.hibernate.dialect.MySQL5InnoDBDialect{
	public MySQL5Dialect() {
	   super();
	   // register additional hibernate types for default use in scalar
	   // sqlquery type auto detection
//	 registerHibernateType(Types.INTEGER,StandardBasicTypes.STRING.getName());
//	 registerHibernateType(Types.INTEGER,StandardBasicTypes.INTEGER.getName());
	}
	}
