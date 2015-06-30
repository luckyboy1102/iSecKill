package com.rainbow.iSecKill.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * Created by Chen on 2015/6/30.
 */
public class SyncTime {

    private static final int PERIOD = 1 * 1000;

    private SyncTimeTask task;

    private Timer timer;

    public SyncTime(String productId) {
        timer = new Timer();
        task = new SyncTimeTask(productId);
    }

    public void syncTime() {
        timer.schedule(task, 0, PERIOD);
    }

    public void cancel() {
        task.cancel();
    }

    private final class SyncTimeTask extends TimerTask {

        private static final String URL = "http://mlife.cmbchina.com/NeptuneApp/queryProductDetails.json?_ss=640*1136&_mt=iphone7,2_8.3&_uid=e085f59ae49fd7e572e896d721c109dc" +
                "&_r=YES&appId=8535E782B9ED44A5955229AFB1E5B8A0&_iv=28&_accountId=e085f59ae49fd7e572e896d721c109dc&_ver=4.4.0&DeviceID=97E8812B16264FBF87427AA67FDDF3D7" +
                "&_pla=cmblife_iphone_4.4.0_&_pro=0&_appId=8535E782B9ED44A5955229AFB1E5B8A0&_requuid=";

        private RequestSender sender;

        private Map<String, String> postData;

        public SyncTimeTask(String productId) {
            this.sender = new RequestSender();
            postData = new HashMap<String, String>();
            postData.put("productNo", productId);
        }

        @Override
        public void run() {
            System.out.println("Start sync!");
            String responseStr = sender.execute(URL + UUID.randomUUID().toString().toUpperCase(), postData);
            JSONObject responseObj = JSON.parseObject(responseStr);
            System.out.println(responseObj.getString("sysTime"));
        }
    }
}
