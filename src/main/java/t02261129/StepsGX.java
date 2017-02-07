package t02261129;

import java.awt.image.BufferedImage;
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
import com.jiyan.requestmenthod.get.GetRequest;
import com.jiyan.requestmenthod.post.PostRequest;

import geet.CombineImages;
import geet.FindXDiffRectangeOfTwoImage;
import geet.HttpClientManager;
//http://gx.gsxt.gov.cn/sydq/loginSydqAction!sydq.dhtml
public class StepsGX {
	static CloseableHttpClient httpClient = HttpClientManager.getConnection();
	static Logger log = LoggerFactory.getLogger(StepsGX.class);
	public static final String ID="times";
	public static final String CHALLENGE="challenge";
	static final Map<String,String> header=new HashMap<String, String>();
	//Cookie:GeeTestUser=59ecccee40a0649014658cc2eaa7a8ef; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%221593f0fc7923cb-0ad99b9339844-59123916-2073600-1593f0fc795218%22%7D; _ga=GA1.2.945726802.1482821716; Hm_lvt_25b04a5e7a64668b9b88e2711fb5f0c4=1482821716,1483514075,1485052077,1485247903; _qddaz=QD.b6fjge.8s08d5.ix75smtk
	static final Map<String,String> Cookie=new HashMap<String, String>(){{
		
		put("Accept","*/*");
		put("Accept-Encoding","gzip, deflate, sdch");
		put("Accept-Language","zh-CN,zh;q=0.8");
		put("Connection","keep-alive");
		put("Cookie","sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%221593f0fc7923cb-0ad99b9339844-59123916-2073600-1593f0fc795218%22%7D; GeeTestUser=2ffa90cda51fbbf7bf99ea3733f5418c; Hm_lvt_25b04a5e7a64668b9b88e2711fb5f0c4=1485247903,1486179437,1486185760,1486199382; _ga=GA1.2.945726802.1482821716; _qddaz=QD.b6fjge.8s08d5.ix75smtk");
		put("Host","api.geetest.com");
		put("Referer","http://gx.gsxt.gov.cn/sydq/loginSydqAction!sydq.dhtml");
		put("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
//		put("Cookie","GeeTestUser=59ecccee40a0649014658cc2eaa7a8ef; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%221593f0fc7923cb-0ad99b9339844-59123916-2073600-1593f0fc795218%22%7D; _ga=GA1.2.945726802.1482821716; Hm_lvt_25b04a5e7a64668b9b88e2711fb5f0c4=1482821716,1483514075,1485052077,1485247903; _qddaz=QD.b6fjge.8s08d5.ix75smtk");
//		put("Host","api.geetest.com");
//		put("Referer","http://gx.gsxt.gov.cn/sydq/loginSydqAction!sydq.dhtml");
//		put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:46.0) Gecko/20100101 Firefox/46.0");
	}};
	static{
	header.put("Referer", "http://gx.gsxt.gov.cn/sydq/loginSydqAction!sydq.dhtml");
	header.put("Host", "gx.gsxt.gov.cn");
	header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:46.0) Gecko/20100101 Firefox/46.0");
	}
	public static JSONObject one(){
		String times=new Date().getTime()+"";
		try {
			new GetRequest().request(httpClient, "http://gx.gsxt.gov.cn/sydq/loginSydqAction!sydq.dhtml", null, null);
			CloseableHttpResponse response = new GetRequest().request(httpClient, "http://gx.gsxt.gov.cn/pc-geetest/register?t="+times, null, null);
			
		
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
	public static JSONObject tow(){
		JSONObject  config =one();
		//http://api.geetest.com/gettype.php?gt=73a7e78f77d457e28a6ad4f12d4bb63e&callback=geetest_1485166572710
		String url="http://api.geetest.com/gettype.php?gt=" + config.getString("gt") + "&callback=geetest_" 
			    + random();

		
		try {
			CloseableHttpResponse response = new GetRequest().request(httpClient, url, null, Cookie);
			
			
			String body = 	EntityUtils.toString(response.getEntity());
			return config;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static JSONObject three(){
		JSONObject  config =tow();
		//http://api.geetest.com/get.php?gt=73a7e78f77d457e28a6ad4f12d4bb63e&challenge=ca8db21a6252fc137457ca9fb3306ab2&product=popup&offline=false&protocol=&path=/static/js/geetest.5.10.0.js&type=slide&callback=geetest_1485166578375 
		String url="http://api.geetest.com/get.php?gt=" + config.getString("gt") + 
				"&challenge="+config.getString(CHALLENGE) +"&product=popup&offline=true&protocol=&path=/static/js/geetest.5.10.0.js&type=slide&callback=geetest_" 
				+ random();
		
//		System.out.println(url);
		try {
			while(true){
			CloseableHttpResponse response = new GetRequest().request(httpClient, url, null, Cookie);
			
			
			String body = 	EntityUtils.toString(response.getEntity());
			if(body.contains("DOCTYPE"))
				continue;
//			System.out.println(body.substring(body.indexOf("{"), body.length()-1));
			return JSON.parseObject(body.substring(body.indexOf("{"), body.length()-1));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//http://api.geetest.com/getfrontlib.php?callback=geetest_
	
    public static String random()
    {
    	
        String random = String.valueOf(Double.valueOf(10000*Math.random()).intValue()+ new Date().getTime());
//        log.debug(random);
		return random;
    }
    public static String entlist(String challenge,String keyword)
    {
    	String url3="http://gx.gsxt.gov.cn/es/esAction!entlist.dhtml?urlflag=0&challenge="+challenge;
		List<NameValuePair> paramsList=new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("clear", "请输入企业名称、统一社会信用代码或注册号"));
		paramsList.add(new BasicNameValuePair("keyword",keyword));
		paramsList.add(new BasicNameValuePair("nowNum", ""));
		paramsList.add(new BasicNameValuePair("urlflag", "0"));
		try {
			return EntityUtils.toString(new PostRequest().request(httpClient, url3, null, paramsList, StepsGX.header).getEntity(),"utf-8");
		}  catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    public static String validate(String challenge,String validate)
    {
    	
    	String url2="http://gx.gsxt.gov.cn/pc-geetest/validate";
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
    public static void main(String[] args) {
    	JSONObject responseString = null;
    	while((responseString=gj()).getIntValue("success")!=1);
    	String validate = validate(responseString.getString(CHALLENGE) ,responseString.getString("validate") );
		System.out.println(validate);
		JSONObject parseObject = JSON.parseObject(validate);
		if("success".equals(parseObject.getString("status")))
			log.info(entlist(responseString.getString(CHALLENGE),"中国银行"));
    	
	}
	private static JSONObject gj() {
		try {
			
		
		JSONObject three = three();
    	String staticservers="http://static.geetest.com/";
    	String bg=staticservers+three.getString("bg");
//    	System.out.println("bg "+bg);
    	String fullbg=staticservers+three.getString("fullbg");
//    	System.out.println("fullbg "+fullbg);
    	List<BufferedImage> imageList = CombineImages.alignImage(bg, fullbg);
		int posx = FindXDiffRectangeOfTwoImage.findXDiffRectangeOfTwoImage(imageList, 6);
//		System.out.println("findXDiffRectangeOfTwoImage:" +posx);
		
		JSONObject responseString = null;
		do{
		responseString=GeeTestVlidate.GetValidate(posx, three,Cookie);
		}while("forbidden".equals(responseString.getString("message")));
//		System.out.println("responseString:" +responseString);
		return responseString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    
}
