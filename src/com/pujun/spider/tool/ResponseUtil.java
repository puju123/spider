package com.pujun.spider.tool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
/**
 * url抓取类，封装抓取动作和内容取得
 * @Title: ResponseUtil.java 
 * @Description: TODO
 * @author xinhua
 * @date 2014年11月21日 下午1:50:16
 */
public class ResponseUtil {
	private CloseableHttpResponse response;
	private int statusCode;
	private String html;
	private String charset;
	private static final int CHUNK_SIZE = 2000;

	// NUTCH-1006 Meta equiv with single quotes not accepted
	private static Pattern metaPattern = Pattern.compile(
			"<meta\\s+([^>]*http-equiv=(\"|')?content-type(\"|')?[^>]*)>",
			Pattern.CASE_INSENSITIVE);
	private static Pattern charsetPattern = Pattern.compile(
			"charset=\\s*([a-z][_\\-0-9a-z]*)", Pattern.CASE_INSENSITIVE);
   
	public void getResponse(String url) {
		if (StringUtils.isBlank(url)) {
			return;
		}
		try {
			get(url,2000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX url抓取出错："+e.getMessage()+" : "+e.getCause().getMessage());
			statusCode=500;
		}
	}

	public void get(String url, Integer timeOut) throws Exception {

		HttpClient httpClient = HttpClients.createDefault();
		URIBuilder uri = new URIBuilder();
		uri.setPath(url);
//		uri.addParameters(params);
		HttpGet httpget = new HttpGet(uri.build());
//		setWenxinHeader(httpget);
        setHeader(httpget);

		// set Timeout
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(timeOut)
				.setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
		httpget.setConfig(requestConfig);
		// get responce
		HttpResponse response = httpClient.execute(httpget);
		// get http status code
		statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK) {
			// get result data
			HttpEntity hEntity = response.getEntity();
			ByteBuffer buffer;
			buffer = ByteBuffer.wrap(EntityUtils.toByteArray(hEntity));
			charset = sniffCharacterEncoding(buffer, "utf-8");
			html = Charset.forName(charset).decode(buffer).toString();
		}
	}
	private void setHeader(HttpGet httpget) {
		// TODO Auto-generated method stub
		// Set the User Agent in the header
		httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");
		// prefer English
		httpget.addHeader("Accept-Language", "en-us,en-gb,en;q=0.7,*;q=0.3");
		// prefer UTF-8
		httpget.addHeader("Accept-Charset",
				"utf-8,GBK,ISO-8859-1;q=0.7,*;q=0.7");
		// prefer understandable formats
		httpget.addHeader(
				"Accept",
				"text/html,application/xml;q=0.9,application/xhtml+xml,text/xml;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		// accept gzipped content
		httpget.addHeader("Accept-Encoding", "x-gzip, gzip, deflate");
	}

	private void setWenxinHeader(HttpGet httpget) {
		// TODO Auto-generated method stub
		httpget.addHeader("Host","weixin.sogou.com");
		httpget.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");
		httpget.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpget.addHeader("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpget.addHeader("Accept-Encoding","gzip, deflate");
		httpget.addHeader("Referer","http://weixin.sogou.com/");Referer: 
		httpget.addHeader("Cookie","CXID=93BB9FFC4F8B6FD3EA4B39B4452AF08B; SUID=8374786A791C900A5455C3920004C288; ad=eklLvlllll2UqtnvlllllVSMPjDlllllzXP1kZllll9llllllh7ll5@@@@@@@@@@; SUV=1411021347384051; IPLOC=CN1100; ssuid=9857610260; ABTEST=7|1417586642|v17; sct=3; SNUID=D1DF6ACEA2A7A8E627787939A28D4C8D"); 
		httpget.addHeader("Connection","keep-alive");
	}

	/**
	 * @return the response
	 */
	public CloseableHttpResponse getResponse() {
		return response;
	}
	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}
	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}
	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}
	/**
	 * Given a <code>ByteBuffer</code> representing an html file of an
	 * <em>unknown</em> encoding, read out 'charset' parameter in the meta tag
	 * from the first <code>CHUNK_SIZE</code> bytes. If there's no meta tag for
	 * Content-Type or no charset is specified, <code>null</code> is returned. <br />
	 * FIXME: non-byte oriented character encodings (UTF-16, UTF-32) can't be
	 * handled with this. We need to do something similar to what's done by
	 * mozilla
	 * (http://lxr.mozilla.org/seamonkey/source/parser/htmlparser/src/nsParser
	 * .cpp#1993). See also http://www.w3.org/TR/REC-xml/#sec-guessing <br />
	 *
	 * @param content
	 *            <code>ByteBuffer</code> representation of an html file
	 */

	private static String sniffCharacterEncoding(ByteBuffer content,
			String defaultEncoding) {
		int length = Math.min(content.remaining(), CHUNK_SIZE);

		// We don't care about non-ASCII parts so that it's sufficient
		// to just inflate each byte to a 16-bit value by padding.
		// For instance, the sequence {0x41, 0x82, 0xb7} will be turned into
		// {U+0041, U+0082, U+00B7}.
		String str = "";
		try {
			str = new String(content.array(), content.arrayOffset()
					+ content.position(), length, Charset.forName("ASCII")
					.toString());
		} catch (UnsupportedEncodingException e) {
			// code should never come here, but just in case...
			return null;
		}

		Matcher metaMatcher = metaPattern.matcher(str);
		String encoding = null;
		if (metaMatcher.find()) {
			Matcher charsetMatcher = charsetPattern.matcher(metaMatcher
					.group(1));
			if (charsetMatcher.find())
				encoding = new String(charsetMatcher.group(1));
		}
		return encoding == null ? defaultEncoding : encoding;
	}
}
