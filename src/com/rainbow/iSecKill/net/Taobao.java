package com.rainbow.iSecKill.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class Taobao {
    private static final Logger logger = Logger.getLogger(Taobao.class);
     
    private static String loginUrl = "https://login.taobao.com/member/login.jhtml";
     
    private static String tbToken = null;// �Ա�����ʹ�õ�token
     
    private static DefaultHttpClient httpclient = null;// HttpClient����
     
    private static HttpResponse response = null;
     
    private String userName = "";// �û���
     
    private String passWord = "";// ��������
     
    /**
     * ���캯��
     * 
     * @param userName
     * @param passWord
     */
    public Taobao(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }
     
    /**
     * ��½�Ա�
     * 
     * @return
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public boolean login()  {
        if (null != httpclient) {
            return true;
        }
     
        httpclient = new DefaultHttpClient();
        // �趨cookie����
        HttpClientParams.setCookiePolicy(httpclient.getParams(),
        CookiePolicy.BROWSER_COMPATIBILITY);
        // ��½ʹ�õı�����
        List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
        loginParams.add(new BasicNameValuePair("TPL_username", userName));
        loginParams.add(new BasicNameValuePair("TPL_password", passWord));
         
        //��½post����
        HttpPost loginPost = new HttpPost(loginUrl);
        loginPost.addHeader("Referer", loginUrl);
        loginPost.addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
        loginPost.addHeader("User-Agent",
        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.2; Tablet PC 2.0)");
        loginPost.addHeader("Host", "login.taobao.com");
        try {
            loginPost.setEntity(new UrlEncodedFormEntity(loginParams,  HTTP.UTF_8));
             
            //��ȡ��½Ӧ������
            response = httpclient.execute(loginPost);
             
            if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                String redirectUrl=getRedirectUrl();
                if(!"".equals(redirectUrl)){
                    //���ض���˵���ɹ���,��ȡtoken
                    getTbToken(redirectUrl);
                }else{
                    logger.info("��½��������ض���ʧ�ܣ�");
                    return false;
                }
            }else{
                logger.info("��½�������post����״̬:"+response.getStatusLine().getStatusCode());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            loginPost.abort();
        }
        return true;
    }
    /**
     * ��ȡÿ�ս��
     */
    @SuppressWarnings("rawtypes")
    public boolean getEveryDayCoins(){
        boolean flag=false;
         
        long t=System.currentTimeMillis();
        long enter_time=t-12345;
         
        String gainCoinUrl="http://vip.taobao.com/home/grant_everyday_coin.htm?t="+String.valueOf(t)
        +"&_tb_token_="+tbToken+"&checkCode=null" +
        "&enter_time="+String.valueOf(enter_time);
                 
        HttpGet gainCoinGet1 =new HttpGet(gainCoinUrl);
        HttpResponse gainCoinResponse1;
        try {
            gainCoinResponse1 = httpclient.execute(gainCoinGet1);
            HttpEntity httpEntity = gainCoinResponse1.getEntity(); 
            String responseJsonStr = EntityUtils.toString(httpEntity);//ȡ��Ӧ���ַ��� 
            //logger.info("��ȡ���Ӧ���ַ�����"+responseJsonStr);
            Map map = JSONObject.fromObject(responseJsonStr);
            int code=(Integer)map.get("code");
            int daysTomorrow=(Integer)map.get("daysTomorrow");
            String coinTomorrow=(String) map.get("coinTomorrow");
            int coinNew=(Integer) map.get("coinNew");
            int coinOld=(Integer) map.get("coinOld");
            int coinGot=coinNew-coinOld;
             
            if(1==code){
                logger.info("�ɹ���ȡ"+coinGot+"���Խ�ң�������"+daysTomorrow+"�죬��ǰ�������"+coinNew+"���������"+coinTomorrow);
                flag=true;
            }else if(4==code){
                logger.info("Ŷ? ��Ҫ������֤�룬����Խ�һ���ô�鷳��");
            }else if(5==code){
                logger.info("��֤�����");
            }else if(6==code){
                logger.info("��������߼�����5�����ѵ��û������������ң���ǰ�Խ������"+coinNew);
            }else if(2==code){
                logger.info("�������������Ѿ�����");
                flag=true;
            }else{
                logger.info("û�������code�������Ա��ͷ���");
                flag=true;
            }
            //�Է���һ����ִ��һ��.....
            gainCoinResponse1 = httpclient.execute(gainCoinGet1);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            gainCoinGet1.abort();
        }   
        return flag;
    }
     
    /**
     * ��ȡ�Ա��ض���url
     * @return
     */
    private String getRedirectUrl(){
        String redirectUrl="";
        HttpEntity resEntity =  response.getEntity();
         try {
            String bufferPageHtml=EntityUtils.toString(resEntity, HTTP.UTF_8);
            Pattern pattern1 = Pattern.compile("window.location = \"(.*)\";");
            Matcher m1 = pattern1.matcher(bufferPageHtml);
            if (m1.find()) {
                redirectUrl=m1.group(1);
                logger.info("redirectUrl:"+redirectUrl);
            } else {
                logger.error("��ȡredirectUrlʧ�ܣ�");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return redirectUrl;
    }
    /**
     * ��ȡ�Ա���½����
     * ����ʹ�����ַ�ʽ
     * 1.jsoup������ҳ��ȡ
     * 2.��httpclient�����cookie�л�ȡ
     * @param redirectUrl
     */
    private void getTbToken(String redirectUrl){
        HttpGet itaobaoGet =new HttpGet(redirectUrl);
        try {
            httpclient.execute(itaobaoGet); 
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            itaobaoGet.abort();
        }
         
        CookieStore cookiestore=httpclient.getCookieStore();
        List<Cookie> cookies=cookiestore.getCookies();
         if (cookies.isEmpty()) {    
             logger.info("cookies is null!");    
         } else {    
             for (int i = 0; i < cookies.size(); i++) {
                  Cookie cookie=cookies.get(i);
                  //logger.info( cookies.get(i).toString());  
                   if(cookie.getName().equals("_tb_token_")){
                        tbToken=cookie.getValue();
                        logger.info("�Ա�����:"+tbToken);
                    }
              }    
         }
    }
}
