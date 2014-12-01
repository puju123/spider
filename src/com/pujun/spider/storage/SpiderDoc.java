package com.pujun.spider.storage;

public class SpiderDoc {
	private String id;
	private String title;
	private String fetchtime;
	private String html;
	private String content;
	private String pubdate;
	private String author;
	private String original;
	private String url;
	private String status;
	private String charset;
	private int fetchcode;

	public void clear() {
		id=null;
		title=null;
		fetchtime=null;
		html=null;
		content=null;
		pubdate=null;
		author=null;
		original=null;
		url=null;
		status=null;
		charset=null;
		fetchcode=0;
	}
	/**
	 * @return the fetchcode
	 */
	public int getFetchcode() {
		return fetchcode;
	}
	/**
	 * @param fetchcode the fetchcode to set
	 */
	public void setFetchcode(int fetchcode) {
		this.fetchcode = fetchcode;
	}
	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}
	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the fetchtime
	 */
	public String getFetchtime() {
		return fetchtime;
	}
	/**
	 * @param fetchtime the fetchtime to set
	 */
	public void setFetchtime(String fetchtime) {
		this.fetchtime = fetchtime;
	}
	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}
	/**
	 * @param html the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the pubdate
	 */
	public String getPubdate() {
		return pubdate;
	}
	/**
	 * @param pubdate the pubdate to set
	 */
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the original
	 */
	public String getOriginal() {
		return original;
	}
	/**
	 * @param original the original to set
	 */
	public void setOriginal(String original) {
		this.original = original;
	}


}
