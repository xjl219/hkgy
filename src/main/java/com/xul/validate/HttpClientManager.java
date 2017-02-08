package com.xul.validate;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

public class HttpClientManager {
	private static PoolingHttpClientConnectionManager poolConnManager;
	private static final int maxTotalPool = 200;
	private static final int maxConPerRoute = 20;
	private static final int socketTimeout = 5000;
	private static final int connectionRequestTimeout = 5000;
	private static final int connectTimeout = 5000;

	static{  
		try {  
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null,  
					new TrustSelfSignedStrategy())  
					.build();  
			HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;  
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
					sslcontext,hostnameVerifier);  
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
					.register("http", PlainConnectionSocketFactory.getSocketFactory())  
					.register("https", sslsf)  
					.build();  
			poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
			// Increase max total connection to 200  
			poolConnManager.setMaxTotal(maxTotalPool);  
			// Increase default max connection per route to 20  
			poolConnManager.setDefaultMaxPerRoute(maxConPerRoute);  
			SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).build();  
			poolConnManager.setDefaultSocketConfig(socketConfig);  
		} catch (Exception e) {  

		}  
	} 
	
	public synchronized static CloseableHttpClient getConnection(	CookieStore cookieStore){  
		
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)  
				.setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();  
		HttpClientBuilder custom = HttpClients.custom()  
				.setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig);
		CloseableHttpClient httpClient = custom.setDefaultCookieStore(cookieStore).build();  
		if(poolConnManager!=null&&poolConnManager.getTotalStats()!=null){  
			
			System.out.println("now client pool "+poolConnManager.getTotalStats().toString());  
		}  
		return httpClient;  
	} 
	public synchronized static CloseableHttpClient getConnection(){  
	    RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)  
	            .setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();  
	    HttpClientBuilder custom = HttpClients.custom()  
	                .setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig);
		CloseableHttpClient httpClient = custom.build();  
	    if(poolConnManager!=null&&poolConnManager.getTotalStats()!=null){  

	        System.out.println("now client pool "+poolConnManager.getTotalStats().toString());  
	    }  
	    return httpClient;  
	} 
	
	public synchronized static CloseableHttpClient getConnection(String ip,int port){  
	    RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)  
	            .setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();  
	    CloseableHttpClient httpClient = HttpClients.custom()  
	                .setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig).build();  
	    if(poolConnManager!=null&&poolConnManager.getTotalStats()!=null){  

	        System.out.println("now client pool "+poolConnManager.getTotalStats().toString());  
	    }  
	    return httpClient;  
	} 
	
	
	public synchronized static void resleaseConnection(CloseableHttpClient httpClient){
		if(httpClient!=null){
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
