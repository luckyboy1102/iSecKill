package com.totoro.iSecKill.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Chen on 2015/6/30.
 */
public class SyncTime {

    private static final int PERIOD = 500;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);

    private String productId;

    public SyncTime(String productId) {
        this.productId = productId;
    }

    public void syncTime() {
        executorService.scheduleAtFixedRate(new SyncTimeTask(productId), 0, PERIOD, TimeUnit.MILLISECONDS);
    }

    private final class SyncTimeTask implements Runnable {

        private static final String URL = "http://mlife.cmbchina.com/NeptuneApp/queryProductDetails.json?_ss=640*1136&_mt=iphone7,2_8.3&_uid=e085f59ae49fd7e572e896d721c109dc" +
                "&_r=YES&appId=8535E782B9ED44A5955229AFB1E5B8A0&_iv=28&_accountId=e085f59ae49fd7e572e896d721c109dc&_ver=4.4.0&DeviceID=97E8812B16264FBF87427AA67FDDF3D7" +
                "&_pla=cmblife_iphone_4.4.0_&_pro=0&_appId=8535E782B9ED44A5955229AFB1E5B8A0&_requuid=";

        private RequestSender sender;

        private Map<String, String> postData;

        private SimpleDateFormat formatter;

        public SyncTimeTask(String productId) {
            this.sender = new RequestSender();
            formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
            postData = new HashMap<String, String>();
            postData.put("productNo", productId);
        }

        public void run() {
            System.out.println(formatter.format(new Date()) + " Start sync!");
            String responseStr = sender.execute(URL + UUID.randomUUID().toString().toUpperCase(), postData);
            JSONObject responseObj = JSON.parseObject(responseStr);
            System.out.println("Sync success, Server Time:" + responseObj.getString("sysTime"));
        }
    }
}
