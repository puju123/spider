package com.pujun.spider.fetch;

import java.util.Date;

import com.pujun.spider.storage.SpiderDoc;
import com.pujun.spider.tool.ResponseUtil;

public class HtmlFetcher extends Fetcher {
    private String url=null;
    private SpiderDoc spiderDoc=null;
    public HtmlFetcher(String url) {
		this.url=url;
		spiderDoc=new SpiderDoc();
	}
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//	    ResponseUtil responseUtil=new ResponseUtil();
//	    String html=null;
//	    int statusCode=0;
//	    responseUtil.getResponse(url);
//	    statusCode=responseUtil.getStatusCode();
//	    spiderDoc.setUrl(url);
//	    spiderDoc.setStatus(statusCode);
//	    spiderDoc.setFetchtime(new Date().toString());
//	    if (statusCode==200||statusCode==304) {
//	    	html=responseUtil.getHtml();
//	    	spiderDoc.setHtml(html);
//	    	spiderDoc.setCharset(responseUtil.getCharset());
//		}
//	}
	@Override
	public SpiderDoc call() throws Exception {
		// TODO Auto-generated method stub
	    ResponseUtil responseUtil=new ResponseUtil();
	    String html=null;
	    int statusCode=0;
	    responseUtil.getResponse(url);
	    statusCode=responseUtil.getStatusCode();
	    spiderDoc.setUrl(url);
	    spiderDoc.setStatus(statusCode);
	    spiderDoc.setFetchtime(new Date().toString());
	    if (statusCode==200||statusCode==304) {
	    	html=responseUtil.getHtml();
	    	spiderDoc.setHtml(html);
	    	spiderDoc.setCharset(responseUtil.getCharset());
		}
	    return spiderDoc;
	}
	
}
