package com.xul.validate;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xujl.http.method.GetRequest;
import com.xujl.http.method.PostRequest;
//http://gx.gsxt.gov.cn/sydq/loginSydqAction!sydq.dhtml
public class Steps {
	static CloseableHttpClient httpClient = HttpClientManager.getConnection();
	static Logger log = LoggerFactory.getLogger(Steps.class);
	public static final String ID="times";
	public static final String CHALLENGE="challenge";
	static final Map<String,String> header=new HashMap<String, String>();
	static{
	header.put("Referer", "http://bj.gsxt.gov.cn/sydq/loginSydqAction!sydq.dhtml");
	header.put("Host", "bj.gsxt.gov.cn");
	header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:46.0) Gecko/20100101 Firefox/46.0");
	}
	public static JSONObject one(){
		String times=new Date().getTime()+"";
		try {
			new GetRequest().request(httpClient, "http://bj.gsxt.gov.cn/sydq/loginSydqAction!sydq.dhtml", null, null);
			CloseableHttpResponse response = new GetRequest().request(httpClient, "http://bj.gsxt.gov.cn/pc-geetest/register?t="+times, null, null);
			
		
			String body = 	EntityUtils.toString(response.getEntity());
			log.info(body);
			JSONObject parseObject = JSON.parseObject(body);
			parseObject.put("times", times);
			return parseObject;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	} 
	public static String tow(){
		JSONObject  config =one();
		
		String url="http://api.geetest.com/get.php?callback=geetest_" 
			    + random() + "&challenge="
			    + config.getString("challenge") + "&gt=" 
			    + config.getString("gt") + "&offline=false&product=float";

		
		Connection connect = Jsoup.connect(url);
		try {
			Response response = connect.execute();
			String body = response.body();
			log.info(body);
			JSONObject parseObject = JSON.parseObject(body);
			return parseObject.getString("gt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	//http://api.geetest.com/getfrontlib.php?callback=geetest_
	
    public static String random()
    {
    	log.debug(Math.random()+"");
        return ""+((Math.random() * 10000)+ (new Date().getTime()));
    }
    public static String entlist(String hallenge,String keyword)
    {
    	String url3="http://bj.gsxt.gov.cn/es/esAction!entlist.dhtml?urlflag=0&challenge="+hallenge;
		List<NameValuePair> paramsList=new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("clear", "请输入企业名称、统一社会信用代码或注册号"));
		paramsList.add(new BasicNameValuePair("keyword",keyword));
		paramsList.add(new BasicNameValuePair("nowNum", ""));
		paramsList.add(new BasicNameValuePair("urlflag", 0+""));
		try {
			return EntityUtils.toString(new PostRequest().request(httpClient, url3, null, paramsList, Steps.header).getEntity());
		}  catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    public static String validate(String challenge,String validate)
    {
    	
    	String url2="http://bj.gsxt.gov.cn/pc-geetest/validate";
		List<NameValuePair> paramsList=new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("geetest_challenge", challenge));
		paramsList.add(new BasicNameValuePair("geetest_validate", validate));
		paramsList.add(new BasicNameValuePair("geetest_seccode", validate+"|jordan"));
		 CloseableHttpResponse response;
		try {
			response = new PostRequest().request(httpClient, url2, null, paramsList, header);
			return EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    
}
