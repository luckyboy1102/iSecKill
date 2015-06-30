package com.rainbow.iSecKill.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rainbow.iSecKill.net.RequestSender;
import com.rainbow.iSecKill.net.SyncTime;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimClient {

	public static void main(String[] args) {
        RequestSender sender = new RequestSender();

        String createOrder = "http://mlife.cmbchina.com/NeptuneApp/createOrderV2.json?_ss=640*1136&_mt=iphone7,2_8.3&_uid=e085f59ae49fd7e572e896d721c109dc" +
                "&_r=YES&appId=8535E782B9ED44A5955229AFB1E5B8A0&_iv=28&_accountId=e085f59ae49fd7e572e896d721c109dc&_ver=4.4.0&DeviceID=97E8812B16264FBF87427AA67FDDF3D7" +
                "&_pla=cmblife_iphone_4.4.0_&_pro=0&_appId=8535E782B9ED44A5955229AFB1E5B8A0&_requuid=" + UUID.randomUUID().toString().toUpperCase();

        Map<String, String> orderParam = new HashMap<String, String>();
        orderParam.put("productId", "0190000002393");
        orderParam.put("quantity", "1");
        orderParam.put("payType", "1");
        orderParam.put("orderPoint", "9");
        orderParam.put("accountId", "e085f59ae49fd7e572e896d721c109dc");
        orderParam.put("mac", "dae97f097757d17f3fec5bd8f4ff6590");
//
//        String queryDetail = "http://mlife.cmbchina.com/NeptuneApp/queryProductDetails.json?_ss=640*1136&_mt=iphone7,2_8.3&_uid=e085f59ae49fd7e572e896d721c109dc" +
//                "&_r=YES&appId=8535E782B9ED44A5955229AFB1E5B8A0&_iv=28&_accountId=e085f59ae49fd7e572e896d721c109dc&_ver=4.4.0&DeviceID=97E8812B16264FBF87427AA67FDDF3D7" +
//                "&_pla=cmblife_iphone_4.4.0_&_pro=0&_appId=8535E782B9ED44A5955229AFB1E5B8A0&_requuid=" + UUID.randomUUID().toString().toUpperCase();
//
//        Map<String, String> queryParam = new HashMap<String, String>();
//        queryParam.put("productNo", "0190000002393");
//
//        String getProductList = "http://mlife.cmbchina.com/NeptuneApp/queryProductList.json?_ss=640*1136&_mt=iphone7,2_8.3&_uid=e085f59ae49fd7e572e896d721c109dc" +
//                "&_r=YES&appId=8535E782B9ED44A5955229AFB1E5B8A0&_iv=28&_accountId=e085f59ae49fd7e572e896d721c109dc&_ver=4.4.0&DeviceID=97E8812B16264FBF87427AA67FDDF3D7" +
//                "&_pla=cmblife_iphone_4.4.0_&_pro=0&_appId=8535E782B9ED44A5955229AFB1E5B8A0&_requuid=" + UUID.randomUUID().toString().toUpperCase();
//
//        Map<String, String> productListParam = new HashMap<String, String>();
//        productListParam.put("pageIndex", "1");
//        productListParam.put("cityNo", "25");
//        productListParam.put("couponType", "3HoursRushBuy");
//
//        String result = sender.execute(getProductList, productListParam);
//        JSONObject object = JSON.parseObject(result);
//        JSONArray array = object.getJSONArray("coupons");
//        System.out.println(array);

//        String result = RequestSender.execute(queryDetail, queryParam);
//        System.out.println(result);
        String result1 = sender.execute(createOrder, orderParam);
        System.out.println(result1);

    }

}
