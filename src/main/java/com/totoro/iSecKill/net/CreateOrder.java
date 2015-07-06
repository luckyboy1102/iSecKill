package com.totoro.iSecKill.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.totoro.iSecKill.bean.Device;
import com.totoro.iSecKill.utils.ParamUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Chen on 2015/7/1.
 */
public class CreateOrder implements Runnable {

    private static final String URL = "http://mlife.cmbchina.com/NeptuneApp/createOrderV2.json";

    private Map<String, String> postData;

    private RequestSender sender;

    private JSONObject responseObj;

    private Device device;

    public CreateOrder(Device device, String productId) {
        sender = new RequestSender();
        this.device = device;

        postData = new HashMap<String, String>();
        postData.put("payType", "1");
        postData.put("orderPoint", "9");
        postData.put("quantity", "1");
        postData.put("productId", productId);
        postData.put("mac", ParamUtil.generateMac(postData, "asc"));
        postData.put("accountId", device.getAccountId());
    }

    public void run() {
        do {
            String result1 = sender.execute(ParamUtil.getUrl(device, URL), postData);
            System.out.println(Thread.currentThread().getId() + " " + result1);
            responseObj = JSON.parseObject(result1);
        } while (!"1000".equals(responseObj.getString("respCode")));
    }
}
