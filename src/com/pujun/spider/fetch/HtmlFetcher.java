package com.pujun.spider.fetch;

import java.util.Date;

import com.pujun.spider.storage.SpiderDoc;
import com.pujun.spider.tool.Const;
import com.pujun.spider.tool.MD5Signature;
import com.pujun.spider.tool.ResponseUtil;
/**
 * url内容抓取实现类
 * @Title: HtmlFetcher.java 
 * @Description: TODO
 * @author xinhua
 * @date 2014年12月2日 下午4:10:43
 */
public class HtmlFetcher{
	public SpiderDoc fetch(SpiderDoc spiderDoc) {
		// TODO Auto-generated method stub
//	    System.out.println("抓取记录第：" +urlCount+"条:"+ url);
		String url=spiderDoc.getUrl();
	    ResponseUtil responseUtil=new ResponseUtil();
	    String html=null;
	    int statusCode=0;
	    responseUtil.getResponse(url);
	    statusCode=responseUtil.getStatusCode();
//	    spiderDoc.setUrl(url);
	    spiderDoc.setId(MD5Signature.calculate(url));
	    spiderDoc.setFetchcode(statusCode);
	    spiderDoc.setFetchtime(new Date().toString());
	    if (statusCode==200||statusCode==304) {
	    	html=responseUtil.getHtml();
	    	spiderDoc.setHtml(html);
	    	spiderDoc.setCharset(responseUtil.getCharset());
	    	spiderDoc.setStatus(Const.FETCHED);
		}
	    return spiderDoc;
	}
	
}
