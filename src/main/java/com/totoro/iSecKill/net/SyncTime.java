package com.totoro.iSecKill.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.totoro.iSecKill.bean.Device;
import com.totoro.iSecKill.utils.DateUtil;
import com.totoro.iSecKill.utils.ParamUtil;

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

    private Device device;

    public SyncTime(Device device, String productId) {
        this.device = device;
        this.productId = productId;
    }

    public void syncTime() {
        executorService.scheduleAtFixedRate(new SyncTimeTask(productId), 0, PERIOD, TimeUnit.MILLISECONDS);
    }

    private final class SyncTimeTask implements Runnable {

        private static final String URL = "http://mlife.cmbchina.com/NeptuneApp/queryProductDetails.json";

        private RequestSender sender;

        private Map<String, String> postData;

        public SyncTimeTask(String productId) {
            this.sender = new RequestSender();
            postData = new HashMap<String, String>();
            postData.put("productNo", productId);
        }

        public void run() {
            System.out.println(DateUtil.formatHMS(new Date()) + " Start sync!");
            String responseStr = sender.execute(ParamUtil.getUrl(device, URL), postData);
            JSONObject responseObj = JSON.parseObject(responseStr);
            System.out.println("Sync success, Server Time:" + responseObj.getString("sysTime"));
        }
    }
}
