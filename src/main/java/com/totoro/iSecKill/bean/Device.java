package com.totoro.iSecKill.bean;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Represent a device
 * Created by Chen on 2015/7/6.
 */
public class Device {

    private String _ss;

    private String _mt;

    private String _uid;

    private String _r;

    private String appId;

    private String _iv;

    private String _accountId;

    private String _ver;

    private String DeviceID;

    private String _pla;

    private String _pro;

    private String _appId;

    public Device(String accountId) {
        this._ss = "640*1136";
        this._mt = "iphone7,2_8.3";
        this._uid = accountId;
        this._accountId = accountId;
        this._r = "YES";
        this.appId = "8535E782B9ED44A5955229AFB1E5B8A0";
        this._appId = appId;
        this._iv = "28";
        this._ver = "4.4.0";
        this.DeviceID = "97E8812B16264FBF87427AA67FDDF3D7";
        this._pla = "cmblife_iphone_4.4.0_";
        this._pro = "0";
    }

    @Override
    public String toString() {
        Field[] fields = Device.class.getDeclaredFields();
        StringBuilder param = new StringBuilder("?");
        for (Field field : fields) {
            try {
                param.append(field.getName()).append("=").append(field.get(this)).append("&");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return param.toString();
    }

    public String getAccountId() {
        return _accountId;
    }
}
