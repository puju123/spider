package com.pujun.spider.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;

import com.pujun.spider.storage.SpiderDoc;
import com.pujun.spider.storage.SpiderDocDao;
import com.pujun.spider.tool.Const;

public class TestParseMetadata{
    public static void main(String[] args) {
    	TestParseMetadata testParseMetadata= new TestParseMetadata();
		List<SpiderDoc> resultList=testParseMetadata.getSpiderDocList();
		if (resultList!=null) {
			ParseMetadata parseMetadata=new ParseMetadata();
			for (int i = 0; i < resultList.size(); i++) {
				parseMetadata.setSpiderDoc(resultList.get(i));
				parseMetadata.parse();
				System.out.println("正文为："+parseMetadata.spiderDoc.getContent());
			}
		}
	}
	/*
	 * 查询数据库
	 * @Description: TODO
	 * @author xinhua
	 * @date 2014年12月2日 下午4:09:44 
	 * @update 
	 * @return
	 */
   private List<SpiderDoc> getSpiderDocList() {
       // TODO Auto-generated method stub
       SpiderDocDao spiderDocDao=new SpiderDocDao();
       HashMap<String, Object> params=new HashMap<String, Object>();
       params.put("startKey", 0);
       params.put("size", 1000);
       params.put("status", Const.FETCHED);
       return spiderDocDao.selectAll();
   }
    
}
