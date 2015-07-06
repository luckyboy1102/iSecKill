package com.totoro.iSecKill.client;

import com.totoro.iSecKill.bean.Device;
import com.totoro.iSecKill.net.CreateOrder;

public class SimClient {

	public static void main(String[] args) {
        new Thread(new CreateOrder(new Device("e085f59ae49fd7e572e896d721c109dc"), "0060000001845")).start();
    }
}
