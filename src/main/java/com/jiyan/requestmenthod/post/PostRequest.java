package com.jiyan.requestmenthod.post;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

public class PostRequest {
	public CloseableHttpResponse request(CloseableHttpClient httpClient,String url,RequestConfig config,List<NameValuePair> paramsList,Map<String,String> requestParams) throws ClientProtocolException, IOException{
		HttpPost postMethod=new HttpPost(url);
		if(config!=null){
			postMethod.setConfig(config);
		}
		if(requestParams!=null&&requestParams.size()>0){
			Set<Entry<String, String>> entrySet = requestParams.entrySet();
			for(Entry e:entrySet){
				String k=(String)e.getKey();
				String v=(String)e.getValue();
				postMethod.setHeader(k, v);
			}
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramsList, "utf-8");
		postMethod.setEntity(entity); 
		return httpClient.execute(postMethod);
	}
}
