package com.pujun.spider.storage;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DBBase {
	//日志
	public static final Logger LOG=LoggerFactory.getLogger(DBBase.class);
	//连接对象
	private static SqlSessionFactory sqlSessionFactory;
	/**
	 * 取得数据库连接
	 * @Description: TODO
	 * @author xinhua
	 * @date 2014年11月19日 下午1:41:13 
	 * @update 
	 * @return SqlSessionFactory
	 */
    public SqlSessionFactory getConnection() {
    	if (sqlSessionFactory==null) {
        	String resource = "mybatis-config.xml";
        	InputStream inputStream;
			try {
				inputStream = Resources.getResourceAsStream(resource);
	        	sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return sqlSessionFactory;
	}
    public abstract int add(Object record);
    public abstract int update(Object record);
    public abstract int delete(Object record);
}
