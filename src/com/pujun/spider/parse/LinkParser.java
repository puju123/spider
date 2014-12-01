package com.pujun.spider.parse;

import java.util.ArrayList;

public class LinkParser extends Parser {
	private ArrayList<String> outlinks=new ArrayList<String>();

	/**
	 * @return the outlinks
	 */
	public ArrayList<String> getOutlinks() {
		return outlinks;
	}

	/**
	 * @param outlinks the outlinks to set
	 */
	public void setOutlinks(ArrayList<String> outlinks) {
		this.outlinks = outlinks;
	}

	@Override
	public void parse() {
		// TODO Auto-generated method stub
		outlinks.clear();
        String html=spiderDoc.getHtml();
        DOMContentUtils domContentUtils=new DOMContentUtils();
        domContentUtils.setConf();
        domContentUtils.getOutlinks(html, outlinks);
	}

}
