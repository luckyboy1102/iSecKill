package com.rainbow.iSecKill.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class ApacheHttpInvoker {
	private final HttpClient client = new DefaultHttpClient();
	
	public String doGet(String uri) {
		HttpGet httpGet = new HttpGet(uri);
		httpGet.addHeader("Accept", "text/html");  
		httpGet.addHeader("Accept-Charset", "utf-8");  
		httpGet.addHeader("Accept-Encoding", "gzip, deflate");  
		httpGet.addHeader("Accept-Language", "zh-CN");  
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Trident/7.0; rv:11.0) like Gecko"); 
		
		try {
			HttpResponse response = client.execute(httpGet);
			if(response.getStatusLine().getStatusCode()== 200){ 
                InputStream in = response.getEntity().getContent(); 
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        		StringBuilder sbStr = new StringBuilder();
        		String line;
        		while ((line = bufferedReader.readLine()) != null) { 
        			sbStr.append(line); 
        		} 
        		bufferedReader.close();
        		return new String(sbStr.toString().getBytes(), "utf-8");
            } 
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			httpGet.releaseConnection();
		}
		return null;
	}
	
	public String doPost(String url, Map<String, String> mapEntity) {
		UrlEncodedFormEntity entity = null;
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			NameValuePair nvp = null;
			Iterator<Entry<String, String>> iterator = mapEntity.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
				nvps.add(nvp);
			}
			
			entity = new UrlEncodedFormEntity(nvps,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        HttpPost post = new HttpPost(url); 
        post.addHeader("Accept", "text/html");  
        post.addHeader("Accept-Charset", "utf-8");  
        post.addHeader("Accept-Encoding", "gzip, deflate");  
        post.addHeader("Accept-Language", "zh-CN");  
        post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Trident/7.0; rv:11.0) like Gecko"); 
        post.setEntity(entity); 
        
        try {
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) { 
                InputStream in = response.getEntity().getContent(); 
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        		StringBuilder sbStr = new StringBuilder();
        		String line;
        		while ((line = bufferedReader.readLine()) != null) { 
        			sbStr.append(line); 
        		} 
        		bufferedReader.close();
        		return new String(sbStr.toString().getBytes(), "utf-8");
            } 
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			post.releaseConnection();
		}
		return null;
	}
}
