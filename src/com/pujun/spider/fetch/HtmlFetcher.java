package com.pujun.spider.fetch;

import java.util.Date;

import com.pujun.spider.storage.SpiderDoc;
import com.pujun.spider.tool.Const;
import com.pujun.spider.tool.MD5Signature;
import com.pujun.spider.tool.ResponseUtil;

public class HtmlFetcher extends Fetcher {
    private String url=null;
    private SpiderDoc spiderDoc=null;
    private long urlCount=0;
    public HtmlFetcher(String url, long urlCount) {
		this.url=url;
		spiderDoc=new SpiderDoc();
		this.urlCount=urlCount;
	}
	@Override
	public SpiderDoc call() throws Exception {
		// TODO Auto-generated method stub
	    System.out.println("抓取记录第：" +urlCount+"条:"+ url);
	    ResponseUtil responseUtil=new ResponseUtil();
	    String html=null;
	    int statusCode=0;
	    responseUtil.getResponse(url);
	    statusCode=responseUtil.getStatusCode();
	    spiderDoc.setUrl(url);
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
