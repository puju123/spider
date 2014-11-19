package com.pujun.spider.storage;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class SpiderDocDao extends DBBase {

	@Override
	public int add(Object record) {
		// TODO Auto-generated method stub
		SqlSession session=getConnection().openSession();
		LOG.info("SpiderDoc插入记录:"+ record);
		return session.insert("add", record);
	}
	public List<SpiderDoc> selectAll() {
		// TODO Auto-generated method stub
		SqlSession session=getConnection().openSession();
		LOG.info("SpiderDoc查询所有记录。");
		return session.selectList("selectall");
	}
	@Override
	public int update(Object record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Object record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
