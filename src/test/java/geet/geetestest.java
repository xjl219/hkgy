package geet;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.xul.validate.GeeTest;

public class geetestest {
	public static void main(String[] args) {
		String ref= "http://gx.gsxt.gov.cn/sydq/loginSydqAction!sydq.dhtml";
	
		String host="gx.gsxt.gov.cn";
		String url= "http://gx.gsxt.gov.cn/pc-geetest/register?t="+new Date().getTime();
	
		GeeTest geet= new GeeTest(host,ref);
		JSONObject validate = null;//geet.validate(url);
		do{
			validate=  geet.validate(url);;
			}while("forbidden".equals(validate.getString("message")));
		System.out.println(validate);

	}
}
