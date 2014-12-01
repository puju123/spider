package com.pujun.spider.storage;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class SpiderDocDao extends DBBase {

	@Override
	public int add(Object record) {
		// TODO Auto-generated method stub
		SqlSession session=getConnection().openSession();
//		LOG.info("SpiderDoc插入记录:"+ record);
		return session.insert("add", record);
	}
	public int add(List<SpiderDoc> records){
		// TODO Auto-generated method stub
//		SqlSession session=getConnection().openSession();
//		session.getConnection().setAutoCommit(false); 
//		int code=session.insert("addList", records);
//		session.commit();
	    SqlSession sqlSession = getConnection().openSession(ExecutorType.BATCH);
	    int i = 0; 
	    try {
			for (;i < records.size(); i++) {
				sqlSession.insert("add",records.get(i));
//				sqlSession.commit();
//				sqlSession.flushStatements();
		    }
//	      sqlSession.flushStatements();
	      sqlSession.commit();
	    }catch (Exception e) {
			// TODO: handle exception
//	    	System.out.println(records.get(i).getUrl());
//	    	System.out.println(records.get(i).getHtml());
	    	e.printStackTrace();
		} finally {
	      sqlSession.close();
	    }
	    System.out.println("插入记录：" + records.size());
//		TransactionFactory transactionf=new JdbcTransactionFactory();
//		Transaction transaction=transactionf.newTransaction(session.getConnection());
//		try {
//			
//			for (int i = 0; i < records.size(); i++) {
//				session.insert("add",records.get(i));
//			}
//			session.commit();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			session.rollback();
//		}
//		LOG.info("SpiderDoc插入记录多条记录:"+ records.size());
		return 0;
	}
	public List<SpiderDoc> selectAll() {
		// TODO Auto-generated method stub
		SqlSession session=getConnection().openSession();
//		LOG.info("SpiderDoc查询所有记录。");
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
