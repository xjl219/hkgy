package com.jiyan.requestmenthod.get;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

public class GetRequest {
	public CloseableHttpResponse request(CloseableHttpClient httpClient,String url,RequestConfig config,Map<String,String> requestParams) throws ClientProtocolException, IOException{
		HttpGet getMethod=new HttpGet(url);
		if(config!=null){
			getMethod.setConfig(config);
		}
		if(requestParams!=null&&requestParams.size()>0){
			Set<Entry<String, String>> entrySet = requestParams.entrySet();
			for(Entry e:entrySet){
				String k=(String)e.getKey();
				String v=(String)e.getValue();
				getMethod.setHeader(k, v);
			}
		}
		
		CloseableHttpResponse execute = httpClient.execute(getMethod);
		if(getMethod!=null){
			getMethod.releaseConnection();
		}
		return execute;
	}
}
