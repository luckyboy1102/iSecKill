package com.totoro.iSecKill.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.totoro.iSecKill.utils.ParamUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Chen on 2015/7/1.
 */
public class CreateOrder implements Runnable {

    private static final String URL = "http://mlife.cmbchina.com/NeptuneApp/createOrderV2.json?_ss=640*1136&_mt=iphone7,2_8.3&_uid=e085f59ae49fd7e572e896d721c109dc" +
            "&_r=YES&appId=8535E782B9ED44A5955229AFB1E5B8A0&_iv=28&_accountId=e085f59ae49fd7e572e896d721c109dc&_ver=4.4.0&DeviceID=97E8812B16264FBF87427AA67FDDF3D7" +
            "&_pla=cmblife_iphone_4.4.0_&_pro=0&_appId=8535E782B9ED44A5955229AFB1E5B8A0&_requuid=";

    private Map<String, String> postData;

    private RequestSender sender;

    private JSONObject responseObj;

    public CreateOrder(String productId) {
        sender = new RequestSender();

        postData = new HashMap<String, String>();
        postData.put("payType", "1");
        postData.put("orderPoint", "9");
        postData.put("quantity", "1");
        postData.put("productId", productId);
        postData.put("mac", ParamUtil.generateMac(postData, "asc"));
        postData.put("accountId", "e085f59ae49fd7e572e896d721c109dc");
    }

    public void run() {
        do {
            String result1 = sender.execute(URL + UUID.randomUUID().toString().toUpperCase(), postData);
            System.out.println(result1);
            responseObj = JSON.parseObject(result1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!("1000".equals(responseObj.getString("respCode")) ||
                "1001".equals(responseObj.getString("respCode"))));
    }
}
