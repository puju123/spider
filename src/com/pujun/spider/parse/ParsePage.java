package com.pujun.spider.parse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pujun.spider.storage.SpiderDoc;
import com.pujun.spider.storage.SpiderDocDao;
import com.pujun.spider.tool.Const;
import com.pujun.spider.tool.MD5Signature;

public class ParsePage {
    
	public void parse(SpiderDoc spiderDoc) {
		//数据库查询待解析url
		System.out.println("开始解析。。。。。。。。。。。。。。。。。" + spiderDoc.getUrl());
		LinkParser linkParser=new LinkParser();
		linkParser.setSpiderDoc(spiderDoc);
		linkParser.parse();
		linkParser.getOutlinks();
		insertOutlinks(linkParser.getOutlinks());
		spiderDoc.setStatus(Const.PARSED);
	}

	private void insertOutlinks(ArrayList<String> outlinks) {
		// TODO Auto-generated method stub
		SpiderDocDao spiderDocDao=new SpiderDocDao();
		List<SpiderDoc> list=new ArrayList<SpiderDoc>();
		for (int i = 0; i < outlinks.size(); i++) {
			SpiderDoc spiderDoc=new SpiderDoc();
			spiderDoc.setUrl(outlinks.get(i));
			spiderDoc.setId(MD5Signature.calculate(outlinks.get(i)));
			list.add(spiderDoc);
			if (list.size()>=200) {
				try {
					spiderDocDao.add(list);
					list.clear();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		outlinks.clear();
		try {
			spiderDocDao.add(list);
			list.clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
